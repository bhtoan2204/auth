package com.marketplace.auth.presentation.http.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.application.command.CommandBus;
import com.marketplace.auth.application.user.command.GetProfileCommand;
import com.marketplace.auth.presentation.http.v1.response.BaseResponse;
import com.marketplace.auth.presentation.http.v1.response.ProfileResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/profile")
@RequiredArgsConstructor
public class ProfileHttp {

    private final CommandBus commandBus;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Mono<BaseResponse<ProfileResponse>> getProfile(Authentication authentication) {
        try {
            String userIdStr = authentication.getName();
            Integer userId = Integer.valueOf(userIdStr);

            GetProfileCommand command = new GetProfileCommand(userId);

            return commandBus.execute(command)
                    .map(result -> {
                        ProfileResponse response = new ProfileResponse(result.userAggregate());
                        return BaseResponse.success(response);
                    });
        } catch (Exception e) {
            return Mono.just(BaseResponse.error("Failed to get profile"));
        }
    }
}
