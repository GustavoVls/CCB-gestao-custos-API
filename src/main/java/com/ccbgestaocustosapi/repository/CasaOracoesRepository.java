package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.CasaOracoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasaOracoesRepository extends JpaRepository<CasaOracoes, Integer> {
}
