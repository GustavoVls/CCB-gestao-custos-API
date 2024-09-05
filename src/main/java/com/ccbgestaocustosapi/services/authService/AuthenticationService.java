package com.ccbgestaocustosapi.services.authService;


import com.ccbgestaocustosapi.dto.AuthenticationRequest;
import com.ccbgestaocustosapi.dto.AuthenticationResponse;
import com.ccbgestaocustosapi.dto.RegisterRequest;
import com.ccbgestaocustosapi.email.EmailService;
import com.ccbgestaocustosapi.email.EmailTemplateName;
import com.ccbgestaocustosapi.models.*;
import com.ccbgestaocustosapi.repository.*;
import com.ccbgestaocustosapi.security.JwtService;
import com.ccbgestaocustosapi.token.Token;
import com.ccbgestaocustosapi.token.TokenRepository;
import com.ccbgestaocustosapi.token.TokenType;
import com.ccbgestaocustosapi.utils.MethodsUtils;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.BadCredentialException;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.InvalidCodeException;
import com.ccbgestaocustosapi.utils.exceptions.genericExceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdministracaoRepository admRepository;
    private final SetoresRepository setoresRepository;
    private final CasaOracoesRepository casaOracoesRepository;
    private final TokenRepository tokenRepository;
    private final CruzamentoPerfilUsuarioRepository cruzamentoPerfilUsuarioRepository;
    private final PerfilUsuarioRepository perfilUsuarioRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final MethodsUtils methodsUtils;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) throws ResponseStatusException {

        if (userRepository.existsByNome(request.getNomeUsuario())) {
            throw new BadCredentialException("Nome de usuário já existente");
        }

        if (userRepository.existsByEmail(request.getEmailUsuario())) {
            throw new BadCredentialException("Email de usuário já existente");
        }

        Administracao adm = admRepository.findById(request.getAdmId()).orElse(null);
        Setores setor = new Setores();
        CasaOracoes casaOracoes = new CasaOracoes();


        if (request.getSetorId() != null) {
            setor = setoresRepository.findById(request.getSetorId()).orElse(null);
        }

        if (request.getIgrId() != null) {
            casaOracoes = casaOracoesRepository.findById(request.getIgrId()).orElse(null);
        }

        Usuarios userDTO = new Usuarios();
        userDTO.setNome(request.getNomeUsuario());
        userDTO.setEmail(request.getEmailUsuario());
        userDTO.setSenha(passwordEncoder.encode(request.getSenha()));
        userDTO.setAdm(adm);

        CruzamentoPerfilUsuario cruzamentoPerfilUsuario = new CruzamentoPerfilUsuario();

        Optional<Perfil> idPerfil = perfilUsuarioRepository.findById(request.getIdPerfil());

        List<?> tipoPerfil = perfilUsuarioRepository.findTipoPerfilByIdPerfil(request.getIdPerfil());


        // TODO: 08/07/2024  Todos os perfils que serão adicioandos deverá ser implementado nessa condição de cadastro e no SecutiryConfiguration
        if (tipoPerfil.get(0).equals("ADM")) {
            userDTO.setRole(Role.ADMIN);
        } else {
            userDTO.setRole(Role.USER);
        }

        if (request.getSetorId() != null) {
            userDTO.setSetor(setor);
        } else {
            userDTO.setSetor(null);
        }
        if (request.getIgrId() != null) {
            userDTO.setIgr(casaOracoes);
        } else {
            userDTO.setIgr(null);
        }
        userRepository.save(userDTO);

        cruzamentoPerfilUsuario.setIdPerfil(idPerfil.get());
        cruzamentoPerfilUsuario.setIdUsuario(userDTO);

        cruzamentoPerfilUsuarioRepository.save(cruzamentoPerfilUsuario);

        var jwtToken = jwtService.generateToken(userDTO);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Transactional
    public void authenticate(AuthenticationRequest request) {
        try {
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            var jwtToken = jwtService.generateToken(user);

            String codValid = generateActivationCode();
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken, codValid);
            sendValidationEmail(user, codValid);
        } catch (InvalidCodeException | ResourceNotFoundException e) {
            throw e;  // Re-lançar a exceção específica para ser capturada pelo controlador
        } catch (BadCredentialException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro interno do servidor");
        }
    }

    private void revokeAllUserTokens(Usuarios user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getIdUsuario());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(Usuarios user, String jwtToken, String codValid) {


        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .codeValid(codValid)
                .codigoExpirado(false)
                .build();
        tokenRepository.save(token);
    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String codValid = generateActivationCode();

                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken, codValid);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private String generateActivationCode() {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    private void sendValidationEmail(Usuarios usuarios, String codigoAcesso) throws MessagingException {
        emailService.sendEmail(
                usuarios.getEmail(),
                usuarios.getNome(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                codigoAcesso,
                "Account activation"
        );
    }

    @Transactional
    public AuthenticationResponse verificaCodigoAcesso(String codigo) {
        try {
            Token token = this.tokenRepository.findByCodeValid(codigo)
                    .orElseThrow(() -> new InvalidCodeException("Código inválido"));

            if (token.codigoExpirado) {
                throw new ResourceNotFoundException("Código expirado. Realize o login novamente.");
            }

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setAccessToken(token.getToken());

            token.setCodigoExpirado(true);
            tokenRepository.save(token);

            // Encontra o idUsuario pelo token
            Integer idUsuario = tokenRepository.findIdUsuarioByToken(token.getToken());

            // Encontra o tipoPermissao pelo idUsuario
            String tipoPermissao = tokenRepository.findTipoPermissaoByIdUsuario(Long.valueOf(idUsuario));

            authenticationResponse.setTipoPermissao(tipoPermissao);

            return authenticationResponse;
        } catch (InvalidCodeException | ResourceNotFoundException e) {
            throw e;  // Re-lançar a exceção específica para ser capturada pelo controlador
        } catch (Exception e) {
            throw new RuntimeException("Erro interno do servidor");
        }
    }

    public boolean validaUsuario(String email, String senha) {
        String senhaResult = this.methodsUtils.decryptFromBase64(senha);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            senhaResult
                    )
            );

            userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

            return true;
        } catch (BadCredentialsException e) {
            throw new BadCredentialException("Email ou Senha inválida.");
        }
    }
}
