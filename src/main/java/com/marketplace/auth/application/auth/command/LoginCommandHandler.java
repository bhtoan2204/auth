package com.marketplace.auth.application.auth.command;

import org.springframework.stereotype.Component;

import com.marketplace.auth.application.auth.usecase.LoginUseCase;
import com.marketplace.auth.application.command.CommandHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler implements CommandHandler<LoginCommand, LoginCommandResult> {

    private final LoginUseCase loginUseCase;

    @Override
    public LoginCommandResult handle(LoginCommand command) {
        return loginUseCase.execute(command);
    }
}
