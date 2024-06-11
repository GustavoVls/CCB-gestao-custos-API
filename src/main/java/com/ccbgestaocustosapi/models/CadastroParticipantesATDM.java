package com.ccbgestaocustosapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "CADASTRO_PARTICIPANTES_ATDM", schema = "CCB")
@AllArgsConstructor
@NoArgsConstructor
public class CadastroParticipantesATDM {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cadastro_participantes_atdm_id_participantes_seq")
    @SequenceGenerator(schema = "CCB", name = "cadastro_participantes_atdm_id_participantes_seq",
            sequenceName = "cadastro_reuniao_atdm_reuniao_id_seq", allocationSize = 1)
    @Column(name = "id_participantes", nullable = false)
    private Integer idParticipantes;

    @ManyToOne
    @JoinColumn(name = "reuniao_id")
    private CadastroReuniaoATDM reuniaoId;

    @Column(name = "par_nome", length = 100)
    private String nomeParticipante;

    @Column(name = "par_cargo", length = 100)
    private String cargoParticipante;

    @Column(name = "par_comum", length = 100)
    private String comumParticipante;
}