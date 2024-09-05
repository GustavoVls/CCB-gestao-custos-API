package com.ccbgestaocustosapi.controllers;

import com.ccbgestaocustosapi.services.DropdownsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api-ccb/dropdowns")
@RequiredArgsConstructor
public class DropdownsController {

    private  final DropdownsService dropdownsService;


    @GetMapping(path = "/setor")
    public ResponseEntity<?> dropdownSetor(@RequestParam Integer admId) {
        return ResponseEntity.ok(this.dropdownsService.getDropDownSetor(admId));
    }


    @GetMapping(path = "casa-de-oracao")
    public ResponseEntity<?> dropdownComum(@RequestParam Integer admId, @RequestParam Integer setorId) {
        return ResponseEntity.ok(this.dropdownsService.getDropdownCasaDeOracao(admId,setorId));
    }


}
