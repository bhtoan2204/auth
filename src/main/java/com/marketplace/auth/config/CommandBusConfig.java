package com.marketplace.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.marketplace.auth.application.auth.command.LoginCommand;
import com.marketplace.auth.application.auth.command.LoginCommandHandler;
import com.marketplace.auth.application.command.CommandBus;
import com.marketplace.auth.application.user.command.CreateUserCommand;
import com.marketplace.auth.application.user.command.CreateUserCommandHandler;
import com.marketplace.auth.application.user.command.GetProfileCommand;
import com.marketplace.auth.application.user.command.GetProfileCommandHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CommandBusConfig {

    private final CreateUserCommandHandler createUserCommandHandler;
    private final LoginCommandHandler loginCommandHandler;
    private final GetProfileCommandHandler getProfileCommandHandler;

    @Bean
    public CommandBus commandBus() {
        CommandBus bus = new CommandBus();
        bus.register(CreateUserCommand.class, createUserCommandHandler);
        bus.register(LoginCommand.class, loginCommandHandler);
        bus.register(GetProfileCommand.class, getProfileCommandHandler);
        return bus;
    }
}
