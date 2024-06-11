package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;
    private Integer idAdm;
    private Integer idSetor;
    private Integer idIgr;
    private Character usuarioAdm;
}