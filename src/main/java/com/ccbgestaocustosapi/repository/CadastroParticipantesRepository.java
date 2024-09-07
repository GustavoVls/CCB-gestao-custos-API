package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.CadastroParticipantesATDM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CadastroParticipantesRepository extends JpaRepository<CadastroParticipantesATDM, Integer> {


    @Query(value =
            """                      
                               SELECT
                                                  COUNT(*) OVER() AS total_records,
                                                         cpa.id_participantes,
                                                         cpa.par_nome,
                                                         cpa.par_cargo,
                                                         cpa.par_comum,
                                                         cra.reuniao_descricao,
                                                         cra.reuniao_id
                                                      FROM
                                                     ccb.cadastro_participantes_atdm cpa  inner join ccb.cadastro_reuniao_atdm cra   on cpa.reuniao_id  = cra.reuniao_id\s
                                                  WHERE
                                                     upper(cpa.par_nome)  like upper('%' || :nomeParticipante ||'%')
                                                     and cra.adm_id =:admId
                    """
            , nativeQuery = true)
    List<Object[]> findByNomeParticipantes(@Param("nomeParticipante") String nomeParticipante, @Param("admId") Integer admId);


    @Query(value =
            """                      
                               SELECT
                                                  COUNT(*) OVER() AS total_records,
                                                         cpa.id_participantes,
                                                         cpa.par_nome,
                                                         cpa.par_cargo,
                                                         cpa.par_comum,
                                                         cra.reuniao_descricao,
                                                         cra.reuniao_id
                                                      FROM
                                                     ccb.cadastro_participantes_atdm cpa  inner join ccb.cadastro_reuniao_atdm cra   on cpa.reuniao_id  = cra.reuniao_id\s
                                                  WHERE
                                                     cra.adm_id = :admId
                    """
            , nativeQuery = true)
    List<Object[]> findByAdmId(@Param("admId") Integer admId);


    @Query(value =
            """
                                 \s
                                       SELECT
                                               COUNT(*) OVER() AS total_records,
                                                                       cpa.id_participantes,
                                                                        cpa.par_nome,
                                                                        cpa.par_cargo,                                                                                  cpa.par_comum,
                                                                        cra.reuniao_descricao,
                                                                        cra.reuniao_id
                                                                               FROM
                                                                              ccb.cadastro_participantes_atdm cpa  inner join ccb.cadastro_reuniao_atdm cra   on cpa.reuniao_id  = cra.reuniao_id\\s
                                                                           WHERE
                                                                              upper(cpa.par_nome)  like upper('%' || :nomeParticipante ||'%')
                                        order by :orderBy :ascDescValue
                    """
            , nativeQuery = true)
    List<Object[]> findByNomeParticipantesOrderBy(@Param("nomeParticipante") String nomeParticipante, @Param("orderBy") String orderBY, @Param("ascDescValue") String asdDescValue);



    @Query(value =
            """
                                       select
                                                     co.igr_nome\s
                                                       FROM
                                                     ccb.casa_oracoes co where  co.adm_id = :admId \s
             """, nativeQuery = true)
    List<Object[]> findAllDropdownComum(@Param("admId") Integer admId);


    @Query(value =
            """
                                    select\s
                                             cra.reuniao_id, cra.reuniao_descricao\s
                                              from\s
                                    ccb.cadastro_reuniao_atdm cra where cra.adm_id = :admId\s
             """, nativeQuery = true)
    List<Object[]> findAllDropdownReuniao(@Param("admId") Integer admId);
}
