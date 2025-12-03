package com.marketplace.auth.application.auth.command;

import java.time.Instant;

public record RefreshTokenCommandResult(
                String accessToken,
                String refreshToken,
                Instant expiresAt) {
}