package com.marketplace.auth.application.user.command;

import com.marketplace.auth.application.command.Command;

public record CreateUserCommand(
        String username,
        String email,
        String password,
        String userType
) implements Command<CreateUserCommandResult> {
}

