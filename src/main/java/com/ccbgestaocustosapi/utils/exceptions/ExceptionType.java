package com.ccbgestaocustosapi.utils.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionType {
    NULL_POINTER_EXCEPTION("Erro de NullPointerExcption"),
    ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION("Array index is out of bounds"),
    ARITHMETIC_EXCEPTION("Arithmetic error"),
    ILLEGAL_ARGUMENT_EXCEPTION("Illegal argument provided"),
    IO_EXCEPTION("Input/output error"),
    FILE_NOT_FOUND_EXCEPTION("Arquivo não encotrado"),
    CLASS_NOT_FOUND_EXCEPTION("Classe não encontrada"),
    SQL_EXCEPTION("erro de sql");


    private final String message;

    ExceptionType(String message) {
        this.message = message;
    }

}
