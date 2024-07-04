package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Data
@Table(name = "CRUZAMENTO_PERFIL_USUARIO", schema = "CCB",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id_perfil_usuario"),
        })
@AllArgsConstructor
@NoArgsConstructor
public class CruzamentoPerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_perfil_usuario_seq")
    @SequenceGenerator(schema = "CCB", name = "id_perfil_usuario_seq", sequenceName = "id_perfil_usuario_seq", allocationSize = 1)
    @Column(name = "id_perfil_usuario")
    private Long idPerfilUsuario;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    @Comment("fk da tabela perfil_usuario") // TODO: 27/06/2024  Aplicar comentario em todas as models de acordo com o script já criado
    private Perfil idPerfil;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios idUsuario;

}
