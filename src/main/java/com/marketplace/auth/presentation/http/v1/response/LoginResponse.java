package com.marketplace.auth.presentation.http.v1.response;

import java.time.Instant;

public record LoginResponse(
                String accessToken,
                String refreshToken,
                Instant expiresAt) {
}
