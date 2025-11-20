package com.marketplace.auth.application.exceptions;

public class AccountInactiveException extends AuthException {

    public AccountInactiveException() {
        super("Account is inactive");
    }

    public AccountInactiveException(String message) {
        super(message);
    }

    public AccountInactiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
