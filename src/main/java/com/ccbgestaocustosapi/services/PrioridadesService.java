package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.PrioridadeFiltroResponse;
import com.ccbgestaocustosapi.models.Prioridades;
import com.ccbgestaocustosapi.repository.PrioridadesRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public PaginatedResponse<PrioridadeFiltroResponse> getbyPrioridades(String nomePrioridade, String valueOrderBY, boolean isOrderByAsc) {

        List<Object[]> resultId;
        if (valueOrderBY == null){
            resultId = this.prioridadesRepository.findByNomePrioridade(nomePrioridade);
        }else {
            resultId = this.prioridadesRepository.findByNomePrioridadeOrderBy(nomePrioridade, valueOrderBY, isOrderByAsc ? "asc" : "desc");
        }

        List<PrioridadeFiltroResponse> prioridadesDTOs = new ArrayList<>();

        for (Object[] resultado : resultId) {
            Integer totalRecords = ((Number) resultado[0]).intValue();
            Integer prioridadeId = ((Number) resultado[1]).intValue();
            String  prioridadeNome = (String) resultado[2];

            PrioridadeFiltroResponse dto = new PrioridadeFiltroResponse(prioridadeId, prioridadeNome, totalRecords);
            prioridadesDTOs.add(dto);
        }

        return new PaginatedResponse<>(prioridadesDTOs.stream().toList(), 0);
    }

    @Transactional
    public void createNewPrioridade(Prioridades prioridades) {
        Prioridades prioridadesValue = new Prioridades();
        prioridadesValue.setNomePrioridade(prioridades.getNomePrioridade());
        this.prioridadesRepository.save(prioridadesValue);
    }

    @Transactional
    public void updatePrioridade(Prioridades prioridades) {

        Optional<Prioridades> idFindValues = this.prioridadesRepository.findById(prioridades.getIdPrioridade());

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
                throw new BadCredentialException("Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new BadCredentialException("Prioridade não encontrada.");
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
            throw new BadCredentialException("Id da Prioridade não encontrado para realizar a remoção.");
        }
    }
}
