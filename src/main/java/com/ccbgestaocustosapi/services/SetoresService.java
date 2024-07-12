package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.UsuariosRequest;
import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SetoresService {
    private final SetoresRepository setoresRepository;

    private final AdministracaoRepository administracaoRepository;

    public PaginatedResponse<Setores> getAllSetores(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null){
            Page<Setores> setoresPage = this.setoresRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ?  Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(setoresPage.getContent(), setoresPage.getTotalElements());
        }

        Page<Setores> setoresPage = this.setoresRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(setoresPage.getContent(), setoresPage.getTotalElements());
    }

    public PaginatedResponse<Setores> getByIdSetores(Integer id) {
        Optional<Setores> resultId = this.setoresRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
    }

    public void createNewSetores(UsuariosRequest usuariosRequest) {
        Optional<Administracao> adm = administracaoRepository.findById(usuariosRequest.getAdmId());
        if (adm.isPresent()) {
            Setores setor = new Setores();
            setor.setSetorNome(usuariosRequest.getSetorNome());
            setor.setAdm(adm.get());
            this.setoresRepository.save(setor);
        } else {
            throw new InternalError("adm_id não encontrado para realizar a vinculação com esse setor");
        }
    }

    public void updateSetores(Setores setores) {
        Optional<Setores> idFindValues = this.setoresRepository.findById(setores.getSetorId());
        if (idFindValues.isPresent()) {
            Setores setorExistente = idFindValues.get();
            boolean updated = false;

            if (setores.getSetorNome() != null) {
                setorExistente.setSetorNome(setores.getSetorNome());
                updated = true;
            }

            if (updated) {
                this.setoresRepository.save(setorExistente);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Setor não encontrado.");
        }

    }

    public void deleteSetor(Integer id) {
        Optional<Setores> idEncontrado = setoresRepository.findById(id);

        if (idEncontrado.isPresent()) {
            try {
                setoresRepository.deleteById(id);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Esse Setor já está vinculado com outros serviços. " +
                        "Realize primeiramente a remoção dos serivços vinculados para prosseguir  com a remoção");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do setor não encontrado para realizar a remoção");
        }
    }
}
