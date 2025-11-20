package com.marketplace.auth.presentation.http.v1.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Long timestamp;

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, null, data, System.currentTimeMillis());
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(true, message, data, System.currentTimeMillis());
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(false, message, null, System.currentTimeMillis());
    }

    public static <T> BaseResponse<T> error(String message, T data) {
        return new BaseResponse<>(false, message, data, System.currentTimeMillis());
    }
}
