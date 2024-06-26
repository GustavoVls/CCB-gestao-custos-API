package com.ccbgestaocustosapi.services.authService;


import com.ccbgestaocustosapi.dto.AuthenticationRequest;
import com.ccbgestaocustosapi.dto.AuthenticationResponse;
import com.ccbgestaocustosapi.dto.RegisterRequest;
import com.ccbgestaocustosapi.email.EmailService;
import com.ccbgestaocustosapi.email.EmailTemplateName;
import com.ccbgestaocustosapi.models.*;
import com.ccbgestaocustosapi.repository.AdministracaoRepository;
import com.ccbgestaocustosapi.repository.CasaOracoesRepository;
import com.ccbgestaocustosapi.repository.SetoresRepository;
import com.ccbgestaocustosapi.repository.UsersRepository;
import com.ccbgestaocustosapi.security.JwtService;
import com.ccbgestaocustosapi.token.Token;
import com.ccbgestaocustosapi.token.TokenRepository;
import com.ccbgestaocustosapi.token.TokenType;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

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


    private final JwtService jwtService;

    private final EmailService emailService;
    private static final String key = "63686176655365637265743132333334"; // 16, 24, or 32 bytes
    private static final String iv = "64617461536563726574313233333435"; // 16 bytes


    public AuthenticationResponse register(RegisterRequest request) throws ResponseStatusException {

        if (userRepository.existsByNome(request.getNome())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome usuário já existente");
        }


        if (userRepository.existsByEmail(request.getEmail())) {
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

        if (request.getUsuarioAdm().toString().equals("S")) {
            userDTO.setRole(Role.ADMIN);
        } else {
            userDTO.setRole(Role.USER);
        }

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
                .accessToken(jwtToken)
                .build();
    }

    public void authenticate(AuthenticationRequest request) {
        try {
            String senha = decryptFromBase64(request.getPassword());

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                senha
                        )
                );
            } catch (BadCredentialsException e) {
                throw new BadCredentialException("Email ou Senha inválida.");
            }

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


            Integer idUsuario = tokenRepository.findIdUsuarioByToken(token.getToken());

            String usuarios = tokenRepository.findTipoPermissaoByIdUsuario(Long.valueOf(idUsuario));

            authenticationResponse.setTipoPermissao(usuarios);


            return authenticationResponse;
        } catch (InvalidCodeException | ResourceNotFoundException e) {
            throw e;  // Re-lançar a exceção específica para ser capturada pelo controlador
        } catch (Exception e) {
            throw new RuntimeException("Erro interno do servidor");
        }
    }

    // Função para descriptografar uma string Base64 com AES
    // Após implementar esse decrypt, a senha passada para o payload e descriptografada com a senha chave e
    // depois passado de base64 para string para ser utilizada
    // TODO: 26/06/2024 implementar no cadastro de usuário da mesma lógica para o processo funcianar de forma segura. 
    public static String decryptFromBase64(String base64) {
        try {
            byte[] keyBytes = hexStringToByteArray(key);
            byte[] ivBytes = hexStringToByteArray(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(base64));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método auxiliar para converter uma string hexadecimal em um array de bytes
    public static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }


}
