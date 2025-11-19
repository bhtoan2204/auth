package com.marketplace.auth.presentation.http;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.application.auth.command.LoginCommand;
import com.marketplace.auth.application.command.CommandBus;
import com.marketplace.auth.presentation.http.request.LoginRequest;
import com.marketplace.auth.presentation.http.response.BaseResponse;
import com.marketplace.auth.presentation.http.response.LoginResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthHttp {

    private final CommandBus commandBus;

    @PostMapping("/login")
    public Mono<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
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
    }
}
