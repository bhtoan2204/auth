package com.marketplace.auth.application.auth.usecase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.marketplace.auth.application.auth.command.LoginCommand;
import com.marketplace.auth.application.auth.command.LoginCommandResult;
import com.marketplace.auth.application.exceptions.AccountBannedException;
import com.marketplace.auth.application.exceptions.AccountInactiveException;
import com.marketplace.auth.application.exceptions.AccountLockedException;
import com.marketplace.auth.application.exceptions.AuthenticationException;
import com.marketplace.auth.application.user.service.UserService;
import com.marketplace.auth.domain.aggregate.UserAggregate;
import com.marketplace.auth.domain.entity.User;
import com.marketplace.auth.infrastructure.jwt.JwtProperties;
import com.marketplace.auth.infrastructure.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginUseCase {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    public LoginCommandResult execute(LoginCommand command) {
        try {
            UserAggregate aggregate = userService.findByUsername(command.username());
            if (aggregate == null || aggregate.getUser() == null) {
                throw new AuthenticationException();
            }

            User user = aggregate.getUser();

            if (!passwordEncoder.matches(command.password() + user.getSalt(), user.getPasswordHash())) {
                handleFailedLogin(user);
                throw new AuthenticationException();
            }

            validateAccountStatus(aggregate);

            handleSuccessfulLogin(user);

            String accessToken = generateAccessToken(user);
            String refreshToken = generateRefreshToken(user);

            return new LoginCommandResult(
                    accessToken,
                    refreshToken,
                    Instant.now().plus(jwtProperties.getToken().getAccessTtl()));

        } catch (Exception e) {
            log.error("Error authenticating user", e);
            throw new AuthenticationException("Failed to authenticate user", e);
        }
    }

    private void handleFailedLogin(User user) {
        try {
            int currentAttempts = user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0;
            int failedAttempts = currentAttempts + 1;
            user.setFailedLoginAttempts(failedAttempts);
            user.setLastFailedLogin(LocalDateTime.now());

            if (failedAttempts >= 5) {
                user.setLockedUntil(LocalDateTime.now().plusHours(1));
            }

            userService.saveUser(user);
        } catch (Exception e) {
            log.error("Error handling failed login", e);
            throw new AuthenticationException("Failed to handle failed login", e);
        }
    }

    private void validateAccountStatus(UserAggregate aggregate) {
        if (!aggregate.isActive()) {
            throw new AccountInactiveException();
        }

        if (aggregate.isBanned()) {
            throw new AccountBannedException();
        }

        if (aggregate.isLocked()) {
            throw new AccountLockedException();
        }
    }

    private void handleSuccessfulLogin(User user) {
        try {
            user.setFailedLoginAttempts(0);
            user.setLastLoginAt(LocalDateTime.now());
            user.setLockedUntil(null);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("Error handling successful login", e);
            throw new AuthenticationException("Failed to handle successful login", e);
        }
    }

    private String generateAccessToken(User user) {
        try {
            Map<String, Object> payload = Map.of(
                    "sub", user.getId().toString(),
                    "username", user.getUsername(),
                    "type", "access",
                    "iss", jwtProperties.getJwt().getIssuer());
            return jwtProvider.generateToken(payload, jwtProperties.getToken().getAccessTtl(),
                    jwtProperties.getJwt().getSecret());
        } catch (Exception e) {
            log.error("Error generating access token", e);
            throw new AuthenticationException("Failed to generate access token", e);
        }
    }

    private String generateRefreshToken(User user) {
        try {
            Map<String, Object> payload = Map.of(
                    "sub", user.getId().toString(),
                    "username", user.getUsername(),
                    "type", "refresh",
                    "iss", jwtProperties.getJwt().getIssuer());
            return jwtProvider.generateToken(payload, jwtProperties.getToken().getRefreshTtl(),
                    jwtProperties.getJwt().getSecret());
        } catch (Exception e) {
            log.error("Error generating refresh token", e);
            throw new AuthenticationException("Failed to generate refresh token", e);
        }
    }
}
