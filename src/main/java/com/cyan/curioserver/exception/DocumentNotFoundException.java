package com.cyan.curioserver.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(){
        super("资料不存在");
    }

}
