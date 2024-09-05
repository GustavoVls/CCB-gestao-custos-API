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
    private String nomeUsuario;
    private String emailUsuario;
    private String senha;
    private Integer admId;
    private Integer setorId;
    private Integer igrId;
//    private Boolean usuarioAdm;
    private Integer idPerfil;
}

