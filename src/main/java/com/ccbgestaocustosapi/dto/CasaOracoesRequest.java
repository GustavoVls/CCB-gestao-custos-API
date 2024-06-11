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
    private Character igrCep;
    private String igrCidade;
    private Character igrEstado;
    private String igrComplemento;
    private String igrBairro;
}
