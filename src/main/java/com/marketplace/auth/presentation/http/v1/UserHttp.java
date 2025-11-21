package com.marketplace.auth.presentation.http.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.application.command.CommandBus;
import com.marketplace.auth.application.user.command.CreateUserCommand;
import com.marketplace.auth.constant.enums.AccountType;
import com.marketplace.auth.presentation.http.v1.request.CreateUserRequest;
import com.marketplace.auth.presentation.http.v1.response.BaseResponse;
import com.marketplace.auth.presentation.http.v1.response.CreateUserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserHttp {

    private final CommandBus commandBus;

    @PostMapping
    public Mono<BaseResponse<CreateUserResponse>> createCustomer(@Valid @RequestBody CreateUserRequest request) {
        try {
            CreateUserCommand command = new CreateUserCommand(
                    request.username(),
                    request.email(),
                    request.password(),
                    AccountType.CUSTOMER.name());

            return commandBus.execute(command)
                    .map(result -> {
                        CreateUserResponse response = new CreateUserResponse(
                                result.userId(),
                                result.username(),
                                result.email());
                        return BaseResponse.success(response);
                    });
        } catch (Exception e) {
            log.error("Error creating user", e);
            return Mono.just(BaseResponse.error("Failed to create user"));
        }
    }
}
