package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String encrypt(String password) throws NoSuchAlgorithmException {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] bytes = md.digest(password.getBytes());

            StringBuilder builder = new StringBuilder();

            for(byte b : bytes) {
                builder.append(String.format("%02x", b));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new NoSuchAlgorithmException("No Such Algorithm");
        }
    }


    public static boolean verify(String password, String encryptedPassword) throws NoSuchAlgorithmException {
        return encrypt(password).equals(encryptedPassword);
    }

}
