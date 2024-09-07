package com.ccbgestaocustosapi.services;

import com.ccbgestaocustosapi.dto.dropdowns.AdmDropdownResponse;
import com.ccbgestaocustosapi.dto.dropdowns.ComumDropdownResponse;
import com.ccbgestaocustosapi.dto.dropdowns.SetorDropdownResponse;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
import com.ccbgestaocustosapi.repository.UsersRepository;
import com.ccbgestaocustosapi.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DropdownsService {

    private final SetoresRepository setoresRepository;

    private final AdministracaoRepository administracaoRepository;

    private final TokenRepository tokenRepository;

    private final UsersRepository usersRepository;


    public List<SetorDropdownResponse> getDropDownSetor(Integer admId) {
        List<Object[]> dropdownSetorList = this.setoresRepository.findAllDropdownSetorByAdm(admId);

        List<SetorDropdownResponse> setorDropdownDto = new ArrayList<>();


        for (Object[] resultado : dropdownSetorList) {

            Integer setorId = ((Number) resultado[0]).intValue();

            String setorNome = ((String) resultado[1]);

            SetorDropdownResponse dto = new SetorDropdownResponse(setorId, setorNome);

            setorDropdownDto.add(dto);
        }

        return setorDropdownDto;

    }

    public List<SetorDropdownResponse> dropdownSetorSemParamAdmId(String token) {
        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);



        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);


        List<Object[]> dropdownSetorList = this.setoresRepository.findAllDropdownSetorByAdm(idAdm);

        List<SetorDropdownResponse> setorDropdownDto = new ArrayList<>();


        for (Object[] resultado : dropdownSetorList) {

            Integer setorId = ((Number) resultado[0]).intValue();

            String setorNome = ((String) resultado[1]);

            SetorDropdownResponse dto = new SetorDropdownResponse(setorId, setorNome);

            setorDropdownDto.add(dto);
        }

        return setorDropdownDto;

    }



    public List<ComumDropdownResponse> getDropdownCasaDeOracao(Integer admId, Integer setorId) {
        List<Object[]> dropdownAdmList = this.setoresRepository.findCasaDeOracaoByAdmSetorId(admId, setorId);

        List<ComumDropdownResponse> comumDropdownDto = new ArrayList<>();

        for (Object[] resultado : dropdownAdmList) {
            Integer igrId = ((Number) resultado[0]).intValue();
            String igrNome = ((String) resultado[1]);
            ComumDropdownResponse dto = new ComumDropdownResponse(igrId, igrNome);
            comumDropdownDto.add(dto);
        }

        return comumDropdownDto;
    }


    public List<AdmDropdownResponse> getDropdownAdm(String token) {

        Integer IdUsuarioFinded = tokenRepository.findIdUsuarioByToken(token);



        Integer idAdm = usersRepository.findAdmIdByIdUsuarios(IdUsuarioFinded);


        List<Object[]> dropdownAdmList = this.administracaoRepository.findAllDropdownAdmId(idAdm);


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
