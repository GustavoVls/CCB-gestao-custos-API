package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilUsuarioRepository extends JpaRepository<Perfil, Integer> {

    @Query(value = """


            select pu.tipo_perfil from ccb.perfil_usuario pu \s
            where pu.id_perfil  = :idPerfil
            """, nativeQuery = true)
    List<?> findTipoPerfilByIdPerfil(@Param("idPerfil") Integer idPerfil);
}
