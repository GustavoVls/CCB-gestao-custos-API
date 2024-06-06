package com.ccbgestaocustosapi.utils.exceptions.handlers;

import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ClassNotFoundExceptionHandler<T> implements ExceptionHandler<T> {
    @Override
    public ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage) {
        System.out.println("Handling ClassNotFoundException: " + e.getMessage());
        PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "Class not found exception: " + additionalMessage);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
