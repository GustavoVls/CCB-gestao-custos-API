package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.ParticipantesAtdmRequest;
import com.ccbgestaocustosapi.models.CadastroParticipantesATDM;
import com.ccbgestaocustosapi.models.CadastroReuniaoATDM;
import com.ccbgestaocustosapi.repository.CadastroParticipantesRepository;
import com.ccbgestaocustosapi.repository.CadastroReuniaoRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroParticipantesService {
    private final CadastroParticipantesRepository cadastroParticipantesRepository;
    private final CadastroReuniaoRepository cadastroReuniaoRepository;

    public PaginatedResponse<CadastroParticipantesATDM> getAllParticipantes(int pageValue, Integer size) {
        Page<CadastroParticipantesATDM> setoresPage = this.cadastroParticipantesRepository.findAll(PageRequest.of(pageValue, size));
        return new PaginatedResponse<>(setoresPage.getContent(), setoresPage.getTotalElements());

    }

    public PaginatedResponse<CadastroParticipantesATDM> getByIdParticipantes(Integer id) {
        Optional<CadastroParticipantesATDM> resultId = this.cadastroParticipantesRepository.findById(id);
        return new PaginatedResponse<>(resultId.stream().toList(), 0);
    }

    @Transactional
    public void createNewParticipantes(ParticipantesAtdmRequest participantesAtdmRequest) {
        Optional<CadastroReuniaoATDM> reuniaoValue = cadastroReuniaoRepository.findById(participantesAtdmRequest.getReuniaoId());
        if (reuniaoValue.isPresent()) {
            CadastroParticipantesATDM cadastroParticipantesATDM = new CadastroParticipantesATDM();
            cadastroParticipantesATDM.setReuniaoId(reuniaoValue.get());
            cadastroParticipantesATDM.setNomeParticipante(participantesAtdmRequest.getNomeParticipante());
            cadastroParticipantesATDM.setCargoParticipante(participantesAtdmRequest.getCargoParticipante());
            cadastroParticipantesATDM.setComumParticipante(participantesAtdmRequest.getComumParticipante());
            this.cadastroParticipantesRepository.save(cadastroParticipantesATDM);
        } else {
            throw new InternalError("Reunião não encontrada para realizar a vinculação com esse Participante");
        }
    }


    @Transactional
    public void updateParticipantes(CadastroParticipantesATDM cadastroParticipantesATDM) {
        Optional<CadastroParticipantesATDM> idFindValues = this.cadastroParticipantesRepository.findById(cadastroParticipantesATDM.getIdParticipantes());
        if (idFindValues.isPresent()) {
            CadastroParticipantesATDM existingParticipantes = idFindValues.get();
            boolean updated = false;

            // caso Precisar adicionar mais updates, apenas implementar nas condições
            if (cadastroParticipantesATDM.getNomeParticipante() != null) {
                existingParticipantes.setNomeParticipante(cadastroParticipantesATDM.getNomeParticipante());
                updated = true;
            }

            if (cadastroParticipantesATDM.getCargoParticipante() != null) {
                existingParticipantes.setCargoParticipante(cadastroParticipantesATDM.getCargoParticipante());
                updated = true;
            }

            if (cadastroParticipantesATDM.getComumParticipante() != null) {
                existingParticipantes.setComumParticipante(cadastroParticipantesATDM.getNomeParticipante());
                updated = true;
            }

            if (updated) {
                this.cadastroParticipantesRepository.save(existingParticipantes);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Participante não encontrado.");
        }
    }


    @Transactional
    public void deleteParticipantes(Integer id) {
        Optional<CadastroParticipantesATDM> idEncontrado = this.cadastroParticipantesRepository.findById(id);

        if (idEncontrado.isPresent()) {
            this.cadastroParticipantesRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do participante não encontrado para realizar a remoção");
        }
    }
}