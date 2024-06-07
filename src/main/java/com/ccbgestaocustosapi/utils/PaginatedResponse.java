package com.ccbgestaocustosapi.utils;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {

    private final String successMessage;
    private final String errorMessage;
    private List<T> items;
    private long totalRecords;

    // Main constructor with all parameters
    public PaginatedResponse(String successMessage, List<T> items, long totalRecords, String errorMessage) {
        this.successMessage = successMessage;
        this.items = items;
        this.totalRecords = totalRecords;
        this.errorMessage = errorMessage;
    }

    // Overloaded constructor without successMessage
    public PaginatedResponse(List<T> items, long totalRecords, String errorMessage) {
        this(null, items, totalRecords, errorMessage);
    }

    // You may want to add another constructor with fewer parameters if needed
    public PaginatedResponse(List<T> items, long totalRecords) {
        this(null, items, totalRecords, null);
    }
    public PaginatedResponse(String successMessage, List<T> items, long totalRecords) {
        this(successMessage, items, totalRecords, null);
    }
}