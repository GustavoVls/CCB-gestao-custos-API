package com.ccbgestaocustosapi.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DataConverter {
    // Converte a string para LocalDateTime no início do dia
    public static LocalDateTime convertStringToDateTimeStartOfDay(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return date.atStartOfDay();
    }

    // Converte a string para LocalDateTime no fim do dia
    public static LocalDateTime convertStringToDateTimeEndOfDay(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return date.atTime(23, 59, 59);
    }
}
