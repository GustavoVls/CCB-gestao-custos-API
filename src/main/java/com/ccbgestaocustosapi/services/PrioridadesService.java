package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.models.Prioridades;
import com.ccbgestaocustosapi.repository.PrioridadesRepository;
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
public class PrioridadesService {
    private final PrioridadesRepository prioridadesRepository;
    public PaginatedResponse<Prioridades> getAllPrioridades(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null){
            Page<Prioridades> prioridadesPage = this.prioridadesRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ?  Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(prioridadesPage.getContent(), prioridadesPage.getTotalElements());
        }

        Page<Prioridades> prioridadesPage = this.prioridadesRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(prioridadesPage.getContent(), prioridadesPage.getTotalElements());
    }

    public PaginatedResponse<Prioridades> getbyPrioridades(Integer id) {
        Optional<Prioridades> resultId = this.prioridadesRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
    }

    @Transactional
    public void createNewPrioridade(Prioridades prioridades) {
        Prioridades prioridadesValue = new Prioridades();
        prioridadesValue.setNomePrioridade(prioridades.getNomePrioridade());
        this.prioridadesRepository.save(prioridadesValue);
    }

    @Transactional
    public void updatePrioridade(Prioridades prioridades) {

        Optional<Prioridades> idFindValues = this.prioridadesRepository.findById(prioridades.getIdCategoria());

        if (idFindValues.isPresent()) {
            Prioridades existingPrioridade = idFindValues.get();
            boolean updated = false;

            if (prioridades.getNomePrioridade() != null) {
                existingPrioridade.setNomePrioridade(prioridades.getNomePrioridade());
                updated = true;
            }

            if (updated) {
                this.prioridadesRepository.save(existingPrioridade);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prioridade não encontrada.");
        }
    }

    @Transactional
    public void deletePrioridade(Integer id) {
        Optional<Prioridades> idEncontrado = this.prioridadesRepository.findById(id);

        if (idEncontrado.isPresent()) {
            try{
                this.prioridadesRepository.deleteById(id);
            }catch (Exception e){
                throw new DataIntegrityViolationException("Essa Prioridade já está vinculada com outros serviços. " +
                        "Realize primeiramente a remoção dos serivços vinculados para prosseguir com a remoção.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da Prioridade não encontrado para realizar a remoção.");
        }
    }
}
