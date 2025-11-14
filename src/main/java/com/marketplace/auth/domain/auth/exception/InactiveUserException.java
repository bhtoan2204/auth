package com.marketplace.auth.domain.auth.exception;

import com.marketplace.auth.domain.exception.DomainException;

public class InactiveUserException extends DomainException {
    public InactiveUserException() {
        super("User account is inactive or banned.");
    }
}
