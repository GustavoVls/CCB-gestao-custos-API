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
    @Column(name = "par_nome")
    private String descricaoCategoria;
    @Column(name = "par_cargo")
    private String cargoParticipante;
    @Column(name = "par_comum")
    private String tipoCategoria;}
