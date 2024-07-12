package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.models.CadastroReuniaoATDM;
import com.ccbgestaocustosapi.services.CadastroReuniaoService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/cadastro-reuniao")
@RequiredArgsConstructor
public class CadastroReuniaoController {
    private final CadastroReuniaoService cadastroReuniaoService;


    @GetMapping
    public ResponseEntity<PaginatedResponse<CadastroReuniaoATDM>> findReunioesCadastradas(@RequestParam Integer page,
                                                                                          @RequestParam Integer size,
                                                                                          @RequestParam(required = false) Integer id,
                                                                                          @RequestParam (required = false) String valueOrderBY,
                                                                                          @RequestParam(required = false) boolean isOrderByAsc ) {
        try {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (id == null) {
                int pageValue = page - 1;
                PaginatedResponse<CadastroReuniaoATDM> response = this.cadastroReuniaoService.getAllReunioesCadastradas(pageValue, size, valueOrderBY, isOrderByAsc);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<CadastroReuniaoATDM> response = this.cadastroReuniaoService.getbyIdReunioesCadastradas(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados da Reuniões cadastradas.");
        }
    }



    @PostMapping
    public ResponseEntity<PaginatedResponse<CadastroReuniaoATDM>> createNewCadastroReuniao(@RequestBody CadastroReuniaoATDM cadastroReuniaoATDM) {
        try {
            this.cadastroReuniaoService.createNewCadastroReuniao(cadastroReuniaoATDM);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Reunião cadastrada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Não foi possível cadastrar essa reunião.");
        }
    }


    @PutMapping
    public ResponseEntity<PaginatedResponse<CadastroReuniaoATDM>> updateCadastroReuniao(@RequestBody CadastroReuniaoATDM cadastroReuniaoATDM) {
        try {
            this.cadastroReuniaoService.updateCadastroReuniao(cadastroReuniaoATDM);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campos atualizados com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<PaginatedResponse<CadastroReuniaoATDM>> deleteReuniaoCadastrada(@RequestParam Integer id) {
        try {
            this.cadastroReuniaoService.deleteReuniaoCadastrado(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Reunião deletada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }
}