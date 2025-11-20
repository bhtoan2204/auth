package com.marketplace.auth.application.user.command;

public record CreateUserCommandResult(
                Integer userId,
                String username,
                String email) {
}
