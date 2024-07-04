package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.dto.MenuAcessoResponse;
import com.ccbgestaocustosapi.services.MenuAcessoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/menu-acesso")
public class MenuAcessoController {


    private final MenuAcessoService menuAcessoService;


    @GetMapping("/valida-visualizacao-menu")
    public ResponseEntity<?> getVisualizacaoMenu(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Pega o token após 'Bearer '
            List<MenuAcessoResponse> visualizacoesMenu = menuAcessoService.visualizacoesMenu(token);
            return ResponseEntity.ok(visualizacoesMenu);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token não fornecido");
        }
    }


}
