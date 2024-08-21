package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.CasaOracoesFiltroResponse;
import com.ccbgestaocustosapi.dto.CasaOracoesRequest;
import com.ccbgestaocustosapi.dto.dropdowns.SetorDropdownResponse;
import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.models.CasaOracoes;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.CasaOracoesRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
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
public class CasaOracoesService {

    private final CasaOracoesRepository casaOracoesRepository;

    private final AdministracaoRepository administracaoRepository;

    private final SetoresRepository setoresRepository;

    public PaginatedResponse<CasaOracoes> getAllCasaOracoes(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null) {
            Page<CasaOracoes> casaOracoesPage = this.casaOracoesRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ? Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(casaOracoesPage.getContent(), casaOracoesPage.getTotalElements());
        }

        Page<CasaOracoes> casaOracoesPage = this.casaOracoesRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(casaOracoesPage.getContent(), casaOracoesPage.getTotalElements());
    }

    public PaginatedResponse<CasaOracoesFiltroResponse> getByCasaOracoes(String nomeIgreja, String valueOrderBY, boolean isOrderByAsc) {
        List<Object[]> resultId;

        if (valueOrderBY == null) {
            resultId = this.casaOracoesRepository.findByNomeIgreja(nomeIgreja);
        } else {
            resultId = this.casaOracoesRepository.findByNomeIgrejaOrderBy(
                    nomeIgreja, valueOrderBY, isOrderByAsc ? "asc" : "desc");
        }

        List<CasaOracoesFiltroResponse> casaOracoesDTOs = new ArrayList<>();

        for (Object[] resultado : resultId) {
            Integer totalRecords = ((Number) resultado[0]).intValue();
            Integer igrId = ((Number) resultado[1]).intValue();
            String igrCod = ((String) resultado[2]);
            String igrNome = ((String) resultado[3]);
            String admNome = ((String) resultado[4]);
            String setorNome = ((String) resultado[5]);
            String igrEstado = ((String) resultado[6]);
            String igrCidade = ((String) resultado[7]);
            String igrBairro = ((String) resultado[8]);
            String igrCep = (String) resultado[9];
            String igrEndereco = (String) resultado[10];
            String igrComplemento = (String) resultado[11];
            Integer admId = ((Number) resultado[12]).intValue();
            Integer setorId = ((Number) resultado[13]).intValue();

            CasaOracoesFiltroResponse dto = new CasaOracoesFiltroResponse(totalRecords, igrId, igrCod, igrNome, admNome, setorNome, igrEstado, igrCidade, igrBairro, igrCep, igrEndereco, igrComplemento, admId, setorId);
            casaOracoesDTOs.add(dto);
        }
        return new PaginatedResponse<>(casaOracoesDTOs.stream().toList(), casaOracoesDTOs.isEmpty() ? 0 : casaOracoesDTOs.get(0).getTotalRecords());
    }

    @Transactional
    public void createNewCasaOracoes(CasaOracoesRequest casaOracoesRequest) {
        Optional<Administracao> adm = administracaoRepository.findById(casaOracoesRequest.getAdmId());
        Optional<Setores> setor = setoresRepository.findById(casaOracoesRequest.getSetorId());

        if (adm.isPresent() && setor.isPresent()) {
            CasaOracoes casaOracoes = new CasaOracoes();

            casaOracoes.setAdm(adm.get());
            casaOracoes.setSetor(setor.get());
            casaOracoes.setIgrCod(casaOracoesRequest.getIgrCod());
            casaOracoes.setIgrNome(casaOracoesRequest.getIgrNome());
            casaOracoes.setIgrEndereco(casaOracoesRequest.getIgrEndereco());
            casaOracoes.setIgrBairro(casaOracoesRequest.getIgrBairro());
            casaOracoes.setIgrCep(casaOracoesRequest.getIgrCep());
            casaOracoes.setIgrCidade(casaOracoesRequest.getIgrCidade());
            casaOracoes.setIgrEstado(casaOracoesRequest.getIgrEstado());
            casaOracoes.setIgrComplemento(casaOracoesRequest.getIgrComplemento());

            this.casaOracoesRepository.save(casaOracoes);
        } else {
            throw new InternalError("Vinculação não encontrada para cadastrar essa casa de oração");
        }


    }

    @Transactional
    public void updateCasaOracoes(CasaOracoesRequest casaOracoesRequest) {
        Optional<CasaOracoes> idFindValues = this.casaOracoesRepository.findById(casaOracoesRequest.getIgrId());
        if (idFindValues.isPresent()) {
            CasaOracoes casaOracoesExistente = idFindValues.get();
            boolean updated = false;

            if (casaOracoesRequest.getIgrCod() != null) {
                casaOracoesExistente.setIgrCod(casaOracoesRequest.getIgrCod());
                updated = true;
            }

            if (casaOracoesRequest.getIgrNome() != null) {
                casaOracoesExistente.setIgrNome(casaOracoesRequest.getIgrNome());
                updated = true;
            }

            if (casaOracoesRequest.getIgrEndereco() != null) {
                casaOracoesExistente.setIgrEndereco(casaOracoesRequest.getIgrEndereco());
                updated = true;
            }

            if (casaOracoesRequest.getIgrCep() != null) {
                casaOracoesExistente.setIgrCep(casaOracoesRequest.getIgrCep());
                updated = true;
            }

            if (casaOracoesRequest.getIgrBairro() != null) {
                casaOracoesExistente.setIgrBairro(casaOracoesRequest.getIgrBairro());
                updated = true;
            }

            if (casaOracoesRequest.getIgrComplemento()!= null) {
                casaOracoesExistente.setIgrComplemento(casaOracoesRequest.getIgrComplemento());
                updated = true;
            }

            if (updated) {
                this.casaOracoesRepository.save(casaOracoesExistente);
            } else {
                throw new BadCredentialException("Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new BadCredentialException("Casa de Oração não encontrada.");
        }
    }

    @Transactional
    public void deleteCasaOracao(Integer id) {
        Optional<CasaOracoes> idEncontrado = casaOracoesRepository.findById(id);

        if (idEncontrado.isPresent()) {
            try {
                casaOracoesRepository.deleteById(id);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Essa Casa de Oração já está vinculada com outros serviços. " +
                        "Realize primeiramente a remoção dos serivços vinculados para prosseguir com a remoção.");
            }
        } else {
            throw new BadCredentialException("Id da Casa de Oração não encontrado para realizar a remoção.");
        }

    }

    public List<SetorDropdownResponse> getDropDownSetor() {
        List<Object[]> dropdownSetorList = this.casaOracoesRepository.findAllDropdownSetor();

        List<SetorDropdownResponse> setorDropdownDto = new ArrayList<>();


        for (Object[] resultado : dropdownSetorList) {

            Integer setorId = ((Number) resultado[0]).intValue();

            String setorNome = ((String) resultado[1]);

            SetorDropdownResponse dto = new SetorDropdownResponse(setorId, setorNome);

            setorDropdownDto.add(dto);
        }

        return setorDropdownDto;

    }
}
