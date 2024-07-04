package com.ccbgestaocustosapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PERFIL_USUARIO", schema = "CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_perfil", "tipo_perfil"}),
                @UniqueConstraint(columnNames = {"id_perfil"}),
                @UniqueConstraint(columnNames = {"tipo_perfil"})
        })
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Perfil {

    @Id
    @Column(nullable = false, name = "id_perfil")
    private Long idPerfil;

    @Column(nullable = false, name = "tipo_perfil", length = 15)
    private String tipoPerfil;

}
