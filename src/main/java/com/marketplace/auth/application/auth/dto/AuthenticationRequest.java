package com.marketplace.auth.application.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
    @NotBlank String usernameOrEmail,
    @NotBlank String password
) { }
