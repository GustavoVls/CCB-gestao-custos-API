package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.dto.UsuariosRequest;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.services.SetoresService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/setores")
@RequiredArgsConstructor
public class SetoresController {

    private final SetoresService setoresService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<Setores>> findSetores(@RequestParam Integer page,
                                                                  @RequestParam Integer size,
                                                                  @RequestParam(required = false) Integer id) {
        try {
            // caso não tenha nenhum filtro, ele realizar um getAll
            if (id == null) {
                int pageValue = page - 1;
                PaginatedResponse<Setores> response = this.setoresService.getAllSetores(pageValue, size);
                return ResponseEntity.ok(response);
            }
            PaginatedResponse<Setores> response = this.setoresService.getByIdSetores(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Erro na busca de dados de setores.");
        }
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse<Setores>> createNewSetores(@RequestBody UsuariosRequest usuariosRequest) {
        try {
            this.setoresService.createNewSetores(usuariosRequest);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Setor cadastrado com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, "Não foi possível cadastrar esse setor.");
        }
    }


    @PutMapping
    public ResponseEntity<PaginatedResponse<Setores>> updateSetores(@RequestBody Setores setores) {
        try {
            this.setoresService.updateSetores(setores);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Campo do Setor foi atualizado com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }


    @DeleteMapping
    public ResponseEntity<PaginatedResponse<Setores>> deleteSetores(@RequestParam Integer id) {
        try {
            this.setoresService.deleteSetor(id);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Setor deletado com sucesso.", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }


}
