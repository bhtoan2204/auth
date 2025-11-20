package com.marketplace.auth.presentation.http.v1.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
    @NotBlank String username,
    @NotBlank String email,
    @NotBlank String password) {
}
