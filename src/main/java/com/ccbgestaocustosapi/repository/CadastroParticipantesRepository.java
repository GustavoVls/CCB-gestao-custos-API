package com.ccbgestaocustosapi.repository;

import com.ccbgestaocustosapi.models.CadastroParticipantesATDM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroParticipantesRepository extends JpaRepository<CadastroParticipantesATDM, Integer> {
}
