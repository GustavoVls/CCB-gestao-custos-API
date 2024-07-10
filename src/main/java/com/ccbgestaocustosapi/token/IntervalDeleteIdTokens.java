package com.ccbgestaocustosapi.token;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@AllArgsConstructor
// classe criada para executar uma verificação caso exista id's token inutilizavéis na tabela token
public class IntervalDeleteIdTokens {

    private final TokenRepository tokenRepository;

    // TODO: 08/07/2024 Após o termino de tetes aplicar o valor equivalente a um token expirado que é 86400000
    @Scheduled(fixedRate = 100000) // 5 minutos em milissegundos
    public void checkAndExecuteScript() {

        List<Object[]> idTokensEncontrados = this.findIdTokenExpired();
        if (!idTokensEncontrados.isEmpty()) {
            List<IdTokenResponse> listaDto = new ArrayList<>();

            //adiciona o resultado da query em um dto
            for (Object[] resultado : idTokensEncontrados) {
                Integer idToken = (Integer) resultado[0];
                IdTokenResponse dto = new IdTokenResponse();
                dto.setIdToken(idToken);

                listaDto.add(dto);
            }
            executeDelete(listaDto);
        }
    }

    private List<Object[]> findIdTokenExpired() {
        return tokenRepository.findIdTokenExpired();
    }

    private void executeDelete(List<IdTokenResponse> listaDto) {

        try {
            // realiza o delete de tokens já expirados
            listaDto.forEach(idTokenResponse -> this.tokenRepository.deleteById(idTokenResponse.getIdToken()));

            System.out.println("Deletado com sucesso");

        } catch (Exception e) {
            System.out.println("Falha na tentativa de deletar");
            e.printStackTrace();
        }
    }
}