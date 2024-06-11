package com.ccbgestaocustosapi.dto;

import lombok.Data;

@Data
public class CasaOracoesRequest {
    private Integer igrId;
    private  Integer setorId;
    private  Integer admId;
    private  String igrCod;
    private String igrNome;
    private String igrEndereco;
    private String igrCep;
    private String igrCidade;
    private String igrEstado;
    private String igrComplemento;
    private String igrBairro;
}
