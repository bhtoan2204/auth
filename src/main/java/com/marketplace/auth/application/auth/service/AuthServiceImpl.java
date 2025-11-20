package com.marketplace.auth.application.auth.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marketplace.auth.application.exceptions.AccountBannedException;
import com.marketplace.auth.application.exceptions.AccountInactiveException;
import com.marketplace.auth.application.exceptions.AccountLockedException;
import com.marketplace.auth.application.exceptions.AuthenticationException;
import com.marketplace.auth.application.user.service.UserService;
import com.marketplace.auth.domain.aggregate.UserAggregate;
import com.marketplace.auth.infrastructure.jwt.JwtProperties;
import com.marketplace.auth.infrastructure.jwt.JwtProvider;
import com.marketplace.auth.infrastructure.persistence.model.UserAccountEntity;
import com.marketplace.auth.infrastructure.persistence.repository.FactoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final FactoryRepository factoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Override
    public AuthResult authenticate(String username, String password) {
        UserAggregate aggregate = userService.findByUsername(username);
        if (aggregate == null || aggregate.getUser() == null) {
            throw new AuthenticationException();
        }

        UserAccountEntity userEntity = factoryRepository.getUserAccountRepository()
                .findByUsername(username)
                .orElseThrow(AuthenticationException::new);

        if (!passwordEncoder.matches(password + userEntity.getSalt(), userEntity.getPasswordHash())) {
            handleFailedLogin(userEntity);
            throw new AuthenticationException();
        }

        validateAccountStatus(aggregate);

        handleSuccessfulLogin(userEntity);

        String accessToken = generateAccessToken(userEntity);
        String refreshToken = generateRefreshToken(userEntity);

        return new AuthResult(
                accessToken,
                refreshToken,
                Instant.now().plus(jwtProperties.getToken().getAccessTtl()));
    }

    private void handleFailedLogin(UserAccountEntity userEntity) {
        int failedAttempts = userEntity.getFailedLoginAttempts() + 1;
        userEntity.setFailedLoginAttempts(failedAttempts);
        userEntity.setLastFailedLogin(LocalDateTime.now());

        if (failedAttempts >= 5) {
            userEntity.setLockedUntil(LocalDateTime.now().plusHours(1));
        }

        factoryRepository.getUserAccountRepository().save(userEntity);
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

    private void handleSuccessfulLogin(UserAccountEntity userEntity) {
        userEntity.setFailedLoginAttempts(0);
        userEntity.setLastLoginAt(LocalDateTime.now());
        userEntity.setLockedUntil(null);
        factoryRepository.getUserAccountRepository().save(userEntity);
    }

    private String generateAccessToken(UserAccountEntity userEntity) {
        Map<String, Object> payload = Map.of(
                "sub", userEntity.getId().toString(),
                "username", userEntity.getUsername(),
                "type", "access",
                "iss", jwtProperties.getJwt().getIssuer());
        return jwtProvider.generateToken(payload, jwtProperties.getToken().getAccessTtl(),
                jwtProperties.getJwt().getSecret());
    }

    private String generateRefreshToken(UserAccountEntity userEntity) {
        Map<String, Object> payload = Map.of(
                "sub", userEntity.getId().toString(),
                "username", userEntity.getUsername(),
                "type", "refresh",
                "iss", jwtProperties.getJwt().getIssuer());
        return jwtProvider.generateToken(payload, jwtProperties.getToken().getRefreshTtl(),
                jwtProperties.getJwt().getSecret());
    }
}
