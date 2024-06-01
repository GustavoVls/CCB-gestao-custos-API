package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrerRequest {

    private String nome;
    private String email;
    private String senha;
    private Integer idAdm;
    private Integer idSetor;

    private Integer idIgr;
    private String usuarioAdm;
}
