package com.msvc.user.user_service.exceptions;
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Resource not found by server");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
