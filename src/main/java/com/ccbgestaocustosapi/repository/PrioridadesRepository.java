package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Prioridades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioridadesRepository extends JpaRepository<Prioridades, Integer> {
}
