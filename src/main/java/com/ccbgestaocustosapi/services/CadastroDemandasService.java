package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.CadastroDemandaRequest;
import com.ccbgestaocustosapi.models.*;
import com.ccbgestaocustosapi.repository.*;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroDemandasService {
    private final CadastroDemandaRepository cadastroDemandaRepository;
    private final AdministracaoRepository administracaoRepository;
    private final SetoresRepository setoresRepository;
    private final CasaOracoesRepository casaOracoesRepository;
    private final PrioridadesRepository prioridadesRepository;
    private final CategoriasRepository categoriasRepository;
    private final CadastroReuniaoRepository cadastroReuniaoRepository;
    private final UsersRepository usuariosRepository;


    public PaginatedResponse<CadastroDemanda> getAllDemandas(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc) {

        if (valueOrderBY != null){
            Page<CadastroDemanda> cadastroDemandaPage = this.cadastroDemandaRepository.findAll(PageRequest.of(pageValue, size, Sort.by(isOrderByAsc ?  Sort.Direction.ASC : Sort.Direction.DESC, valueOrderBY)));
            return new PaginatedResponse<>(cadastroDemandaPage.getContent(), cadastroDemandaPage.getTotalElements());
        }


        Page<CadastroDemanda> cadastroDemandaPage = this.cadastroDemandaRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(cadastroDemandaPage.getContent(), cadastroDemandaPage.getTotalElements());
    }

    public PaginatedResponse<CadastroDemanda> getbyIdDemandas(Integer id) {
        Optional<CadastroDemanda> resultId = this.cadastroDemandaRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);

    }

    @Transactional
    public void createNewCadastroDemanda(CadastroDemandaRequest cadastroDemandaRequest) {
        Administracao adm = administracaoRepository.findById(cadastroDemandaRequest.getAdmId())
                .orElseThrow(() -> new IllegalArgumentException("Administração não encontrada"));
        Setores setor = setoresRepository.findById(cadastroDemandaRequest.getSetorId())
                .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado"));
        CasaOracoes casaOracoes = casaOracoesRepository.findById(cadastroDemandaRequest.getIgrId())
                .orElseThrow(() -> new IllegalArgumentException("Casa de Oração não encontrada"));
        Prioridades prioridades = prioridadesRepository.findById(cadastroDemandaRequest.getPrioridadeId())
                .orElseThrow(() -> new IllegalArgumentException("Prioridade não encontrada"));
        Categorias categorias = categoriasRepository.findById(cadastroDemandaRequest.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
        CadastroReuniaoATDM reuniaoATDM = cadastroReuniaoRepository.findById(cadastroDemandaRequest.getReuniaoId())
                .orElseThrow(() -> new IllegalArgumentException("Reunião não encontrada"));
        Usuarios usuarios = usuariosRepository.findById(cadastroDemandaRequest.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        CadastroDemanda cadastroDemandaValue = new CadastroDemanda();
        cadastroDemandaValue.setAdm(adm);
        cadastroDemandaValue.setSetor(setor);
        cadastroDemandaValue.setIgr(casaOracoes);
        cadastroDemandaValue.setPrioridade(prioridades);
        cadastroDemandaValue.setCategoria(categorias);
        cadastroDemandaValue.setReuniao(reuniaoATDM);
        cadastroDemandaValue.setUsuario(usuarios);
        cadastroDemandaValue.setDatalct(cadastroDemandaRequest.getDatalct());
        cadastroDemandaValue.setDataReuniao(cadastroDemandaRequest.getDataReuniao());
        cadastroDemandaValue.setDemandaReuniao(cadastroDemandaRequest.getDemandaReuniao());
        cadastroDemandaValue.setValorEstimado(cadastroDemandaRequest.getValorEstimado());
        cadastroDemandaValue.setValorApurado(cadastroDemandaRequest.getValorApurado());
        cadastroDemandaValue.setValorAprovado(cadastroDemandaRequest.getValorAprovado());
        cadastroDemandaValue.setValorComprado(cadastroDemandaRequest.getValorComprado());
        cadastroDemandaValue.setDemandaStatus(cadastroDemandaRequest.getDemandaStatus());
        cadastroDemandaValue.setDemandaObs(cadastroDemandaRequest.getDemandaObs());
        cadastroDemandaValue.setDemandaCheckList(cadastroDemandaRequest.getDemandaCheckList());
        this.cadastroDemandaRepository.save(cadastroDemandaValue);
    }

    @Transactional
    public void updateDemanda(CadastroDemandaRequest cadastroDemandaRequest) {
        Optional<CadastroDemanda> idFindValues = this.cadastroDemandaRepository.findById(cadastroDemandaRequest.getIdDemanda());
        if (idFindValues.isPresent()) {
            CadastroDemanda existingDemanda = idFindValues.get();
            boolean updated = false;

            // TODO: 11/06/2024
            //  Verificar com o João como vai funcionar o udpate e de outras telas, pois tudo será de acordo com as regras que serão passadas ainda

            if (cadastroDemandaRequest.getDemandaStatus() != null) {
                existingDemanda.setDemandaStatus(cadastroDemandaRequest.getDemandaStatus());
                updated = true;
            }

            if (updated) {
                this.cadastroDemandaRepository.save(existingDemanda);
            } else {
                throw new BadCredentialException("Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new BadCredentialException("Demanda não encontrada.");
        }
    }

    public void deleteDemanda(Integer id) {
        Optional<CadastroDemanda> idEncontrado = this.cadastroDemandaRepository.findById(id);
        if (idEncontrado.isPresent()) {
            this.cadastroDemandaRepository.deleteById(id);
        } else {
            throw new BadCredentialException("Id do setor não encontrado para realizar a remoção");
        }
    }
}
