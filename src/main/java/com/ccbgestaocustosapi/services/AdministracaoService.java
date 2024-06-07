package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdministracaoService {
    private final AdministracaoRepository administracaoRepository;

    public PaginatedResponse<Administracao> getAllAdministracao(int page, int size) {
        Page<Administracao> administracaoPage = this.administracaoRepository.findAll(PageRequest.of(page, size));
        return new PaginatedResponse<>(administracaoPage.getContent(), administracaoPage.getTotalElements());
    }

    public PaginatedResponse<Administracao> getbyIdAdministracao(Integer id) {
        Optional<Administracao> resultId = this.administracaoRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
    }

    @Transactional
    public void createNewAdministracao(Administracao administracao) {
        Administracao adm = new Administracao();
        adm.setAdmNome(administracao.getAdmNome());
        adm.setAdmCidade(administracao.getAdmCidade());
        adm.setAdmEstado(administracao.getAdmEstado());
        this.administracaoRepository.save(adm);


    }

    @Transactional
    public void updateAdministracao(Administracao administracao) {
        Optional<Administracao> idFindValues = this.administracaoRepository.findById(administracao.getAdmId());

        if (idFindValues.isPresent()) {
            Administracao existingAdm = idFindValues.get();
            boolean updated = false;

            if (administracao.getAdmNome() != null) {
                existingAdm.setAdmNome(administracao.getAdmNome());
                updated = true;
            }

            if (administracao.getAdmCidade() != null) {
                existingAdm.setAdmCidade(administracao.getAdmCidade());
                updated = true;
            }

            if (administracao.getAdmEstado() != null) {
                existingAdm.setAdmEstado(administracao.getAdmEstado());
                updated = true;
            }

            if (updated) {
                this.administracaoRepository.save(existingAdm);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Administração não encontrada.");
        }
    }

    public void deleteAdministracao(Integer id) {
        Optional<Administracao> idEncontrado = administracaoRepository.findById(id);

        if (idEncontrado.isPresent()) {
            try{
                administracaoRepository.deleteById(id);
            }catch (Exception e){
                throw new DataIntegrityViolationException("Essa Administração já está vinculada com outros serviços. " +
                        "Realize primeiramente a remoção dos serivços para prosseguir");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da Administração não encontrado para realizar a remoção");
        }
    }
}
