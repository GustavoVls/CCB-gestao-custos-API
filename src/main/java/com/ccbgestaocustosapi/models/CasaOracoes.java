package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CASA_ORACOES", schema="CCB",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"igr_nome", "igr_endereco", "igr_bairro", "igr_cep", "igr_cidade", "igr_estado","igr_complemento"}),
        @UniqueConstraint(columnNames = {"igr_nome"}),
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@SqlResultSetMapping(
        name = "CasaOracoesWithCount",
        entities = @EntityResult(entityClass = CasaOracoes.class),
        columns = @ColumnResult(name = "total_records")
)
public class CasaOracoes {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "igr_id_seq")
    @SequenceGenerator(schema = "CCB", name = "igr_id_seq", sequenceName = "igr_id_seq", allocationSize = 1)
    @Column(name = "igr_id", nullable = false)
    private Integer igrId;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    private Setores setor;

    @ManyToOne
    @JoinColumn(name = "adm_id")
    private Administracao adm;

    @Column(name = "igr_cod", nullable = false, length = 10)
    private String igrCod;

    @Column(name = "igr_nome", nullable = false, length = 100)
    private String igrNome;
    @Column(name = "igr_endereco", nullable = false, length = 100)
    private String igrEndereco;

    @Column(name = "igr_bairro", nullable = false, length = 100)
    private String igrBairro;

    @Column(name = "igr_cep", nullable = false, length = 10)
    private String igrCep;

    @Column(name = "igr_cidade", nullable = false, length = 100)
    private String igrCidade;

    @Column(name = "igr_estado", nullable = false, length = 2)
    private String igrEstado;

    @Column(name = "igr_complemento", nullable = false, length = 100)
    private String igrComplemento;
}
