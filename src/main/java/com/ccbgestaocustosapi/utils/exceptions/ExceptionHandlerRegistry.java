package com.ccbgestaocustosapi.utils.exceptions;

import com.ccbgestaocustosapi.utils.exceptions.handlers.*;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerRegistry {
    private static final Map<ExceptionType, Class<? extends ExceptionHandler>> handlers = new HashMap<>();

    static {
        handlers.put(ExceptionType.NULL_POINTER_EXCEPTION, NullPointerExceptionHandler.class);
        handlers.put(ExceptionType.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION, ArrayIndexOutOfBoundsExceptionHandler.class);
        handlers.put(ExceptionType.ILLEGAL_ARGUMENT_EXCEPTION, IllegalArgumentExceptionHandler.class);
        handlers.put(ExceptionType.IO_EXCEPTION, IOExceptionHandler.class);
        handlers.put(ExceptionType.FILE_NOT_FOUND_EXCEPTION, FileNotFoundExceptionHandler.class);
        handlers.put(ExceptionType.CLASS_NOT_FOUND_EXCEPTION, ClassNotFoundExceptionHandler.class);
        handlers.put(ExceptionType.SQL_EXCEPTION, SQLExceptionHandler.class);
        handlers.put(ExceptionType.DATA_INTEGRITY_VIOLATION_EXCEPTION, DataIntegrityViolationExceptionHandler.class);
        handlers.put(ExceptionType.INTERNAL_ERROR, InternalErrorHandler.class);
        handlers.put(ExceptionType.BAD_REQUEST_EXCEPTION, BadRequestExceptionHandler.class);

        // Adicione outras classes conforme necessário
    }

    @SneakyThrows
    public static ExceptionHandler getHandler(ExceptionType exceptionType) throws IllegalAccessException, InstantiationException{
        Class<? extends ExceptionHandler> handlerClass = handlers.get(exceptionType);
        if (handlerClass != null) {
            return handlerClass.getDeclaredConstructor().newInstance();
        } else {
            return null;
        }
    }
}
