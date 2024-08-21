package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.CasaOracoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasaOracoesRepository extends JpaRepository<CasaOracoes, Integer> {


    @Query(value =
            """                      
                    select
                     COUNT(*) OVER() AS total_records,
                            co.igr_id,
                            co.igr_cod,
                            co.igr_nome,
                            a.adm_nome,
                            s.setor_nome,
                            co.igr_estado,
                            co.igr_estado,
                            co.igr_cidade,
                            co.igr_estado,
                            co.igr_cidade,
                            co.igr_bairro,
                            co.igr_cep,
                            co.igr_endereco,
                            co.igr_complemento,
                            a.adm_id,
                            s.setor_id,
                            from
                            ccb.casa_oracoes co
                            inner join ccb.administracao a on
                            co.adm_id = a.adm_id
                            inner join ccb.setores s on
                            co.setor_id = s.setor_id
                            where
                            upper (co.igr_nome) like upper('%' || :nomeIgreja || '%')
                    """
            , nativeQuery = true)
    List<Object[]> findByNomeIgreja(@Param("nomeIgreja") String nomeIgreja);


    @Query(value =
            """
                         select
                          COUNT(*) OVER() AS total_records,
                                 co.igr_id,
                                 co.igr_cod,
                                 co.igr_nome,
                                 a.adm_nome,
                                 s.setor_nome,
                                 co.igr_estado,
                                 co.igr_estado,
                                 co.igr_cidade
                                 co.igr_estado,
                                 co.igr_cidade,
                                 co.igr_bairro,
                                 co.igr_cep,
                                 co.igr_endereco,
                                 co.igr_complemento,
                                 a.adm_id,
                                 s.setor_id,
                                 from
                                 ccb.casa_oracoes co
                                 inner join ccb.administracao a on
                                 co.adm_id = a.adm_id
                                 inner join ccb.setores s on
                                 co.setor_id = s.setor_id
                                 where
                                 upper (co.igr_nome) like upper('%' || :nomeIgreja || '%')
                                        order by :orderBy :ascDescValue
                    """
            , nativeQuery = true)
    List<Object[]> findByNomeIgrejaOrderBy(@Param("nomeIgreja") String nomeIgreja, @Param("orderBy") String orderBY, @Param("ascDescValue") String asdDescValue);


    @Query(value =
            """
                               select s.setor_id,
                                      s.setor_nome\s
                                       from ccb.setores s
                                                                       
                    """, nativeQuery = true)
    List<Object[]> findAllDropdownSetor();


}
