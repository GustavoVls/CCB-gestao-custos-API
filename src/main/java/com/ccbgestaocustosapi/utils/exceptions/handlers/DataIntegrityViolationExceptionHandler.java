package com.ccbgestaocustosapi.utils.exceptions.handlers;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataIntegrityViolationException;

public class DataIntegrityViolationExceptionHandler<T> implements ExceptionHandler<T> {
    @Override
    public ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage) {
        if (e instanceof DataIntegrityViolationException) {
            System.out.println("Handling DataIntegrityViolationException: " + e.getMessage());
            PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, additionalMessage);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } else {
            // Caso a exceção não seja do tipo esperado, retorna um erro interno do servidor
            PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, "Unexpected exception " + additionalMessage);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}