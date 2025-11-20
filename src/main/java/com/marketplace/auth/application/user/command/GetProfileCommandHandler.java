package com.marketplace.auth.application.user.command;

import org.springframework.stereotype.Component;

import com.marketplace.auth.application.command.CommandHandler;
import com.marketplace.auth.application.user.usecase.GetProfileUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetProfileCommandHandler implements CommandHandler<GetProfileCommand, GetProfileCommandResult> {

    private final GetProfileUseCase getProfileUseCase;

    @Override
    public GetProfileCommandResult handle(GetProfileCommand command) {
        return getProfileUseCase.execute(command);
    }
}
