package com.ccbgestaocustosapi.controllers;


import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.models.CasaOracoes;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.models.Usuarios;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.CasaOracoesRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
import com.ccbgestaocustosapi.services.UsuariosService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioController {

    private UsuariosService usuariosService;

    private AdministracaoRepository admRepository;

    private SetoresRepository setoresRepository;

    private CasaOracoesRepository casaOracoesRepository;

    @PostMapping ("/cadastroUsuario")
    public ResponseEntity<Usuarios> createUsuario(@RequestBody Usuarios usuario){
        Administracao adm = admRepository.findById(usuario.getAdm().getAdmId()).orElse(null);
        Setores setor = setoresRepository.findById(usuario.getSetor().getSetorId()).orElse(null);
        CasaOracoes casaOracoes = casaOracoesRepository.findById(usuario.getIgr().getIgrId()).orElse(null);

        usuario.setAdm(adm);
        usuario.setSetor(setor);
        usuario.setIgr(casaOracoes);

        Usuarios salvarUsuario = usuariosService.saveUsuario(usuario);
        return  ResponseEntity.ok(salvarUsuario);
    }
}
