package com.marketplace.auth.presentation.http.response;

import java.time.Instant;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Instant expiresAt
) {
}

