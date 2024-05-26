package com.ccbgestaocustosapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SETORES")
@DiscriminatorValue("SETORES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setores {
    @Id
    @Column(name = "setor_id", nullable = false)
    private Integer setorId;

    @ManyToOne
    @JoinColumn(name = "adm_id", referencedColumnName = "adm_id")
    private Administracao adm;

    @ManyToOne
    @JoinColumn(name = "setor_id", referencedColumnName = "setor_id")
    private String setorNome;
}
