package com.marketplace.auth.application.auth.command;

import com.marketplace.auth.application.command.Command;

/**
 * Command for refreshing authentication tokens.
 * This command is used to generate new access and refresh tokens using a valid refresh token.
 */
public record RefreshTokenCommand(
        String refreshToken
) implements Command<RefreshTokenCommandResult> {

    public RefreshTokenCommand {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required");
        }
    }
}