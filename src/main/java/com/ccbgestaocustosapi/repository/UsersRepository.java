package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Usuarios, Integer> {

    Optional<Usuarios> findByEmail(String email);

    Boolean existsByNome(String nome);

    Boolean existsByEmail(String email);

}
