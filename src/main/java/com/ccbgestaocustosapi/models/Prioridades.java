package com.ccbgestaocustosapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRIORIDADES",schema="CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"prioridade_nome"})
        })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prioridades {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prioridades_prioridade_id_seq")
    @SequenceGenerator(schema = "CCB", name = "prioridades_prioridade_id_seq",
            sequenceName = "prioridades_prioridade_id_seq", allocationSize = 1)
    @Column(name = "prioridade_id", nullable = false)
    private Integer idCategoria;
    @Column(name = "prioridade_nome", nullable = false, length = 100)
    private String nomePrioridade;

}
