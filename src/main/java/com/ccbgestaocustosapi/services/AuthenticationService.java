package com.ccbgestaocustosapi.services;


import com.ccbgestaocustosapi.dto.AuthenticationRequest;
import com.ccbgestaocustosapi.dto.AuthenticationResponse;
import com.ccbgestaocustosapi.dto.RegistrerRequest;
import com.ccbgestaocustosapi.models.*;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.CasaOracoesRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
import com.ccbgestaocustosapi.repository.UsersRepository;
import com.ccbgestaocustosapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final UsersRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    private final AuthenticationManager authenticationManager;

    private final AdministracaoRepository admRepository;


    private final SetoresRepository setoresRepository;


    private final CasaOracoesRepository casaOracoesRepository;


    private final JwtService jwtService;

    public AuthenticationResponse register(RegistrerRequest request) throws ResponseStatusException {



        if (userRepository.existsByNome(request.getNome())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome usuário já existente");
        }


        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email usuário já existente");
        }


        Administracao adm;
        Setores setor = new Setores();
        CasaOracoes casaOracoes = new CasaOracoes();

        adm = admRepository.findById(request.getIdAdm()).orElse(null);

        if (request.getIdSetor() != null) {
            setor = setoresRepository.findById(request.getIdSetor()).orElse(null);
        }

        if (request.getIdIgr() != null) {
            casaOracoes = casaOracoesRepository.findById(request.getIdIgr()).orElse(null);
        }

        Usuarios userDTO = new Usuarios();
        userDTO.setNome(request.getNome());
        userDTO.setEmail(request.getEmail());
        userDTO.setSenha(passwordEncoder.encode(request.getSenha()));
        userDTO.setUsuarioAdm(request.getUsuarioAdm());
        userDTO.setAdm(adm);
        userDTO.setRole(Role.USER);
        if (request.getIdSetor() != null) {
            userDTO.setSetor(setor);
        } else {
            userDTO.setSetor(null);
        }
        if (request.getIdIgr() != null) {
            userDTO.setIgr(casaOracoes);
        } else {
            userDTO.setIgr(null);
        }
        userRepository.save(userDTO);

        var jwtToken = jwtService.generateToken(userDTO);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }



}
