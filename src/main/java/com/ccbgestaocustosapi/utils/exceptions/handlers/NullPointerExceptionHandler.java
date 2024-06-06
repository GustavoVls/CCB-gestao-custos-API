package com.ccbgestaocustosapi.utils.exceptions.handlers;

import com.ccbgestaocustosapi.utils.exceptions.ExceptionHandler;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NullPointerExceptionHandler<T> implements ExceptionHandler<T> {
    @Override
    public ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage) {
        System.out.println("Handling NullPointerException: " + e.getMessage());
        PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "Null pointer exception: " + additionalMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
