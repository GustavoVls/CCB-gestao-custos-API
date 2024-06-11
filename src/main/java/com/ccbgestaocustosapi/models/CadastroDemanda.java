package com.ccbgestaocustosapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CADASTRO_DEMANDAS", schema = "CCB")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CadastroDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cadastro_demandas_demanda_id_seq")
    @SequenceGenerator(schema = "CCB", name = "cadastro_demandas_demanda_id_seq", sequenceName = "cadastro_demandas_demanda_id_seq", allocationSize = 1)
    @Column(name = "demanda_id", nullable = false)
    private Integer idDemanda;
    @ManyToOne
    @JoinColumn(name = "adm_id")
    private Administracao adm;
    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setores setor;
    @ManyToOne
    @JoinColumn(name = "igr_id")
    private CasaOracoes igr;
    @ManyToOne
    @JoinColumn(name = "prioridade_id")
    private Prioridades prioridade;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categorias categoria;
    @ManyToOne
    @JoinColumn(name = "reuniao_id")
    private CadastroReuniaoATDM reuniao;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuarios usuario;
    @Column(name = "demanda_dat_lct", nullable = false)
    private LocalDate datalct;
    @Column(name = "demanda_dat_reu", nullable = false)
    private LocalDate dataReuniao;
    @Column(name = "demanda_descricao", nullable = false)
    private String demandaReuniao;
    @Column(name = "demanda_vlr_est", nullable = false)
    private BigDecimal valorEstimado; // TODO: 11/06/2024  especificar a presisão de todos dados numeric
    @Column(name = "demanda_vlr_apu", nullable = false)
    private BigDecimal valorApurado;
    @Column(name = "demanda_vlr_apr", nullable = false)
    private BigDecimal valorAprovado;
    @Column(name = "demanda_vlr_cpr", nullable = false)
    private BigDecimal valorComprado;
    @Column(name = "demanda_status", nullable = false)
    private Character demandaStatus;
    @Column(name = "demanda_obs", nullable = false)
    private String demandaObs;
    @Column(name = "demanda_checklist", nullable = false)
    private Integer demandaCheckList;
}