package com.marketplace.auth.application.auth.command;

import com.marketplace.auth.application.command.Command;

public record LoginCommand(
        String username,
        String password) implements Command<LoginCommandResult> {
}
