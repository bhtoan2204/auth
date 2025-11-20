package com.marketplace.auth.presentation.http.v1;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.marketplace.auth.presentation.http.v1.response.BaseResponse;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Mono<BaseResponse<Void>> handleRuntimeException(RuntimeException e) {
        String message = e.getMessage();

        if ("Missing or invalid authorization header".equals(message) ||
                "Invalid token".equals(message) ||
                "Invalid token payload".equals(message)) {
            return Mono.just(BaseResponse.error("Authentication failed"));
        }

        return Mono.just(BaseResponse.error("Internal server error"));
    }
}
