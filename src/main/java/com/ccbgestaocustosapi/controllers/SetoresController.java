package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.dto.SetoresFiltroResponse;
import com.ccbgestaocustosapi.dto.UsuariosRequest;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.services.SetoresService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/setores")
@RequiredArgsConstructor
public class SetoresController {

    private final SetoresService setoresService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> findSetores(@RequestParam Integer page,
                                                            @RequestParam Integer size,
                                                            @RequestParam(required = false) String nomeSetor,
                                                            @RequestParam(required = false) String valueOrderBY,
                                                            @RequestParam(required = false) boolean isOrderByAsc,
                                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        PaginatedResponse<SetoresFiltroResponse> response;
        String token = authorizationHeader.substring(7); // Pega o token após 'Bearer '

        if (nomeSetor == null) {

            int pageValue = page - 1;
            response = this.setoresService.getAllSetores(pageValue, size, valueOrderBY, isOrderByAsc, token);
            return ResponseEntity.ok(response);
        }
        response = this.setoresService.getByNomeSetores(nomeSetor, valueOrderBY, isOrderByAsc, token);
        return ResponseEntity.ok(response);

    }

    @GetMapping(path = "dropdown-adm")
    public ResponseEntity<?> dropdownAdm() {
        return ResponseEntity.ok(this.setoresService.getDropdownAdm());
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
