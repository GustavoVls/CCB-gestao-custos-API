package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.CadastroDemanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroDemandaRepository  extends JpaRepository<CadastroDemanda, Integer> {
}
