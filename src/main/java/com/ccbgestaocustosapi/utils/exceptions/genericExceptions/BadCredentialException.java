package com.ccbgestaocustosapi.utils.exceptions.genericExceptions;

public class BadCredentialException extends RuntimeException {
    public BadCredentialException(String message) {
        super(message);
    }
}