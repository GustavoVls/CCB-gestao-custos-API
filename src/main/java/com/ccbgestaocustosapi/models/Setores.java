package com.ccbgestaocustosapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SETORES", schema="CCB")
@DiscriminatorValue("SETORES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setores {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "setores_setor_id_seq")
    @SequenceGenerator(schema = "CCB", name = "setores_setor_id_seq", sequenceName = "setores_setor_id_seq", allocationSize = 1)
    @Column(name = "setor_id")
    private Integer setorId;

    @ManyToOne
    @JoinColumn(name = "adm_id")
    private Administracao adm;

    @Column(name = "setor_nome", nullable = false)
    private String setorNome;
}
