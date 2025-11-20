package com.marketplace.auth.application.exceptions;

public class AccountLockedException extends AuthException {

    public AccountLockedException() {
        super("Account is locked");
    }

    public AccountLockedException(String message) {
        super(message);
    }

    public AccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }
}
