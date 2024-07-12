package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.models.CadastroReuniaoATDM;
import com.ccbgestaocustosapi.repository.CadastroReuniaoRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
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
public class CadastroReuniaoService {
    private final CadastroReuniaoRepository cadastroReuniaoRepository;

    public PaginatedResponse<CadastroReuniaoATDM> getAllReunioesCadastradas(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null){
            Page<CadastroReuniaoATDM> reunioesCadastradasPage = this.cadastroReuniaoRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ?  Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(reunioesCadastradasPage.getContent(), reunioesCadastradasPage.getTotalElements());
        }

        Page<CadastroReuniaoATDM> reunioesCadastradasPage = this.cadastroReuniaoRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(reunioesCadastradasPage.getContent(), reunioesCadastradasPage.getTotalElements());
    }

    public PaginatedResponse<CadastroReuniaoATDM> getbyIdReunioesCadastradas(Integer id) {
        Optional<CadastroReuniaoATDM> resultId = this.cadastroReuniaoRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
    }

    @Transactional
    public void createNewCadastroReuniao(CadastroReuniaoATDM cadastroReuniaoATDM) {

        CadastroReuniaoATDM cadastraReuniao = new CadastroReuniaoATDM();
        cadastraReuniao.setReuniaoDescricao(cadastroReuniaoATDM.getReuniaoDescricao());
        cadastraReuniao.setReuniaoData(cadastroReuniaoATDM.getReuniaoData());
        cadastraReuniao.setReuniaoDataIni(cadastroReuniaoATDM.getReuniaoDataIni());
        cadastraReuniao.setReuniaoDataFim(cadastroReuniaoATDM.getReuniaoDataFim());
        cadastraReuniao.setReuniaoStatus(cadastroReuniaoATDM.getReuniaoStatus());
        cadastraReuniao.setReuniaoAta(cadastroReuniaoATDM.getReuniaoAta());
        this.cadastroReuniaoRepository.save(cadastraReuniao);
    }

    @Transactional
    public void updateCadastroReuniao(CadastroReuniaoATDM cadastroReuniaoATDM) {
        Optional<CadastroReuniaoATDM> idFindValues = this.cadastroReuniaoRepository.findById(cadastroReuniaoATDM.getReuniaoId());
        if (idFindValues.isPresent()) {
            CadastroReuniaoATDM existingAdm = idFindValues.get();
            boolean updated = false;

            // caso Precisar adicionar mais updates, apenas implementar nas condições
            if (cadastroReuniaoATDM.getReuniaoDescricao() != null) {
                existingAdm.setReuniaoDescricao(cadastroReuniaoATDM.getReuniaoDescricao());
                updated = true;
            }

            if (cadastroReuniaoATDM.getReuniaoStatus() != null) {
                existingAdm.setReuniaoStatus(cadastroReuniaoATDM.getReuniaoStatus());
                updated = true;
            }

            if (updated) {
                this.cadastroReuniaoRepository.save(existingAdm);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reunião cadastrada não encontrada.");
        }
    }

    @Transactional
    public void deleteReuniaoCadastrado(Integer id) {
        Optional<CadastroReuniaoATDM> idEncontrado = this.cadastroReuniaoRepository.findById(id);

        if (idEncontrado.isPresent()) {
            try {
                this.cadastroReuniaoRepository.deleteById(id);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Essa Reunião já está vinculada com outros serviços. " +
                        "Realize primeiramente a remoção dos serivços vinculados para prosseguir com a remoção.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da Reunião não encontrado para realizar a remoção");
        }


    }
}