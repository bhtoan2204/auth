package com.marketplace.auth.application.user.usecase;

import org.springframework.stereotype.Component;

import com.marketplace.auth.application.user.command.GetProfileCommand;
import com.marketplace.auth.application.user.command.GetProfileCommandResult;
import com.marketplace.auth.application.user.service.UserService;
import com.marketplace.auth.domain.aggregate.UserAggregate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetProfileUseCase {

    private final UserService userService;

    public GetProfileCommandResult execute(GetProfileCommand command) {
        UserAggregate userAggregate = userService.findById(command.userId());
        return new GetProfileCommandResult(userAggregate);
    }
}
