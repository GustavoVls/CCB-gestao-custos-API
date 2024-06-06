package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.services.AdministracaoService;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-ccb/administracao")
@RequiredArgsConstructor
public class AdministracaoController {
    private final AdministracaoService administracaoService;

    // TODO: 04/06/2024 realizar um requestParam no get de acordo com a escolha de filtragem
    @GetMapping
    public ResponseEntity<PaginatedResponse<Administracao>> findAdministracao(@RequestParam Integer page, @RequestParam Integer size) {
        try {
            int pageValue = page - 1;
            PaginatedResponse<Administracao> response = this.administracaoService.getAllAdministracao(pageValue, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados da administração");
        }
    }



}
