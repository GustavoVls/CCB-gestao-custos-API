package com.ccbgestaocustosapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "demanda_dat_lct", nullable = false)
    private LocalDate datalct;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "demanda_data_reu", nullable = false)
    private LocalDate dataReuniao;
    @Column(name = "demanda_descricao", nullable = false)
    private String demandaReuniao;
    @Column(name = "demanda_vlr_est", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorEstimado;
    @Column(name = "demanda_vlr_apu", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorApurado;
    @Column(name = "demanda_vlr_apr", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorAprovado;
    @Column(name = "demanda_vlr_cpr", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorComprado;
    @Column(name = "demanda_status", nullable = false)
    private Character demandaStatus;
    @Column(name = "demanda_obs", nullable = false)
    private String demandaObs;
    @Column(name = "demanda_checklist", nullable = false)
    private Integer demandaCheckList;
}