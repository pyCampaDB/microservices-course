package com.mscv.hotel.hotel_service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource not found in the server");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
