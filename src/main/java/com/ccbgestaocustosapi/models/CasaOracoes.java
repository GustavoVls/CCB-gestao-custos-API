package com.ccbgestaocustosapi.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CASA_ORACOES")
@DiscriminatorValue("CASA_ORACOES")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CasaOracoes {
    @Id
    @Column(name = "igr_id", nullable = false)
    private Integer igrId;

    @JoinColumn(name = "setor_id", referencedColumnName = "setor_id")
    private Setores setor;

    @ManyToOne
    @JoinColumn(name = "adm_id", referencedColumnName = "adm_id")
    private Administracao adm;

    @Column(name = "igr_cod", nullable = false)
    private String igrCod;

    @Column(name = "igr_endereco", nullable = false)
    private String igrEndereco;

    @Column(name = "igr_bairro", nullable = false)
    private String igrBairro;

    @Column(name = "igr_cep", nullable = false)
    private String igrCep;

    @Column(name = "igr_cidade", nullable = false)
    private String igrCidade;

    @Column(name = "igr_estado", nullable = false)
    private String igrEstado;

    @Column(name = "igr_complemento", nullable = false)
    private String igrComplento;
}
