package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.services.CadastroDemandasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-ccb/cadastro-demandas")
@RequiredArgsConstructor
public class CadastroDemandasController {


    private final CadastroDemandasService cadastroDemandasService;



}
