package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CasaOracoesFiltroResponse {

    private Integer totalRecords;
    private Integer igrId;
    private String igrCod;
    private String igrNome;
    private String admNome;
    private String setorNome;
    private String igrEstado;
    private String igrCidade;
    private String igrBairro;
    private String igrCep;
    private String igrEndereco;
    private String igrComplemento;
    private Integer admId;
    private Integer setorId;
}
