package com.ccbgestaocustosapi.exemplos;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api-ccb/exemplo-controller/admin")
@RestController
public class ExemploControllerAdmin {

    @GetMapping()
    public ResponseEntity<String> testeEndPointAdmin(){
        return  ResponseEntity.ok("endpoint para usuários administradores");
    }
}
