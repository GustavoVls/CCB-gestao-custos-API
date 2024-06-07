package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.UsuariosRequest;
import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SetoresService {
    private final SetoresRepository setoresRepository;

    private final AdministracaoRepository administracaoRepository;
    public PaginatedResponse<Setores> getAllSetores(int page, Integer size) {
        Page<Setores> setoresPage = this.setoresRepository.findAll(PageRequest.of(page, size));
        return new PaginatedResponse<>(setoresPage.getContent(), setoresPage.getTotalElements());
    }

    public PaginatedResponse<Setores> getByIdSetores(Integer id) {
        Optional<Setores> resultId = this.setoresRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
    }

    public void createNewSetores( UsuariosRequest usuariosRequest)  {
        Optional<Administracao> adm = administracaoRepository.findById(usuariosRequest.getAdmId());
        if (adm.isPresent()){
            Setores setor = new Setores();
            setor.setSetorNome(usuariosRequest.getSetorNome());
            setor.setAdm(adm.get());
            this.setoresRepository.save(setor);
        }else {
            throw new InternalError("adm_id não encontrado para realizar a vinculação com esse setor");
        }
    }
}
