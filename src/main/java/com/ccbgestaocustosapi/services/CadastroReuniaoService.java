package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.CadastroReunioesRequest;
import com.ccbgestaocustosapi.models.CadastroReuniaoATDM;
import com.ccbgestaocustosapi.repository.CadastroReuniaoRepository;
import com.ccbgestaocustosapi.utils.DataConverter;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class CadastroReuniaoService {

    @PersistenceContext
    private EntityManager em;

    private final DataConverter dataConverter;
    private final CadastroReuniaoRepository cadastroReuniaoRepository;

    public PaginatedResponse<CadastroReuniaoATDM> getAllReunioesCadastradas(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {
        if (valueOrderBY != null) {
            Page<CadastroReuniaoATDM> reunioesCadastradasPage = this.cadastroReuniaoRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ? Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(reunioesCadastradasPage.getContent(), reunioesCadastradasPage.getTotalElements());
        }

        Page<CadastroReuniaoATDM> reunioesCadastradasPage = this.cadastroReuniaoRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(reunioesCadastradasPage.getContent(), reunioesCadastradasPage.getTotalElements());
    }

    @Transactional
    public PaginatedResponse<CadastroReuniaoATDM> getbyIdReunioesCadastradas(String descricao, String dataInicial, String dataFinal, String valueOrderBY, boolean isOrderByAsc
    , String ascDescValue) {
        StringBuilder queryBuilder = new StringBuilder("select c.*, COUNT(*) OVER() AS total_records from CCB.CADASTRO_REUNIAO_ATDM c where 1=1");

        Map<String, Object> parameters = new HashMap<>();

        if (descricao != null && !descricao.isEmpty()) {
            queryBuilder.append(" and upper (c.reuniao_descricao) like upper('%' || :descricao || '%')");
            parameters.put("descricao", descricao);
        }

        if (dataInicial != null && !dataInicial.isEmpty() && dataFinal != null && !dataFinal.isEmpty()) {
            LocalDateTime dateInicial = DataConverter.convertStringToDateTimeStartOfDay(dataInicial);
            LocalDateTime dateFinal = DataConverter.convertStringToDateTimeEndOfDay(dataFinal);
            queryBuilder.append(" and c.reuniao_data_ini between :dataInicial and :dataFinal");
            parameters.put("dataInicial", dateInicial);
            parameters.put("dataFinal", dateFinal);
        }


        if (dataInicial != null && !dataInicial.isEmpty()) {
            LocalDateTime dateInicial = DataConverter.convertStringToDateTimeStartOfDay(dataInicial);
            queryBuilder.append(" and c.reuniao_data_ini >= :dataInicial");
            parameters.put("dataInicial", dateInicial);
        }

        if (dataFinal != null && !dataFinal.isEmpty()) {
            LocalDateTime dateFinal = DataConverter.convertStringToDateTimeEndOfDay(dataFinal);
            queryBuilder.append(" and c.reuniao_data_fim <= :dataFinal");
            parameters.put("dataFinal", dateFinal);
        }

        if (valueOrderBY != null) {
            queryBuilder.append(" order by :orderBy :ascDescValue");
            parameters.put("orderBy", valueOrderBY);
            parameters.put("ascDescValue", ascDescValue);
        }

        Query query = em.createNativeQuery(queryBuilder.toString(), "CadastroReuniaoATDMWithCount");

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<Object[]> resultList = query.getResultList();
        int totalRecords = 0;

        List<CadastroReuniaoATDM> cadastroList = new ArrayList<>();
        for (Object[] result : resultList) {
            CadastroReuniaoATDM cadastro = (CadastroReuniaoATDM) result[0];
            totalRecords = ((Number) result[1]).intValue();
            cadastroList.add(cadastro);
        }


        return new PaginatedResponse<>(cadastroList.stream().toList(), totalRecords);

    }

    @Transactional
    public void createNewCadastroReuniao(CadastroReunioesRequest cadastroReuniaoATDM) {
        LocalDateTime dateInicial = DataConverter.convertStringToDate(cadastroReuniaoATDM.getReuniaoDataIni());
        LocalDateTime dateFinal = DataConverter.convertStringToDate(cadastroReuniaoATDM.getReuniaoDataFim());

        CadastroReuniaoATDM cadastraReuniao = new CadastroReuniaoATDM();
        cadastraReuniao.setReuniaoDescricao(cadastroReuniaoATDM.getReuniaoDescricao());
        cadastraReuniao.setReuniaoData(LocalDateTime.now());
        cadastraReuniao.setReuniaoDataIni(dateInicial);
        cadastraReuniao.setReuniaoDataFim(dateFinal);
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
                throw new BadCredentialException("Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new BadCredentialException("Reunião cadastrada não encontrada.");
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
            throw new BadCredentialException("Id da Reunião não encontrado para realizar a remoção");
        }


    }
}