package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.CruzamentoPerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CruzamentoPerfilUsuarioRepository extends JpaRepository<CruzamentoPerfilUsuario, Integer> {
}
