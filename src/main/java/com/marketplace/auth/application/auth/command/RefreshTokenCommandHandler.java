package com.marketplace.auth.application.auth.command;

import org.springframework.stereotype.Component;

import com.marketplace.auth.application.auth.usecase.RefreshTokenUseCase;
import com.marketplace.auth.application.command.CommandHandler;

import lombok.RequiredArgsConstructor;

/**
 * Handler for RefreshTokenCommand.
 * Delegates the refresh token logic to the RefreshTokenUseCase.
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenCommandHandler implements CommandHandler<RefreshTokenCommand, RefreshTokenCommandResult> {

    private final RefreshTokenUseCase refreshTokenUseCase;

    @Override
    public RefreshTokenCommandResult handle(RefreshTokenCommand command) {
        return refreshTokenUseCase.execute(command).block();
    }
}