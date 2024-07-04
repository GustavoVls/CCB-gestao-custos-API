package com.ccbgestaocustosapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "CRUZAMENTO_PERFIL_ACESSO", schema = "CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id_perfil_acesso"),
        })
@AllArgsConstructor
@NoArgsConstructor
public class CruzamentoPerfilAcesso {

    @Id
    @Column(name = "id_perfil_acesso")
    private Long idPerfilAcesso;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil idPerfil;

    @ManyToOne
    @JoinColumn(name = "id_acesso")
    private SistemaAcessos idAcesso;
}
