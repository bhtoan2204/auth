package com.marketplace.auth.application.auth.command;

import java.time.Instant;

public record LoginCommandResult(
                String accessToken,
                String refreshToken,
                Instant expiresAt) {
}
