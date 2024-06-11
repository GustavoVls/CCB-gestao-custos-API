package com.ccbgestaocustosapi.dto;

import lombok.Data;

@Data
public class ParticipantesAtdmRequest {
    private Integer reuniaoId;
    private String nomeParticipante;
    private String cargoParticipante;
    private String comumParticipante;
}
