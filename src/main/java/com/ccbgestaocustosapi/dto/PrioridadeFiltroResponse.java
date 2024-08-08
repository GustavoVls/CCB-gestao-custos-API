package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrioridadeFiltroResponse {
    private Integer idPrioridade;
    private String nomePrioridade;

    private Integer totalRecords;

}
