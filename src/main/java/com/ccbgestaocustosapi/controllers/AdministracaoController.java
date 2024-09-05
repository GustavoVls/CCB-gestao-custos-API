package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.dto.AdministracaoFiltroResponse;
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

    // TODO: 28/08/2024 Validar todos os selects de acordo com o usuario logado, exibir somnete a administração que ele está logado , porém na tela de ADM será exibido
    //  todos as novas administrações
    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> findAdministracao(@RequestParam Integer page,
                                                                              @RequestParam Integer size,
                                                                              @RequestParam(required = false) String nomeAdm,
                                                                              @RequestParam(required = false) String valueOrderBY,
                                                                              @RequestParam(required = false) boolean isOrderByAsc) {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (nomeAdm == null) {
                int pageValue = page - 1;
                PaginatedResponse<Administracao> response = this.administracaoService.getAllAdministracao(pageValue, size, valueOrderBY, isOrderByAsc);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<AdministracaoFiltroResponse> response = this.administracaoService.getbyNomeAdministracao(nomeAdm);
            return ResponseEntity.ok(response);

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
