package com.ccbgestaocustosapi.dto;

import lombok.Data;

@Data
public class CadastroReunioesRequest {
    private String reuniaoDescricao;
    private String reuniaoDataIni;
    private String reuniaoDataFim;
    private Character reuniaoStatus;
    private String reuniaoAta;

}
