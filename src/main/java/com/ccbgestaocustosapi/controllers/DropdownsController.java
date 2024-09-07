package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.dto.dropdowns.AdmDropdownResponse;
import com.ccbgestaocustosapi.dto.dropdowns.SetorDropdownResponse;
import com.ccbgestaocustosapi.services.DropdownsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api-ccb/dropdowns")
@RequiredArgsConstructor
public class DropdownsController {

    private final DropdownsService dropdownsService;


    @GetMapping(path = "/setor")
    public ResponseEntity<?> dropdownSetor(@RequestParam Integer admId) {
        return ResponseEntity.ok(this.dropdownsService.getDropDownSetor(admId));
    }

    @GetMapping(path = "dropdown-setor")
    public ResponseEntity<?> dropdownSetorSemParamAdmId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        List<SetorDropdownResponse> result = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Pega o token após 'Bearer '
            result = this.dropdownsService.dropdownSetorSemParamAdmId(token);

        }
        return ResponseEntity.ok(result);
    }


    @GetMapping(path = "casa-de-oracao")
    public ResponseEntity<?> dropdownComum(@RequestParam Integer admId, @RequestParam Integer setorId) {
        return ResponseEntity.ok(this.dropdownsService.getDropdownCasaDeOracao(admId, setorId));
    }


    @GetMapping(path = "dropdown-adm")
    public ResponseEntity<?> dropdownAdm(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        List<AdmDropdownResponse> visualizacoesMenu = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Pega o token após 'Bearer '
            visualizacoesMenu = this.dropdownsService.getDropdownAdm(token);

        }
        return ResponseEntity.ok(visualizacoesMenu);
    }


}
