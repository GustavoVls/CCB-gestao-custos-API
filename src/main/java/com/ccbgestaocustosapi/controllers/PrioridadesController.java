package com.ccbgestaocustosapi.controllers;

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
    public ResponseEntity<PaginatedResponse<Prioridades>> findPrioridades(@RequestParam Integer page,
                                                                          @RequestParam Integer size,
                                                                          @RequestParam(required = false) Integer id) {
        try {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (id == null) {
                int pageValue = page - 1;
                PaginatedResponse<Prioridades> response = this.prioridadesService.getAllPrioridades(pageValue, size);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<Prioridades> response = this.prioridadesService.getbyPrioridades(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados de Prioridades.");
        }
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
