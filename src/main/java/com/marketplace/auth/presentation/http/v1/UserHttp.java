package com.marketplace.auth.presentation.http.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.application.command.CommandBus;
import com.marketplace.auth.application.user.command.CreateUserCommand;
import com.marketplace.auth.presentation.http.v1.request.CreateUserRequest;
import com.marketplace.auth.presentation.http.v1.response.BaseResponse;
import com.marketplace.auth.presentation.http.v1.response.CreateUserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserHttp {

    private final CommandBus commandBus;

    @PostMapping
    public Mono<BaseResponse<CreateUserResponse>> create(@Valid @RequestBody CreateUserRequest request) {
        CreateUserCommand command = new CreateUserCommand(
                request.username(),
                request.email(),
                request.password(),
                "customer");

        return commandBus.execute(command)
                .map(result -> {
                    CreateUserResponse response = new CreateUserResponse(
                            result.userId(),
                            result.username(),
                            result.email());
                    return BaseResponse.success(response);
                });
    }
}
