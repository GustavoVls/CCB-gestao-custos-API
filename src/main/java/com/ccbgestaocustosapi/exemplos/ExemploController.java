package com.ccbgestaocustosapi.exemplos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/exemplo-controller")
@RestController

public class ExemploController {


    @GetMapping()
    public ResponseEntity<ExemploRequest> testeEndpoint(){

        ExemploRequest exemploResponse = new ExemploRequest();

        exemploResponse.setValor("endpoint para usuários em geral com token");
        return  ResponseEntity.ok(exemploResponse);
    }
}
