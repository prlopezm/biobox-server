package mx.com.tecnetia.orthogonal.utils.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AES {
	
    static final String TRANSFORMATION = "AES/GCM/NoPadding";
    static final String ALG0 = "SHA-1";
    static final String ALG1 = "AES";
    static final byte[] SRC = "iv".getBytes();
    static final int LEN = 128;

    private AES() {

    }

    private static SecretKeySpec secretKey;

    public static void setKey(String myKey) {
        byte[] key;
        MessageDigest sha = null;
        try {
            key =  myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance(ALG0);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALG1);
        } catch (NoSuchAlgorithmException e) {
            log.error("Ocurrió la siguiente excepción en el método estático de AES: {}", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(LEN, SRC);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            var ret = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
            log.info("Cadena a encriptar: {} . Cadena encriptada: {}", strToEncrypt, ret);
            return ret;
        } catch (Exception e) {
            log.error("Error al encriptar: {}", e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);            
            GCMParameterSpec parameterSpec = new GCMParameterSpec(LEN, SRC);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            log.info("Cadena para desencriptar: {} ", strToDecrypt);
            var ret = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            log.info("Cadena para desencriptar: {} . Cadena desencriptada: {}", strToDecrypt, ret);
            return ret;
        } catch (Exception e) {
            log.error("Error al desencriptar: {}", e.toString());
        }
        return null;
    }

    public static String obtenerPalabraAleatoria() {
        final int MIN_LENGTH = 8;

        java.util.Random r = new java.util.Random();

        char[] cadenaCaracteres = { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
                'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '2', '3', '4', '5', '6', '7', '8', '9', };

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < MIN_LENGTH; i++) {
            sb.append(cadenaCaracteres[r.nextInt(cadenaCaracteres.length)]);
        }

        return sb.toString();
    }
}
