package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.dto.CasaOracoesFiltroResponse;
import com.ccbgestaocustosapi.dto.CasaOracoesRequest;
import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.models.CasaOracoes;
import com.ccbgestaocustosapi.services.CasaOracoesService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/casa-oracoes")
@RequiredArgsConstructor
public class CasaOracoesController {

    private final CasaOracoesService casaOracoesService;


    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> findSetores(@RequestParam Integer page,
                                                            @RequestParam Integer size,
                                                            @RequestParam(required = false) String nomeIgreja,
                                                            @RequestParam(required = false) String codIgreja,
                                                            @RequestParam(required = false) String valueOrderBY,
                                                            @RequestParam(required = false) boolean isOrderByAsc,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.substring(7); // Pega o token após 'Bearer '
        // caso não tenha nenhum filtro, ele realizar um getAll
        if (nomeIgreja == null && codIgreja == null) {
            PaginatedResponse<CasaOracoes> response = this.casaOracoesService.getAllCasaOracoes(page, size, valueOrderBY, isOrderByAsc, isOrderByAsc ? "asc" : "desc", token);
            return ResponseEntity.ok(response);
        }
        PaginatedResponse<CasaOracoesFiltroResponse> response = this.casaOracoesService.getByCasaOracoes(nomeIgreja, codIgreja, valueOrderBY, isOrderByAsc, token);
        return ResponseEntity.ok(response);


    }

    @GetMapping(path = "dropdown-setor")
    public ResponseEntity<?> dropdownSetor() {
        return ResponseEntity.ok(this.casaOracoesService.getDropDownSetor());
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse<CasaOracoes>> createNewCasaOracoes(@RequestBody CasaOracoesRequest casaOracoesRequest) {
        try {
            this.casaOracoesService.createNewCasaOracoes(casaOracoesRequest);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Casa de oração cadastraada com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Não foi possível cadastrar essa casa de oração.");
        }
    }


    @PutMapping
    public ResponseEntity<PaginatedResponse<CasaOracoes>> updateCasaOracoes(@RequestBody CasaOracoesRequest casaOracoesRequest) {
        try {
            this.casaOracoesService.updateCasaOracoes(casaOracoesRequest);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campos atualizados com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }


    @DeleteMapping
    public ResponseEntity<PaginatedResponse<Administracao>> deleteCasaOracao(@RequestParam Integer id) {
        try {
            this.casaOracoesService.deleteCasaOracao(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Casa de Oração deletada com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }


}
