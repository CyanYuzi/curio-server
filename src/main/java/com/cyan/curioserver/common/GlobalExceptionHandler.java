package com.cyan.curioserver.common;

import com.cyan.curioserver.exception.DocumentNotFoundException;
import com.cyan.curioserver.exception.LoginFailedException;
import com.cyan.curioserver.exception.NotLoggedInException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleIllegalArgument(
            IllegalArgumentException exception){
        return Result.error(exception.getMessage());
    }
    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<Void> handleDocumentNotFound(
            DocumentNotFoundException exception){
        return Result.error(exception.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleIllegalState(
            IllegalStateException exception){
        log.error("资料处理失败",exception);
        return Result.error("资料处理失败，请稍后重试");
    }
    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<Void> handleLoginFailed(LoginFailedException exception){
        return Result.error(exception.getMessage());
    }
    @ExceptionHandler(NotLoggedInException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<Void> handleNotLoggedIn(NotLoggedInException exception){
        return Result.error(exception.getMessage());
    }
}
