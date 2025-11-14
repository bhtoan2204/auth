package com.marketplace.auth.domain.auth.exception;

import com.marketplace.auth.domain.exception.DomainException;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super("Invalid username/email or password.");
    }
}
