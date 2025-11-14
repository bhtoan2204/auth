package com.marketplace.auth.presentation.http.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePermissionRequest(@NotBlank String name) {
}
