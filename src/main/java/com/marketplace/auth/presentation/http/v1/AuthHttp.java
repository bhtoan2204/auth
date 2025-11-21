package com.marketplace.auth.presentation.http.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.application.auth.command.LoginCommand;
import com.marketplace.auth.application.command.CommandBus;
import com.marketplace.auth.presentation.http.v1.request.LoginRequest;
import com.marketplace.auth.presentation.http.v1.response.BaseResponse;
import com.marketplace.auth.presentation.http.v1.response.LoginResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthHttp {

    private final CommandBus commandBus;

    @PostMapping("/login")
    public Mono<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginCommand command = new LoginCommand(
                    request.username(),
                    request.password());

            return commandBus.execute(command)
                    .map(result -> {
                        LoginResponse response = new LoginResponse(
                                result.accessToken(),
                                result.refreshToken(),
                                result.expiresAt());
                        return BaseResponse.success(response);
                    });
        } catch (Exception e) {
            log.error("Error logging in", e);
            return Mono.just(BaseResponse.error("Failed to login"));
        }
    }
}
