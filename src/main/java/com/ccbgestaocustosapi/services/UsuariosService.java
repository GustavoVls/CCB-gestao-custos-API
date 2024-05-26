package com.ccbgestaocustosapi.services;


import com.ccbgestaocustosapi.models.Usuarios;
import com.ccbgestaocustosapi.repository.UsuariosRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosService {

    private UsuariosRepository usuariosRepository;

    public Usuarios saveUsuario(Usuarios usuario){
        return  usuariosRepository.save(usuario);
    }
}
