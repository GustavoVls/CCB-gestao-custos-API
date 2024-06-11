package com.ccbgestaocustosapi.repository;


import com.ccbgestaocustosapi.models.CadastroReuniaoATDM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadastroReuniaoRepository extends JpaRepository<CadastroReuniaoATDM, Integer> {

}
