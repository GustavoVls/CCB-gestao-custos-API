package com.ccbgestaocustosapi.utils.exceptions;

import com.ccbgestaocustosapi.utils.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CentralExceptionHandler {
    public static <T> ResponseEntity<PaginatedResponse<T>> handleException(Exception e, String additionalMessage) {
        ExceptionType exceptionType = null;

        if (e instanceof NullPointerException) {
            exceptionType = ExceptionType.NULL_POINTER_EXCEPTION;
        } else if (e instanceof ArrayIndexOutOfBoundsException) {
            exceptionType = ExceptionType.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION;
        } else if (e instanceof ArithmeticException) {
            exceptionType = ExceptionType.ARITHMETIC_EXCEPTION;
        } else if (e instanceof IllegalArgumentException) {
            exceptionType = ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION;
        } else if (e instanceof java.io.IOException) {
            if (e instanceof java.io.FileNotFoundException) {
                exceptionType = ExceptionType.FILE_NOT_FOUND_EXCEPTION;
            } else {
                exceptionType = ExceptionType.IO_EXCEPTION;
            }
        } else if (e instanceof ClassNotFoundException) {
            exceptionType = ExceptionType.CLASS_NOT_FOUND_EXCEPTION;
        } else if (e instanceof java.sql.SQLException) {
            exceptionType = ExceptionType.SQL_EXCEPTION;
        }

        if (exceptionType != null) {
            try {
                ExceptionHandler handler = ExceptionHandlerRegistry.getHandler(exceptionType);
                if (handler != null) {
                    return handler.handle(e, additionalMessage);
                } else {
                    System.out.println("No handler found for exception type: " + exceptionType);
                    return createErrorResponse("No handler found for exception type: " + exceptionType);
                }
            } catch (InstantiationException | IllegalAccessException exception) {
                System.out.println("Error instantiating handler for exception type: " + exceptionType);
                return createErrorResponse("Error handling exception: " + exception.getMessage());
            }
        } else {
            System.out.println("An unknown exception occurred: " + e.getMessage());
            return createErrorResponse("An unknown exception occurred: " + e.getMessage());
        }
    }

    // Método utilitário para criar uma resposta de erro padrão com ResponseEntity
    private static <T> ResponseEntity<PaginatedResponse<T>> createErrorResponse(String errorMessage) {
        PaginatedResponse<T> response = new PaginatedResponse<>(null, 0, errorMessage);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}