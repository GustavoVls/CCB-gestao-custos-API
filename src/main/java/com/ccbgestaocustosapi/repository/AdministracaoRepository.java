package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Administracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
