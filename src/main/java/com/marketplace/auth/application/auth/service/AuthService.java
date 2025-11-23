package com.marketplace.auth.application.auth.service;

import java.time.Instant;

public interface AuthService {

    AuthResult authenticate(String username, String password);

    AuthResult refreshToken(String refreshToken);

    record AuthResult(
            String accessToken,
            String refreshToken,
            Instant expiresAt) {
    }
}
