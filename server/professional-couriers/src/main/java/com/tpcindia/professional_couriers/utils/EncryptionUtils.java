package com.tpcindia.professional_couriers.utils;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.util.Base64;

public class EncryptionUtils {

    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private Cipher cipher;
    private SecretKey key;

    public EncryptionUtils() {
        try {
            String encryptionKey = "ThisIsSpartaThisIsSparta";
            String encryptionScheme = DESEDE_ENCRYPTION_SCHEME;

            byte[] arrayBytes = encryptionKey.getBytes(UNICODE_FORMAT);
            KeySpec keySpec = new DESedeKeySpec(arrayBytes);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptionScheme);
            cipher = Cipher.getInstance(encryptionScheme);
            key = secretKeyFactory.generateSecret(keySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = Base64.getEncoder().encodeToString(encryptedText);
        } catch (Exception e) {
            encryptedString = unencryptedString;
            e.printStackTrace();
        }
        return encryptedString;
    }

    public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            decryptedText = encryptedString;
            e.printStackTrace();
        }
        return decryptedText;
    }
}

