package com.cyan.curioserver.exception;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException() {
        super("请先登录");
    }
}
