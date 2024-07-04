package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilUsuarioRepository extends JpaRepository<Perfil, Integer> {
}
