package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.CruzamentoPerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CruzamentoPerfilUsuarioRepository extends JpaRepository<CruzamentoPerfilUsuario, Integer> {
    @Modifying
    @Query(value = "delete from ccb.cruzamento_perfil_usuario s where s.id_usuario = :idUsuario"

            ,nativeQuery = true)
    void deleteByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
