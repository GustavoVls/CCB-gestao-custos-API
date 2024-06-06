package com.ccbgestaocustosapi.utils.exceptions.handlers;

import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SQLExceptionHandler<T> implements ExceptionHandler<T> {
    @Override
    public ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage) {
        System.out.println("Handling SQLException: " + e.getMessage());
        PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "SQL exception: " + additionalMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}