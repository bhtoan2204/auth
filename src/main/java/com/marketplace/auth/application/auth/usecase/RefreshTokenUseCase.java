package com.marketplace.auth.application.auth.usecase;

import java.time.Instant;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.marketplace.auth.application.auth.command.RefreshTokenCommand;
import com.marketplace.auth.application.auth.command.RefreshTokenCommandResult;
import com.marketplace.auth.application.exceptions.AccountBannedException;
import com.marketplace.auth.application.exceptions.AccountInactiveException;
import com.marketplace.auth.application.exceptions.AccountLockedException;
import com.marketplace.auth.application.exceptions.AuthenticationException;
import com.marketplace.auth.application.exceptions.InvalidTokenException;
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
public class RefreshTokenUseCase {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    public RefreshTokenCommandResult execute(RefreshTokenCommand command) {
        Map<String, Object> tokenData;
        try {
            Object verifiedData = jwtProvider.verifyToken(command.refreshToken(), jwtProperties.getJwt().getSecret());
            if (!(verifiedData instanceof Map)) {
                throw new AuthenticationException();
            }
            tokenData = (Map<String, Object>) verifiedData;
        } catch (Exception e) {
            log.error("Error verifying refresh token", e);
            throw new InvalidTokenException();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> jwtClaims = (Map<String, Object>) tokenData.get("claims");
        if (jwtClaims == null) {
            throw new InvalidTokenException();
        }

        Object typeClaim = jwtClaims.get("type");
        if (typeClaim == null) {
            throw new InvalidTokenException("Token type not found");
        }
        String tokenType;
        if (typeClaim instanceof String) {
            tokenType = (String) typeClaim;
        } else {
            tokenType = typeClaim.toString();
        }
        if (!"refresh".equals(tokenType)) {
            throw new InvalidTokenException("Token is not a refresh token");
        }

        String userIdStr = (String) tokenData.get("subject");
        if (userIdStr == null) {
            throw new InvalidTokenException();
        }

        Integer userId = Integer.valueOf(userIdStr);

        UserAggregate aggregate = userService.findById(userId);
        if (aggregate == null) {
            throw new AuthenticationException();
        }

        validateAccountStatus(aggregate);

        User user = aggregate.getUser();

        String newAccessToken = generateAccessToken(user);
        String newRefreshToken = generateRefreshToken(user);

        return new RefreshTokenCommandResult(
                newAccessToken,
                newRefreshToken,
                Instant.now().plus(jwtProperties.getToken().getAccessTtl()));
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