package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.CasaOracoesRequest;
import com.ccbgestaocustosapi.models.Administracao;
import com.ccbgestaocustosapi.models.CasaOracoes;
import com.ccbgestaocustosapi.models.Setores;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.CasaOracoesRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
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
public class CasaOracoesService {

    private final CasaOracoesRepository casaOracoesRepository;

    private final AdministracaoRepository administracaoRepository;

    private final SetoresRepository setoresRepository;

    public PaginatedResponse<CasaOracoes> getAllCasaOracoes(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null){
            Page<CasaOracoes> casaOracoesPage = this.casaOracoesRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ?  Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(casaOracoesPage.getContent(), casaOracoesPage.getTotalElements());
        }

        Page<CasaOracoes> casaOracoesPage = this.casaOracoesRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(casaOracoesPage.getContent(), casaOracoesPage.getTotalElements());
    }

    public PaginatedResponse<CasaOracoes> getByIdCasaOracoes(Integer id) {
        Optional<CasaOracoes> resultId = this.casaOracoesRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
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

            if (updated) {
                this.casaOracoesRepository.save(casaOracoesExistente);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Casa de Oração não encontrada.");
        }
    }

    @Transactional
    public void deleteCasaOracao(Integer id) {
        Optional<CasaOracoes> idEncontrado = casaOracoesRepository.findById(id);

        if (idEncontrado.isPresent()) {
            try{
                casaOracoesRepository.deleteById(id);
            }catch (Exception e){
                throw new DataIntegrityViolationException("Essa Casa de Oração já está vinculada com outros serviços. " +
                        "Realize primeiramente a remoção dos serivços vinculados para prosseguir com a remoção.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da Casa de Oração não encontrado para realizar a remoção.");
        }

    }
}
