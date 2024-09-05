package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministracaoFiltroResponse {
    private Integer admId;
    private String admNome;
    private String admCidade;
    private String admEstado;
    private Integer totalRecords;
}
