package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Prioridades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrioridadesRepository extends JpaRepository<Prioridades, Integer> {
    @Query(value =
            """
                    SELECT
                                           COUNT(*) OVER() AS total_records,
                                           s.prioridade_id , s.prioridade_nome\s
                                       FROM
                                           ccb.prioridades s         \s
                                           WHERE
                                           UPPER(s.prioridade_nome)  LIKE UPPER('%' ||:nomePrioridade ||'%')
                    """
            , nativeQuery = true)
    List<Object[]> findByNomePrioridade(@Param("nomePrioridade") String nomePrioridade);


    @Query(value =
            """
                    SELECT
                                  COUNT(*) OVER() AS total_records,
                                  s.prioridade_id , s.prioridade_nome\s
                                       FROM
                                           ccb.prioridades s         \s
                                           WHERE
                                           UPPER(s.prioridade_nome)  LIKE UPPER('%' ||:nomePrioridade ||'%')
                        order by :orderBy :ascDescValue""", nativeQuery = true)
    List<Object[]> findByNomePrioridadeOrderBy(@Param("nomePrioridade") String descricao, @Param("orderBy") String orderBY, @Param("ascDescValue") String asdDescValue);

}
