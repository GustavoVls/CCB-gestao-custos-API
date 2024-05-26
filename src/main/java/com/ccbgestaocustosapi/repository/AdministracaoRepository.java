package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Administracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministracaoRepository extends JpaRepository<Administracao, Integer> {
}
