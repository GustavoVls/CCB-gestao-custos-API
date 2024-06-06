package com.ccbgestaocustosapi.utils.exceptions;

import com.ccbgestaocustosapi.utils.PaginatedResponse;
import org.springframework.http.ResponseEntity;

public interface ExceptionHandler<T> {
    ResponseEntity<PaginatedResponse<T>> handle(Exception e, String additionalMessage);
}