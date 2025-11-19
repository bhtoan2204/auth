package com.marketplace.auth.application.command;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CommandBus {

    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> handlers = new HashMap<>();

    public <C extends Command<R>, R> void register(Class<C> commandType, CommandHandler<C, R> handler) {
        handlers.put(commandType, handler);
    }

    @SuppressWarnings("unchecked")
    public <C extends Command<R>, R> Mono<R> execute(C command) {
        CommandHandler<C, R> handler = (CommandHandler<C, R>) handlers.get(command.getClass());
        if (handler == null) {
            return Mono.error(new IllegalArgumentException("No handler registered for command: " + command.getClass().getName()));
        }
        return Mono.fromCallable(() -> handler.handle(command));
    }
}

