package com.marketplace.auth.presentation.http.response;

public record CreateUserResponse(
        Integer userId,
        String username,
        String email
) {
}
