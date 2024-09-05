package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.dto.AuthenticationResponse;
import com.ccbgestaocustosapi.dto.RegisterRequest;
import com.ccbgestaocustosapi.dto.UsuariosResponse;
import com.ccbgestaocustosapi.models.Usuarios;
import com.ccbgestaocustosapi.services.UsuariosService;
import com.ccbgestaocustosapi.services.authService.AuthenticationService;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.CentralExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api-ccb/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuariosService;

    private final AuthenticationService authenticationService;



    @GetMapping
    public ResponseEntity<PaginatedResponse<?>> findService(@RequestParam Integer page,
                                                                  @RequestParam Integer size,
                                                                  @RequestParam(required = false) String nomeUsuario,
                                                                  @RequestParam(required = false) String valueOrderBY,
                                                                  @RequestParam(required = false) boolean isOrderByAsc) {

        PaginatedResponse<UsuariosResponse>  response = this.usuariosService.getAllService(page, size, valueOrderBY, isOrderByAsc,nomeUsuario);
        return ResponseEntity.ok(response);

    }


    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<AuthenticationResponse> cadastarUsuario(@RequestBody RegisterRequest request){
        return    ResponseEntity.ok(authenticationService.register(request));
    }


    @DeleteMapping("/deletar-usuario")
    public ResponseEntity<PaginatedResponse<Usuarios>> deletarUsuario(@RequestParam Integer idUsuario) {
        try {
            this.usuariosService.deletarUsuario(idUsuario);
            return ResponseEntity.ok(new PaginatedResponse<>
                    ("Administração deletada com sucesso", null, 0));
        } catch (Exception e) {
            return CentralExceptionHandler.handleException(e, e.getMessage());
        }
    }



}
