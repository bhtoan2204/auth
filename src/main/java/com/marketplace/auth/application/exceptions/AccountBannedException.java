package com.marketplace.auth.application.exceptions;

public class AccountBannedException extends AuthException {

    public AccountBannedException() {
        super("Account is banned");
    }

    public AccountBannedException(String message) {
        super(message);
    }

    public AccountBannedException(String message, Throwable cause) {
        super(message, cause);
    }
}
