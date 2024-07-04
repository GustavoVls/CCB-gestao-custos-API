package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SISTEMA_ACESSOS", schema = "CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_acesso", "nome_acesso"}),
                @UniqueConstraint(columnNames = {"id_acesso"}),
                @UniqueConstraint(columnNames = {"nome_acesso"})

        })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SistemaAcessos {

    @Id
    @Column(nullable = false, name = "id_acesso")
    private Long idAcesso;

    @Column(nullable = false, name = "nome_acesso", length = 120)
    private String nomeAcesso;

}
