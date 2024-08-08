package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Integer> {

    @Query(value =
            """
                    SELECT\s
                        COUNT(*) OVER() AS total_records,
                        c.id_categoria ,
                        c.descricao_categoria ,
                        c.tipo_categoria\s
                    FROM\s
                        ccb.categorias c\s
                    WHERE\s
                        UPPER(c.descricao_categoria)  LIKE UPPER('%' ||:descricao ||'%')
                        group by   c.id_categoria ,
                        c.descricao_categoria ,
                        c.tipo_categoria\s
                    """
, nativeQuery = true)
    List<Object[]> findByDescricao(@Param("descricao") String descricao);


    @Query(value =
            """
                    SELECT\s
                        COUNT(*) OVER() AS total_records,
                        c.id_categoria ,
                        c.descricao_categoria ,
                        c.tipo_categoria
                    FROM\s
                        ccb.categorias c\s
                    WHERE\s
                        UPPER (c.descricao_categoria)  LIKE UPPER('%' || :descricao || '%')
                        group by   c.id_categoria ,
                        c.descricao_categoria ,
                        c.tipo_categoria
                        order by :orderBy :ascDescValue""", nativeQuery = true)
    List<Object[]> findByDescricaoOrderBy(@Param("descricao") String descricao, @Param("orderBy") String orderBY, @Param("ascDescValue") String asdDescValue);
}
