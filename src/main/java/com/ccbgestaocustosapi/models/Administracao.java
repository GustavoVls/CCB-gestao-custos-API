package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "ADMINISTRACAO")
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("ADMINISTRACAO")
@Data
public class Administracao implements Serializable {

    @Id
    @Column(name = "adm_id", nullable = false)
    private Integer admId;

    @Column(name = "adm_nome", nullable = false)
    private Integer admNome;

    @Column(name = "adm_cidade", nullable = false)
    private Integer admCidade;

    @Column(name = "adm_estado", nullable = false)
    private String admEstado;

}
