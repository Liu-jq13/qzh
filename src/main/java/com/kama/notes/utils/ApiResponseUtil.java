package com.kama.notes.utils;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.Pagination;
import com.kama.notes.model.base.PaginationApiResponse;
import com.kama.notes.model.base.TokenApiResponse;
import org.springframework.http.HttpStatus;

public class ApiResponseUtil {
    /**
     * 构建成功的响应
     *
     * @param message 响应消息
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.success(null);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.success(data);
    }

    /**
     * 构建参数错误的响应
     */
    public static <T> ApiResponse<T> error(String msg) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), msg);
    }

    /**
     * 构建 TokenApiResponse
     */
    public static <T> TokenApiResponse<T> success(String msg, T data, String token) {
        return new TokenApiResponse<>(HttpStatus.OK.value(), msg, data, token);
    }

    /**
     * 构建 PaginationApiResponse
     */
    public static <T> PaginationApiResponse<T> success(String msg, T data, Pagination pagination) {
        return new PaginationApiResponse<>(HttpStatus.OK.value(), msg, data, pagination);
    }
}
