package com.marketplace.auth.domain.permission.exception;

import com.marketplace.auth.domain.exception.DomainException;

public class PermissionAlreadyExistsException extends DomainException {
    public PermissionAlreadyExistsException(String name) {
        super("Permission with name '%s' already exists.".formatted(name));
    }
}
