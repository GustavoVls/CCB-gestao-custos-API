package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.dto.PrioridadeFiltroResponse;
import com.ccbgestaocustosapi.models.Prioridades;
import com.ccbgestaocustosapi.services.PrioridadesService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/prioridades")
@RequiredArgsConstructor
public class PrioridadesController {


    private final PrioridadesService prioridadesService;


    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> findPrioridades(@RequestParam Integer page,
                                                                          @RequestParam Integer size,
                                                                          @RequestParam(required = false) String nomePrioridade,
                                                                          @RequestParam (required = false) String valueOrderBY,
                                                                          @RequestParam(required = false) boolean isOrderByAsc ) {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (nomePrioridade == null) {
                int pageValue = page - 1;
                PaginatedResponse<Prioridades> response = this.prioridadesService.getAllPrioridades(pageValue, size, valueOrderBY, isOrderByAsc);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<PrioridadeFiltroResponse> response = this.prioridadesService.getbyPrioridades(nomePrioridade,valueOrderBY, isOrderByAsc );
            return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<PaginatedResponse<Prioridades>> createNewPrioridade(@RequestBody Prioridades prioridades) {
        try {
            this.prioridadesService.createNewPrioridade(prioridades);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Prioridade cadastrada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Não foi possível cadastrar essa Prioridade.");
        }
    }

    @PutMapping
    public ResponseEntity<PaginatedResponse<Prioridades>> updatePrioridade(@RequestBody Prioridades prioridades) {
        try {
            this.prioridadesService.updatePrioridade(prioridades);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campos atualizados com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<PaginatedResponse<Prioridades>> deletePrioridade(@RequestParam Integer id) {
        try {
            this.prioridadesService.deletePrioridade(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Prioridade deletada com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }
}
