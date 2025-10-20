package com.posting.post.services.exceptions;

public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(Object obj) {
        super("Acess Not Permitted!");
    }
}


