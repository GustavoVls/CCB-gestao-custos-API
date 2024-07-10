package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.dto.AuthenticationRequest;
import com.ccbgestaocustosapi.dto.AuthenticationResponse;
import com.ccbgestaocustosapi.dto.RegisterRequest;
import com.ccbgestaocustosapi.services.authService.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;


    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<AuthenticationResponse> cadastarUsuario(@RequestBody RegisterRequest request){
     return    ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> loginUsuario(@RequestBody AuthenticationRequest request){
        authenticationService.authenticate(request);
       return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
    @GetMapping("/verificar-codigo-acesso")
    public ResponseEntity<AuthenticationResponse> getAcessoToken(@RequestParam(name = "codigo") String codigo) {
        return ResponseEntity.ok(authenticationService.verificaCodigoAcesso(codigo));
    }

    @GetMapping("/valida-usuario-senha")
    public ResponseEntity<?> getValidaUsuario(@RequestParam(name = "email") String email,
                                                                   @RequestParam(name = "senha") String senha) {
        return ResponseEntity.ok(authenticationService.validaUsuario(email,senha));
    }


}