package com.cyan.curioserver.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.data = data;
        result.code = 0;
        result.message = "success";
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 0;
        result.message = "success";
        return result;
    }
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<T>();
        result.code = 1;
        result.message = message;
        return result;
    }
}
