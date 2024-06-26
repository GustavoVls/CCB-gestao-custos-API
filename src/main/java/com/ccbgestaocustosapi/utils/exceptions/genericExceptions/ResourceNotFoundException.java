package com.ccbgestaocustosapi.utils.exceptions.genericExceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}