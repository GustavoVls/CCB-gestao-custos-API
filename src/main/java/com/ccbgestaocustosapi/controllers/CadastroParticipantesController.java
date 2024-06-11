package com.ccbgestaocustosapi.controllers;

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
    public ResponseEntity<PaginatedResponse<CadastroParticipantesATDM>> findParticipantesAtdm(@RequestParam Integer page,
                                                                                              @RequestParam Integer size,
                                                                                              @RequestParam(required = false) Integer id) {
        try {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (id == null) {
                int pageValue = page - 1;
                PaginatedResponse<CadastroParticipantesATDM> response = this.cadastroParticipantesService.getAllParticipantes(pageValue, size);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<CadastroParticipantesATDM> response = this.cadastroParticipantesService.getByIdParticipantes(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados de setores.");
        }
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

}
