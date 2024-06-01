package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.dto.AuthenticationRequest;
import com.ccbgestaocustosapi.dto.AuthenticationResponse;
import com.ccbgestaocustosapi.dto.RegistrerRequest;
import com.ccbgestaocustosapi.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;


    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<AuthenticationResponse> cadastarUsuario(@RequestBody RegistrerRequest request){
     return    ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> cadastarUsuario(@RequestBody AuthenticationRequest request){
         return         ResponseEntity.ok(authenticationService.authenticate(request));

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}