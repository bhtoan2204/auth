package com.marketplace.auth.application.auth.usecase;

import org.springframework.stereotype.Component;

import com.marketplace.auth.application.auth.command.LoginCommand;
import com.marketplace.auth.application.auth.command.LoginCommandResult;
import com.marketplace.auth.application.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthService authService;

    public LoginCommandResult execute(LoginCommand command) {
        AuthService.AuthResult result = authService.authenticate(
                command.username(),
                command.password());

        return new LoginCommandResult(
                result.accessToken(),
                result.refreshToken(),
                result.expiresAt());
    }
}
