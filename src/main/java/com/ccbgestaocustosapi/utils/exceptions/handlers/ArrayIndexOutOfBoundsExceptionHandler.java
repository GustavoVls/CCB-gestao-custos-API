package com.ccbgestaocustosapi.utils.exceptions.handlers;

import com.ccbgestaocustosapi.utils.exceptions.ExceptionHandler;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ArrayIndexOutOfBoundsExceptionHandler<T> implements ExceptionHandler<T> {
    @Override
    public ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage) {
        System.out.println("Handling ArrayIndexOutOfBoundsException: " + e.getMessage());
        PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "Array index out of bounds: " + additionalMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}