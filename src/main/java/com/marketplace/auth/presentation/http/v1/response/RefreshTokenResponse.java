package com.marketplace.auth.presentation.http.v1.response;

import java.time.Instant;

/**
 * Response DTO for refresh token operation.
 * Contains new access and refresh tokens.
 */
public record RefreshTokenResponse(
                String accessToken,
                String refreshToken,
                Instant expiresAt) {
}