package com.ccbgestaocustosapi.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "CADASTRO_REUNIAO_ATDM", schema = "CCB")
@AllArgsConstructor
@NoArgsConstructor
public class CadastroReuniaoATDM {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cadastro_reuniao_atdm_reuniao_id_seq")
    @SequenceGenerator(schema = "CCB", name = "cadastro_reuniao_atdm_reuniao_id_seq",
            sequenceName = "cadastro_reuniao_atdm_reuniao_id_seq", allocationSize = 1)
    @Column(name = "reuniao_id", nullable = false)
    private Integer reuniaoId;

    @Column(name = "reuniao_descricao", nullable = false)
    private String reuniaoDescricao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "reuniao_data", nullable = false)
    private LocalDate reuniaoData;

    @JsonFormat(pattern = "dd/MM/yyyy")

    @Column(name = "reuniao_data_ini", nullable = false)
    private LocalDate reuniaoDataIni;

    @JsonFormat(pattern = "dd/MM/yyyy") // caso precisar retornar do formato normal que é yyyy-mm-dd, apenas remover

    @Column(name = "reuniao_data_fim", nullable = false)
    private LocalDate reuniaoDataFim;

    @Column(name = "reuniao_status", nullable = false, length = 1)
    private Character reuniaoStatus;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "reuniao_ata", nullable = false, columnDefinition = "TEXT")
    private String reuniaoAta;

}