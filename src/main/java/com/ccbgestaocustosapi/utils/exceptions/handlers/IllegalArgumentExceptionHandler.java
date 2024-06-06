package com.ccbgestaocustosapi.utils.exceptions.handlers;

import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class IllegalArgumentExceptionHandler<T> implements ExceptionHandler<T> {
    @Override
    public ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage) {
        System.out.println("Handling IllegalArgumentException: " + e.getMessage());
        PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "Illegal argument exception: " + additionalMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}