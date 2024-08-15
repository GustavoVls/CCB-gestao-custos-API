package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastroParticipantesATDMResponse {
    private Integer totalRecords;
    private Integer idParticipantes;
    private String nomeParticipante;
    private String cargoParticipante;
    private String comumParticipante;
    private String reuniaoDescricao;

    private Integer idReuniao;


}
