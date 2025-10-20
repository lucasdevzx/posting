package com.posting.post.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Object obj) {
        super("Resource Not Found!");
    }
}
