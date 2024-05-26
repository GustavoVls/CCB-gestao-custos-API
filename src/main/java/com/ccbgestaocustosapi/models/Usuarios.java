package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "USUARIOS")
@DiscriminatorValue("USUARIOS")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;


    @ManyToOne
    @JoinColumn(name = "adm_id", referencedColumnName = "adm_id")
    private Administracao adm;

    @ManyToOne
    @JoinColumn(name = "setor_id", referencedColumnName = "setor_id")
    private Setores setor;

    @ManyToOne
    @JoinColumn(name = "igr_id", referencedColumnName = "igr_id")
    private CasaOracoes igr;

    @Column(name = "nome_usuario", nullable = false)
    private String nome;
    @Column(name = "senha_usuario", nullable = false)
    private String senha;

    @Column(name = "email_usuario", nullable = false)
    private String email;

    @Column(name = "usuario_adm", nullable = false)
    private String usuarioAdm;

}