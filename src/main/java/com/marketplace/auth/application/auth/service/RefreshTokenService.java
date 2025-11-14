package com.marketplace.auth.application.auth.service;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.auth.port.RefreshTokenStore;
import com.marketplace.auth.config.AuthProperties;
import com.marketplace.auth.domain.auth.exception.RefreshTokenInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final int TOKEN_BYTE_SIZE = 48;

    private final RefreshTokenStore refreshTokenStore;
    private final AuthProperties authProperties;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenData issue(Long userId) {
        String rawValue = generateRandomToken();
        refreshTokenStore.saveToken(userId, rawValue, authProperties.getToken().getRefreshTtl());
        return new RefreshTokenData(userId, encode(userId, rawValue));
    }

    public RefreshTokenData rotate(String rawToken) {
        TokenPayload payload = decode(rawToken);
        String persistedValue = refreshTokenStore.findToken(payload.userId())
            .orElseThrow(RefreshTokenInvalidException::new);

        if (!persistedValue.equals(payload.tokenValue())) {
            throw new RefreshTokenInvalidException();
        }

        String newValue = generateRandomToken();
        refreshTokenStore.saveToken(payload.userId(), newValue, authProperties.getToken().getRefreshTtl());
        return new RefreshTokenData(payload.userId(), encode(payload.userId(), newValue));
    }

    public void revoke(String rawToken) {
        TokenPayload payload = decode(rawToken);
        String persistedValue = refreshTokenStore.findToken(payload.userId())
            .orElseThrow(RefreshTokenInvalidException::new);
        if (!persistedValue.equals(payload.tokenValue())) {
            throw new RefreshTokenInvalidException();
        }
        refreshTokenStore.deleteToken(payload.userId());
    }

    private String generateRandomToken() {
        byte[] buffer = new byte[TOKEN_BYTE_SIZE];
        secureRandom.nextBytes(buffer);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer);
    }

    private String encode(Long userId, String tokenValue) {
        return "%d.%s".formatted(userId, tokenValue);
    }

    private TokenPayload decode(String rawToken) {
        if (rawToken == null || !rawToken.contains(".")) {
            throw new RefreshTokenInvalidException();
        }
        String[] parts = rawToken.split("\\.", 2);
        if (parts.length != 2) {
            throw new RefreshTokenInvalidException();
        }
        try {
            Long userId = Long.parseLong(parts[0]);
            String tokenValue = parts[1];
            if (tokenValue.isBlank()) {
                throw new RefreshTokenInvalidException();
            }
            return new TokenPayload(userId, tokenValue);
        } catch (NumberFormatException ex) {
            throw new RefreshTokenInvalidException();
        }
    }

    public record RefreshTokenData(Long userId, String refreshToken) { }

    private record TokenPayload(Long userId, String tokenValue) { }
}
