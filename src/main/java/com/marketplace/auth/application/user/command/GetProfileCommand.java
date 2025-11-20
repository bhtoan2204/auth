package com.marketplace.auth.application.user.command;

import com.marketplace.auth.application.command.Command;

public record GetProfileCommand(Integer userId) implements Command<GetProfileCommandResult> {
}
