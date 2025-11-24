package com.marketplace.auth.application.auth.usecase;

import org.springframework.stereotype.Service;

import com.marketplace.auth.application.auth.command.RefreshTokenCommand;
import com.marketplace.auth.application.auth.command.RefreshTokenCommandResult;
import com.marketplace.auth.application.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Use case for refreshing authentication tokens.
 * Validates the refresh token and generates new tokens if valid.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final AuthService authService;

    public Mono<RefreshTokenCommandResult> execute(RefreshTokenCommand command) {
        return Mono.fromCallable(() -> {
            AuthService.AuthResult result = authService.refreshToken(command.refreshToken());
            return new RefreshTokenCommandResult(
                    result.accessToken(),
                    result.refreshToken(),
                    result.expiresAt()
            );
        });
    }
}