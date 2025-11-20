package com.marketplace.auth.application.user.command;

import org.springframework.stereotype.Component;

import com.marketplace.auth.application.command.CommandHandler;
import com.marketplace.auth.application.user.usecase.CreateUserUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand, CreateUserCommandResult> {

    private final CreateUserUseCase createUserUseCase;

    @Override
    public CreateUserCommandResult handle(CreateUserCommand command) {
        return createUserUseCase.execute(command);
    }
}
