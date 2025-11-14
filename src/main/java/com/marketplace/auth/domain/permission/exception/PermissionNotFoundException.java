package com.marketplace.auth.domain.permission.exception;

import com.marketplace.auth.domain.exception.DomainException;

public class PermissionNotFoundException extends DomainException {
    public PermissionNotFoundException(Long id) {
        super("Permission with id %d was not found.".formatted(id));
    }
}
