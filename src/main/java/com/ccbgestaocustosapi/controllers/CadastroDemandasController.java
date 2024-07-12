package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.dto.CadastroDemandaRequest;
import com.ccbgestaocustosapi.models.CadastroDemanda;
import com.ccbgestaocustosapi.services.CadastroDemandasService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/cadastro-demandas")
@RequiredArgsConstructor
public class CadastroDemandasController {

    private final CadastroDemandasService cadastroDemandasService;


    @GetMapping
    public ResponseEntity<PaginatedResponse<CadastroDemanda>> findDemandas(@RequestParam Integer page,
                                                                           @RequestParam Integer size,
                                                                           @RequestParam(required = false) Integer id,
                                                                           @RequestParam (required = false) String valueOrderBY,
                                                                           @RequestParam(required = false) boolean isOrderByAsc ) {
        try {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (id == null) {
                int pageValue = page - 1;
                PaginatedResponse<CadastroDemanda> response = this.cadastroDemandasService.getAllDemandas(pageValue, size, valueOrderBY, isOrderByAsc);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<CadastroDemanda> response = this.cadastroDemandasService.getbyIdDemandas(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados da administração.");
        }
    }


    @PostMapping
    public ResponseEntity<PaginatedResponse<CadastroDemanda>> createNewCadastroDemanda(@RequestBody CadastroDemandaRequest cadastroDemandaRequest) {
        try {
            this.cadastroDemandasService.createNewCadastroDemanda(cadastroDemandaRequest);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Demanda cadastrada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, null);
        }
    }

    @PutMapping
    public ResponseEntity<PaginatedResponse<CadastroDemanda>> updateDemanda(@RequestBody CadastroDemandaRequest cadastroDemandaRequest) {
        try {
            this.cadastroDemandasService.updateDemanda(cadastroDemandaRequest);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campos atualizados com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<PaginatedResponse<CadastroDemanda>> deleteDemanda(@RequestParam Integer id) {
        try {
            this.cadastroDemandasService.deleteDemanda(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Administração deletada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }


}
