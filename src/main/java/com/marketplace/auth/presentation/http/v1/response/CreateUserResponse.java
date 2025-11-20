package com.marketplace.auth.presentation.http.v1.response;

public record CreateUserResponse(
                Integer userId,
                String username,
                String email) {
}
