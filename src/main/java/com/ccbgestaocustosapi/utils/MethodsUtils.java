package com.ccbgestaocustosapi.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

// todos os metodos que poderão se utilizados em outros momentos serão implementados aqui.
@Component
public class MethodsUtils {

    private static final String key = "63686176655365637265743132333334"; // 16, 24, or 32 bytes
    private static final String iv = "64617461536563726574313233333435"; // 16 bytes



    // Função para descriptografar uma string Base64 com AES
    // Após implementar esse decrypt, a senha passada para o payload é descriptografada com a senha chave e
    // depois passado de base64 para string para ser utilizada
    // TODO: 26/06/2024 implementar no cadastro de usuário da mesma lógica para o processo funcianar de forma segura.
    public String decryptFromBase64(String base64) {
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

    // TODO: 30/06/2024 Realizar um interval que deleta os tokens já expirados
}
