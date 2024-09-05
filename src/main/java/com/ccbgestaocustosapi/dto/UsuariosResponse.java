package com.ccbgestaocustosapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosResponse {

    private Integer idUsuario;

    private String nomeUsuario;

    private String emailUsuario;

    private String nomeAdm;

    private  String nomeSetor;

    private  String nomeIgr;

    private Integer totalRecords;

}
