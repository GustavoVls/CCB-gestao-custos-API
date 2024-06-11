package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORIAS",schema="CCB")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorias_id_categoria_seq")
    @SequenceGenerator(schema = "CCB", name = "categorias_id_categoria_seq",
            sequenceName = "categorias_id_categoria_seq", allocationSize = 1)
    @Column(name = "id_categoria", nullable = false)
    private Integer idCategoria;
    @Column(name = "descricao_categoria")
    private String descricaoCategoria;
    @Column(name = "tipo_categoria")
    private String tipoCategoria;
}
