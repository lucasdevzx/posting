package com.posting.post.services.exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(Object object, String msg) {
        super(msg);
    }
}
