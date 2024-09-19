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
import com.ccbgestaocustosapi.repository.UsersRepository;
import com.ccbgestaocustosapi.token.TokenRepository;
import com.ccbgestaocustosapi.utils.DataConverter;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")

public class CasaOracoesService {

    private final CasaOracoesRepository casaOracoesRepository;

    private final AdministracaoRepository administracaoRepository;

    private final SetoresRepository setoresRepository;


    @PersistenceContext
    private EntityManager em;

    private final DataConverter dataConverter;
    private final TokenRepository tokenRepository;

    private final UsersRepository usersRepository;


    public PaginatedResponse<CasaOracoes> getAllCasaOracoes(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc, String ascDescValue, String token) {

        StringBuilder queryBuilder = new StringBuilder("""
                   select
                                     COUNT(*) OVER() AS total_records,
                                            co.igr_id,
                                            co.igr_cod,
                                            co.igr_nome,
                                            a.adm_nome,
                                            s.setor_nome,
                                            co.igr_estado,
                                            co.igr_cidade,
                                            co.igr_bairro,
                                            co.igr_cep,
                                            co.igr_endereco,
                                            co.igr_complemento,
                                            a.adm_id,
                                            s.setor_id
                                            from
                                            ccb.casa_oracoes co
                                            inner join ccb.administracao a on
                                            co.adm_id = a.adm_id
                                            inner join ccb.setores s on
                                            co.setor_id = s.setor_id
                                            where
                                            a.adm_id = :admId
                """);

        Map<String, Object> parameters = new HashMap<>();

        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);


        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);

        parameters.put("admId", idAdm);

        // Ordenação
        if (valueOrderBY != null) {
            queryBuilder.append(" order by ").append(valueOrderBY).append(" ").append(ascDescValue); // Evite passar parâmetros para "order by"
        }

        // Adicionando paginação com LIMIT e OFFSET
        int offset = (pageValue - 1) * size; // Calcula o offset
        queryBuilder.append(" limit :size offset :offset");
        parameters.put("size", size);
        parameters.put("offset", offset);

        Query query = em.createNativeQuery(queryBuilder.toString(), "CasaOracoesWithCount");

        // Configuração de parâmetros
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        // Execução da consulta
        List<Object[]> resultList = query.getResultList();
        int totalRecords = 0;

        List<CasaOracoes> cadastroList = new ArrayList<>();
        for (Object[] result : resultList) {
            CasaOracoes cadastro = (CasaOracoes) result[0];
            totalRecords = ((Number) result[1]).intValue();
            cadastroList.add(cadastro);
        }

        return new PaginatedResponse<>(cadastroList.stream().toList(), totalRecords);
    }

    public PaginatedResponse<CasaOracoesFiltroResponse> getByCasaOracoes(String nomeIgreja, String codIgreja, String valueOrderBY, boolean isOrderByAsc, String token) {

        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);
        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);

        List<Object[]> resultId = null;

        if (nomeIgreja != null && codIgreja != null) {
            resultId = this.casaOracoesRepository.findByNomeIgrejaAndCodIgreja(nomeIgreja, idAdm, codIgreja);
        }

        if (nomeIgreja != null && codIgreja == null) {
            resultId = this.casaOracoesRepository.findByNomeIgreja(nomeIgreja, idAdm);
        }

        if (codIgreja != null && nomeIgreja == null) {
            resultId = this.casaOracoesRepository.findByCodIgreja(idAdm, codIgreja);
        }

//        if (valueOrderBY == null) {//            resultId = this.casaOracoesRepository.findByNomeIgreja(nomeIgreja, idAdm);//        } else {//            resultId = this.casaOracoesRepository.findByNomeIgrejaOrderBy(
//                    nomeIgreja, valueOrderBY, isOrderByAsc ? "asc" : "desc");
//        }

        List<CasaOracoesFiltroResponse> casaOracoesDTOs = new ArrayList<>();

        assert resultId != null;
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

            if (casaOracoesRequest.getIgrComplemento() != null) {
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
