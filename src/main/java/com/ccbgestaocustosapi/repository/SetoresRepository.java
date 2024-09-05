package com.ccbgestaocustosapi.repository;


import com.ccbgestaocustosapi.models.Setores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetoresRepository extends JpaRepository<Setores, Integer> {


    @Query(value =
            """                      
                    \s
                               SELECT
                                COUNT(*) OVER() AS total_records,
                                    s.setor_id,
                                    s.adm_id,
                                    s.setor_nome,
                                    a.adm_nome\s
                                    FROM
                                   ccb.setores s inner join ccb.administracao a  on a.adm_id  = s.adm_id \s
                                WHERE
                                   upper(s.setor_nome)  like upper('%' || :nomeSetor ||'%')
                    """
            , nativeQuery = true)
    List<Object[]> findByNomeSetor(@Param("nomeSetor") String nomeSetor);


    @Query(value =
            """
                    \s
                               SELECT
                                COUNT(*) OVER() AS total_records,
                                    s.setor_id,
                                    s.adm_id,
                                    s.setor_nome,
                                    a.adm_nome\s
                                    FROM
                                   ccb.setores s inner join ccb.administracao a  on a.adm_id  = s.adm_id \s
                                WHERE
                                   upper(s.setor_nome)  like upper('%'|| :nomeSetor ||'%')
                                   order by :orderBy :ascDescValue
               """
            , nativeQuery = true)
    List<Object[]> findByNomeSetorOrderBy(@Param("nomeSetor") String nomeSetor,  @Param("orderBy") String orderBY, @Param("ascDescValue") String asdDescValue);


    @Query(value =
            """
                                 	
                    select s.setor_id,
                                    s.setor_nome, s.adm_id\s
                          from ccb.setores s where s.adm_id  = :admId
                                                                       
                    """, nativeQuery = true)
    List<Object[]> findAllDropdownSetorByAdm(@Param("admId") Integer admId);

    @Query(value =
            """
                                                           select
                                                                                        co.igr_id,
                                                                                                      co.igr_nome
                                                                                                     FROM
                                                                                             ccb.casa_oracoes co\s
                                                                         where co.adm_id  = :admId and co.setor_id  = :setorId
                                                                                         
                    """, nativeQuery = true)
    List<Object[]> findCasaDeOracaoByAdmSetorId(@Param("admId") Integer admId, @Param("setorId") Integer setorId);



}
