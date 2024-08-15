package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.dto.CadastroParticipantesATDMResponse;
import com.ccbgestaocustosapi.dto.ParticipantesAtdmRequest;
import com.ccbgestaocustosapi.models.CadastroParticipantesATDM;
import com.ccbgestaocustosapi.services.CadastroParticipantesService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/cadastro-participantes")
@RequiredArgsConstructor
public class CadastroParticipantesController {

    private final CadastroParticipantesService cadastroParticipantesService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> findParticipantesAtdm(@RequestParam Integer page,
                                                                      @RequestParam Integer size,
                                                                      @RequestParam(required = false) String nomeParticipante,
                                                                      @RequestParam(required = false) String valueOrderBY,
                                                                      @RequestParam(required = false) boolean isOrderByAsc) {
        // caso não tenha nenhum filtro, ele realizar um getAll
        if (nomeParticipante == null) {
            int pageValue = page - 1;
            PaginatedResponse<CadastroParticipantesATDM> response = this.cadastroParticipantesService.getAllParticipantes(pageValue, size, valueOrderBY, isOrderByAsc);
            return ResponseEntity.ok(response);
        }
        PaginatedResponse<CadastroParticipantesATDMResponse> response = this.cadastroParticipantesService.getByNomeParticipantes(nomeParticipante, valueOrderBY, isOrderByAsc);
        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<PaginatedResponse<CadastroParticipantesATDM>> createNewParticipantesAtdm(@RequestBody ParticipantesAtdmRequest participantesAtdmRequest) {
        try {
            this.cadastroParticipantesService.createNewParticipantes(participantesAtdmRequest);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Participante cadastrado com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Não foi possível cadastrar esse setor.");
        }
    }

    @PutMapping
    public ResponseEntity<PaginatedResponse<CadastroParticipantesATDM>> updateParticipantes(@RequestBody CadastroParticipantesATDM CadastroParticipantesATDM) {
        try {
            this.cadastroParticipantesService.updateParticipantes(CadastroParticipantesATDM);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campos do Partcipante foram atualizados com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<PaginatedResponse<CadastroParticipantesATDM>> deleteParticipantes(@RequestParam Integer id) {
        try {
            this.cadastroParticipantesService.deleteParticipantes(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Participante deletado com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }


    @GetMapping(path = "dropdown-comum")
    public ResponseEntity<?> dropdownComum() {
        return ResponseEntity.ok(this.cadastroParticipantesService.getDropdownComum());
    }

    @GetMapping(path = "dropdown-reuniao")
    public ResponseEntity<?> dropdownReuniao() {
            return ResponseEntity.ok(this.cadastroParticipantesService.getDropdownReuniao());
    }
}
