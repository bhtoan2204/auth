package com.marketplace.auth.application.auth.command;

import com.marketplace.auth.application.command.Command;

public record RefreshTokenCommand(
        String refreshToken) implements Command<RefreshTokenCommandResult> {

    public RefreshTokenCommand {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required");
        }
    }
}