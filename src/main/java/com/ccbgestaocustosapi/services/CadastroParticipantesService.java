package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.CadastroParticipantesATDMResponse;
import com.ccbgestaocustosapi.dto.ParticipantesAtdmRequest;
import com.ccbgestaocustosapi.dto.dropdowns.ComumDropdownResponse;
import com.ccbgestaocustosapi.dto.dropdowns.ReuniaoDropdownResponse;
import com.ccbgestaocustosapi.models.CadastroParticipantesATDM;
import com.ccbgestaocustosapi.models.CadastroReuniaoATDM;
import com.ccbgestaocustosapi.repository.CadastroParticipantesRepository;
import com.ccbgestaocustosapi.repository.CadastroReuniaoRepository;
import com.ccbgestaocustosapi.repository.UsersRepository;
import com.ccbgestaocustosapi.token.TokenRepository;
import com.ccbgestaocustosapi.utils.PaginatedResponse;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CadastroParticipantesService {
    private final CadastroParticipantesRepository cadastroParticipantesRepository;
    private final CadastroReuniaoRepository cadastroReuniaoRepository;

    private final TokenRepository tokenRepository;

    private final UsersRepository usersRepository;

    public PaginatedResponse<CadastroParticipantesATDMResponse> getAllParticipantes(int pageValue, Integer size, String valueOrderBY, boolean isOrderByAsc, String token) {

        List<Object[]> resultId = null;

        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);


        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);

        if (valueOrderBY == null) {
            resultId = this.cadastroParticipantesRepository.findByAdmId(idAdm);
        }

        List<CadastroParticipantesATDMResponse> cadastroParticipantesDTOs = new ArrayList<>();

        assert resultId != null;
        for (Object[] resultado : resultId) {
            Integer totalRecords = ((Number) resultado[0]).intValue();
            Integer idParticipantes = ((Number) resultado[1]).intValue();
            String nomeParticipante = ((String) resultado[2]);
            String cargoParticipante = ((String) resultado[3]);
            String comumParticipante = (String) resultado[4];
            String reuniaoDescricao = (String) resultado[5];
            Integer reuniaoId = ((Number) resultado[6]).intValue();


            CadastroParticipantesATDMResponse dto = new CadastroParticipantesATDMResponse(totalRecords, idParticipantes, nomeParticipante, cargoParticipante,
                    comumParticipante, reuniaoDescricao, reuniaoId);
            cadastroParticipantesDTOs.add(dto);
        }

        return new PaginatedResponse<>(cadastroParticipantesDTOs.stream().toList(), cadastroParticipantesDTOs.isEmpty() ? 0 : cadastroParticipantesDTOs.get(0).getTotalRecords());

    }

    public PaginatedResponse<CadastroParticipantesATDMResponse> getByNomeParticipantes(String nomeParticipantes, String valueOrderBY, boolean isOrderByAsc,
                                                                                       String token) {

        List<Object[]> resultId;

        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);


        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);

        if (valueOrderBY == null) {
            resultId = this.cadastroParticipantesRepository.findByNomeParticipantes(nomeParticipantes, idAdm);
        } else {
            resultId = this.cadastroParticipantesRepository.findByNomeParticipantesOrderBy(nomeParticipantes, valueOrderBY, isOrderByAsc ? "asc" : "desc");
        }

        List<CadastroParticipantesATDMResponse> cadastroParticipantesDTOs = new ArrayList<>();

        for (Object[] resultado : resultId) {
            Integer totalRecords = ((Number) resultado[0]).intValue();
            Integer idParticipantes = ((Number) resultado[1]).intValue();
            String nomeParticipante = ((String) resultado[2]);
            String cargoParticipante = ((String) resultado[3]);
            String comumParticipante = (String) resultado[4];
            String reuniaoDescricao = (String) resultado[5];
            Integer reuniaoId = ((Number) resultado[6]).intValue();


            CadastroParticipantesATDMResponse dto = new CadastroParticipantesATDMResponse(totalRecords, idParticipantes, nomeParticipante, cargoParticipante,
                    comumParticipante, reuniaoDescricao, reuniaoId);
            cadastroParticipantesDTOs.add(dto);
        }

        return new PaginatedResponse<>(cadastroParticipantesDTOs.stream().toList(), cadastroParticipantesDTOs.isEmpty() ? 0 : cadastroParticipantesDTOs.get(0).getTotalRecords());
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


            if (updated) {
                this.cadastroParticipantesRepository.save(existingParticipantes);
            } else {
                throw new BadCredentialException("Nenhum campo para atualização foi fornecido.");
            }
        } else {
            throw new BadCredentialException("Participante não encontrado.");
        }
    }


    @Transactional
    public void deleteParticipantes(Integer id) {
        Optional<CadastroParticipantesATDM> idEncontrado = this.cadastroParticipantesRepository.findById(id);

        if (idEncontrado.isPresent()) {
            this.cadastroParticipantesRepository.deleteById(id);
        } else {
            throw new BadCredentialException("Id do participante não encontrado para realizar a remoção");
        }
    }

    public List<ComumDropdownResponse> getDropdownComum(String token) {

        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);


        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);

        List<Object[]> dropdownAdmList = this.cadastroParticipantesRepository.findAllDropdownComum(idAdm);

        List<ComumDropdownResponse> comumDropdownDto = new ArrayList<>();

        for (Object[] resultado : dropdownAdmList) {
            String igrNome = ((String) resultado[0]);
            ComumDropdownResponse dto = new ComumDropdownResponse(null, igrNome);
            comumDropdownDto.add(dto);
        }

        return comumDropdownDto;
    }

    public List<ReuniaoDropdownResponse> getDropdownReuniao(String token) {

        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);


        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);

        List<Object[]> dropdownAdmList = this.cadastroParticipantesRepository.findAllDropdownReuniao(idAdm);

        List<ReuniaoDropdownResponse> reuniaoDropdownDto = new ArrayList<>();

        for (Object[] resultado : dropdownAdmList) {
            Integer reuniaoId = ((Number) resultado[0]).intValue();
            String reuniaoDescricao = ((String) resultado[1]);
            ReuniaoDropdownResponse dto = new ReuniaoDropdownResponse(reuniaoId, reuniaoDescricao);
            reuniaoDropdownDto.add(dto);
        }

        return reuniaoDropdownDto;


    }
}