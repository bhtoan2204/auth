package com.marketplace.auth.presentation.http.v1.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for refreshing authentication tokens.
 */
public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
}