package com.marketplace.auth.application.exceptions;

public class AuthenticationException extends AuthException {

    public AuthenticationException() {
        super("Invalid username or password");
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
