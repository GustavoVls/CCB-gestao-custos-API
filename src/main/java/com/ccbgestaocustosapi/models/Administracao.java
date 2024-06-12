package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name = "ADMINISTRACAO", schema ="CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"adm_nome", "adm_cidade", "adm_estado"}),
                @UniqueConstraint(columnNames = {"adm_nome"}),
                @UniqueConstraint(columnNames = {"adm_cidade"}),
                @UniqueConstraint(columnNames = {"adm_estado"})
        })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Administracao implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "administracao_adm_id_seq")
    @SequenceGenerator(schema = "CCB", name = "administracao_adm_id_seq", sequenceName = "administracao_adm_id_seq", allocationSize = 1)
    @Column(name = "adm_id", nullable = false)
    private Integer admId;

    @Column(name = "adm_nome", nullable = false, length = 100)
    private String admNome;

    @Column(name = "adm_cidade", nullable = false, length = 100)
    private String admCidade;

    @Column(name = "adm_estado", nullable = false, length = 2)
    private String admEstado;

}
