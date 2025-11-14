package com.marketplace.auth.presentation.http;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marketplace.auth.domain.auth.exception.InactiveUserException;
import com.marketplace.auth.domain.auth.exception.InvalidCredentialsException;
import com.marketplace.auth.domain.auth.exception.RefreshTokenInvalidException;
import com.marketplace.auth.domain.exception.DomainException;
import com.marketplace.auth.domain.permission.exception.PermissionAlreadyExistsException;
import com.marketplace.auth.domain.permission.exception.PermissionNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PermissionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(PermissionNotFoundException ex) {
        return ApiErrorResponse.from(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(PermissionAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleConflict(PermissionAlreadyExistsException ex) {
        return ApiErrorResponse.from(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler({ InvalidCredentialsException.class, RefreshTokenInvalidException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleUnauthorized(RuntimeException ex) {
        return ApiErrorResponse.from(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(InactiveUserException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleInactiveUser(InactiveUserException ex) {
        return ApiErrorResponse.from(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));
        return ApiErrorResponse.from(HttpStatus.BAD_REQUEST, "Validation failed", details);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
        return ApiErrorResponse.from(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleDomainException(DomainException ex) {
        return ApiErrorResponse.from(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private record ApiErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message,
            Map<String, String> details) {
        private static ApiErrorResponse from(HttpStatus status, String message) {
            return from(status, message, Collections.emptyMap());
        }

        private static ApiErrorResponse from(HttpStatus status, String message, Map<String, String> details) {
            return new ApiErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message, details);
        }
    }
}
