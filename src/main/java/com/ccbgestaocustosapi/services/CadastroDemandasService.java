package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.repository.CadastroDemandaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastroDemandasService {

    private final CadastroDemandaRepository cadastroDemandaRepository;


}
