package com.ccbgestaocustosapi.repository;


import com.ccbgestaocustosapi.models.Setores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetoresRepository extends JpaRepository<Setores, Integer> {
}
