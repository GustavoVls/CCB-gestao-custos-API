package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Administracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministracaoRepository extends JpaRepository<Administracao, Integer> {


    @Query(value =
            """
                                    select
                                             a.adm_id , a.adm_nome
                                                    FROM
                                                    ccb.administracao a
                    """, nativeQuery = true)
    List<Object[]> findAllDropdownAdm();


    @Query(value =
            """
                                    select
                                             a.adm_id , a.adm_nome
                                                    FROM
                                                    ccb.administracao a where a.adm_id = :admId
                    """, nativeQuery = true)
    List<Object[]> findAllDropdownAdmId(@Param("admId") Integer admId);


    @Query(value =
            """
                    \s
                    	
                 select
                    	COUNT(*) over() as total_records,
                    	a.adm_id,
                    	a.adm_nome,
                    	a.adm_cidade,
                    	a.adm_estado
                    from
                    	ccb.administracao a
                    where
                    	upper(a.adm_nome) like upper('%' || :nomeAdm || '%')  \s
                    """
            , nativeQuery = true)
    List<Object[]> findByNomeAdm(@Param("nomeAdm") String nomeAdm);
}
