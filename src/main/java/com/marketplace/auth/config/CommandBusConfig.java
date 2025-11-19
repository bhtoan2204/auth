package com.marketplace.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marketplace.auth.application.auth.command.LoginCommand;
import com.marketplace.auth.application.auth.command.LoginCommandHandler;
import com.marketplace.auth.application.command.CommandBus;
import com.marketplace.auth.application.user.command.CreateUserCommand;
import com.marketplace.auth.application.user.command.CreateUserCommandHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CommandBusConfig {

    private final CreateUserCommandHandler createUserCommandHandler;
    private final LoginCommandHandler loginCommandHandler;

    @Bean
    public CommandBus commandBus() {
        CommandBus bus = new CommandBus();
        bus.register(CreateUserCommand.class, createUserCommandHandler);
        bus.register(LoginCommand.class, loginCommandHandler);
        return bus;
    }
}

