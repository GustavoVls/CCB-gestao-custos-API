package com.ccbgestaocustosapi.utils;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private final String errorMessage;
    private List<T> items;
    private long totalRecords;

    public PaginatedResponse(List<T> items, long totalRecords, String errorMessage) {
        this.items = items;
        this.totalRecords = totalRecords;
        this.errorMessage = errorMessage;
    }


}