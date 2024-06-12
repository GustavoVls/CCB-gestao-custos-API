package com.ccbgestaocustosapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CadastroDemandaRequest {
    private Integer idDemanda;
    private Integer admId;
    private Integer setorId;
    private Integer igrId;
    private Integer prioridadeId;
    private Integer categoriaId;
    private Integer reuniaoId;
    private Integer usuarioId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datalct;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataReuniao;
    private String demandaReuniao;
    private BigDecimal valorEstimado;
    private BigDecimal valorApurado;
    private BigDecimal valorAprovado;
    private BigDecimal valorComprado;
    private Character demandaStatus;
    private String demandaObs;
    private Integer demandaCheckList;

}
