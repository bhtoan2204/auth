package com.marketplace.auth.application.auth.command;

import java.time.Instant;

/**
 * Result of the RefreshTokenCommand execution.
 * Contains new access and refresh tokens with expiration time.
 */
public record RefreshTokenCommandResult(
        String accessToken,
        String refreshToken,
        Instant expiresAt
) {
}