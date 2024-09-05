package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.UsuariosResponse;
import com.ccbgestaocustosapi.repository.CruzamentoPerfilUsuarioRepository;
import com.ccbgestaocustosapi.repository.UsersRepository;
import com.ccbgestaocustosapi.token.TokenRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuariosService {

    private final UsersRepository usersRepository;

    private final CruzamentoPerfilUsuarioRepository cruzamentoPerfilUsuarioRepository;

    private  final TokenRepository tokenRepository;

    public PaginatedResponse<UsuariosResponse> getAllService(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc, String nomeUsuario) {
        List<Object[]> resultId = null;


        if (valueOrderBY != null) {

            if (isOrderByAsc) {
                resultId = this.usersRepository.findAllUsuariosOrderbyAsc(pageValue, size, valueOrderBY);
            } else {
                resultId = this.usersRepository.findAllUsuariosOrderbyDesc(pageValue, size, valueOrderBY);
            }

        }

        if (nomeUsuario == null && valueOrderBY == null) {
            resultId = this.usersRepository.findAllUsuarios(pageValue, size);
        }

        if (nomeUsuario != null) {
            resultId = this.usersRepository.findByNomeUsuario(nomeUsuario);
        }


        List<UsuariosResponse> usuariosDTOS = new ArrayList<>();


        for (Object[] resultado : resultId) {
            Integer totalRecords = ((Number) resultado[0]).intValue();
            Integer idUsuario = ((Number) resultado[1]).intValue();
            String nomeUsuarios = ((String) resultado[2]);
            String emailUsuario = ((String) resultado[3]);
            String nomeAdm = ((String) resultado[4]);
            String nomeSetor = (String) resultado[5];
            String nomeIgr = (String) resultado[6];
            UsuariosResponse dto = new UsuariosResponse(idUsuario, nomeUsuarios, emailUsuario, nomeAdm, nomeSetor, nomeIgr, totalRecords);
            usuariosDTOS.add(dto);
        }

        return new PaginatedResponse<>(usuariosDTOS.stream().toList(), 0);
    }

    @Transactional
    public void deletarUsuario(Integer idUsuario) {
        try {
            cruzamentoPerfilUsuarioRepository.deleteByIdUsuario(idUsuario);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        try {
            tokenRepository.deleteByIdUsuario(idUsuario);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        try {
            usersRepository.deleteById(idUsuario);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
