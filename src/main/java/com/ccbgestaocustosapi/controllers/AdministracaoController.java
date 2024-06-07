package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.services.AdministracaoService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/administracao")
@RequiredArgsConstructor
public class AdministracaoController {
    private final AdministracaoService administracaoService;

    // TODO: 04/06/2024 realizar um requestParam no get de acordo com a escolha de filtragem
    @GetMapping
    public ResponseEntity<PaginatedResponse<Administracao>> findAdministracao(@RequestParam Integer page,
                                                                              @RequestParam Integer size,
                                                                              @RequestParam(required = false) Integer id) {
        try {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (id == null) {
                int pageValue = page - 1;
                PaginatedResponse<Administracao> response = this.administracaoService.getAllAdministracao(pageValue, size);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<Administracao> response = this.administracaoService.getbyIdAdministracao(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados da administração.");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse<Administracao>> createNewAdministracao(@RequestBody Administracao administracao) {
        try {
            this.administracaoService.createNewAdministracao(administracao);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Administração cadastrada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Não foi possível cadastrar essa administração.");
        }
    }

    @PutMapping
    public ResponseEntity<PaginatedResponse<Administracao>> updateAdministracao(@RequestBody Administracao administracao) {
        try {
            this.administracaoService.updateAdministracao(administracao);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campos atualizados com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<PaginatedResponse<Administracao>> deleteAdministracao(@RequestParam Integer id) {
        try {
            this.administracaoService.deleteAdministracao(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Administração deletada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }
}
