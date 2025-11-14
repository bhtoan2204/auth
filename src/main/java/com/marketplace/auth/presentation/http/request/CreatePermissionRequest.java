package com.marketplace.auth.presentation.http.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePermissionRequest(@NotBlank String name) {
}
