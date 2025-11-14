package com.marketplace.auth.domain.auth.exception;

import com.marketplace.auth.domain.exception.DomainException;

public class RefreshTokenInvalidException extends DomainException {
    public RefreshTokenInvalidException() {
        super("Refresh token is invalid or expired.");
    }
}
