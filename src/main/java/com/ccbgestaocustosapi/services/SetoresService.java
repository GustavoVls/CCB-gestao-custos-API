package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.SetoresFiltroResponse;
import com.ccbgestaocustosapi.dto.UsuariosRequest;
import com.ccbgestaocustosapi.dto.dropdowns.AdmDropdownResponse;
import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
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
public class SetoresService {
    private final SetoresRepository setoresRepository;

    private final AdministracaoRepository administracaoRepository;

    public PaginatedResponse<Setores> getAllSetores(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null) {
            Page<Setores> setoresPage = this.setoresRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ? Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(setoresPage.getContent(), setoresPage.getTotalElements());
        }

        Page<Setores> setoresPage = this.setoresRepository.findAll(PageRequest.of(pageValue, size, Sort.by(Sort.Direction.ASC, "setorId" )));
        return new PaginatedResponse<>(setoresPage.getContent(), setoresPage.getTotalElements());
    }

    // TODO: 23/08/2024 Verificar se o findByNomeSetorOrderBy está sendo utilizado 
    public PaginatedResponse<SetoresFiltroResponse> getByNomeSetores(String nomeSetor,  String valueOrderBY, boolean isOrderByAsc) {
        List<Object[]> resultId;

        if (valueOrderBY == null){
            resultId = this.setoresRepository.findByNomeSetor(nomeSetor);
        }else {
            resultId = this.setoresRepository.findByNomeSetorOrderBy(nomeSetor, valueOrderBY, isOrderByAsc ? "asc" : "desc");
        }

        List<SetoresFiltroResponse> setoresDTOs = new ArrayList<>();



        for (Object[] resultado : resultId) {
            Integer totalRecords = ((Number) resultado[0]).intValue();
            Integer setorId = ((Number) resultado[1]).intValue();
            Integer admId = ((Number) resultado[2]).intValue();
            String setorNome = ((String) resultado[3]);
            String admNome = (String) resultado[4];

            SetoresFiltroResponse dto = new SetoresFiltroResponse(setorId, admId, setorNome, admNome, totalRecords);
            setoresDTOs.add(dto);
        }
        return new PaginatedResponse<>(setoresDTOs.stream().toList(), setoresDTOs.isEmpty() ? 0 : setoresDTOs.get(0).getTotalRecords());
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
                throw new BadCredentialException("Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new BadCredentialException("Setor não encontrado.");
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
            throw new BadCredentialException("Id do setor não encontrado para realizar a remoção");
        }
    }

    public List<AdmDropdownResponse> getDropdownAdm() {
        List<Object[]> dropdownAdmList = this.administracaoRepository.findAllDropdownAdm();


        List<AdmDropdownResponse> admDropdownDto = new ArrayList<>();

        for (Object[] resultado : dropdownAdmList) {
            Integer admId = ((Number) resultado[0]).intValue();
            String nomeAdm = ((String) resultado[1]);

            AdmDropdownResponse dto = new AdmDropdownResponse(nomeAdm, admId);
            admDropdownDto.add(dto);
        }

        return admDropdownDto;
    }
}
