package com.ccbgestaocustosapi.utils.exceptions.handlers;

import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestExceptionHandler<T> implements ExceptionHandler<T> {
    @Override
    public ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage) {
        if (e instanceof ResponseStatusException responseStatusException) {
            if (responseStatusException.getStatusCode() == HttpStatus.BAD_REQUEST) {
                System.out.println("Handling BadRequestException: " + responseStatusException.getMessage());
                PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "Bad request: " + additionalMessage);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        // Caso a exceção não seja do tipo esperado, retorna um erro interno do servidor
        PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "Unexpected exception: " + additionalMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}