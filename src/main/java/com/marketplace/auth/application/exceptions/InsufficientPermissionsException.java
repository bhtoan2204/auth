package com.marketplace.auth.application.exceptions;

public class InsufficientPermissionsException extends AuthException {

    public InsufficientPermissionsException() {
        super("Insufficient permissions");
    }

    public InsufficientPermissionsException(String message) {
        super(message);
    }

    public InsufficientPermissionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
