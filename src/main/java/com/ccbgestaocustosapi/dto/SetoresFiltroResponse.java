package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetoresFiltroResponse {
    private Integer setorId;
    private Integer admId;
    private String setorNome;
    private String admNome;
    private Integer totalRecords;
}
