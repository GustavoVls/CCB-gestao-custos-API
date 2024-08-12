package com.ccbgestaocustosapi.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CadastroReunioesFiltroResponse {

    private Integer reuniaoId;

    private String reuniaoDescricao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate reuniaoData;

    @JsonFormat(pattern = "dd/MM/yyyy") // caso precisar retornar do formato normal que é yyyy-mm-dd, apenas remover
    private LocalDate reuniaoDataIni;

    @JsonFormat(pattern = "dd/MM/yyyy") // caso precisar retornar do formato normal que é yyyy-mm-dd, apenas remover
    private LocalDate reuniaoDataFim;

    private Character reuniaoStatus;
    private String reuniaoAta;

}
