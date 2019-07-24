package crawler.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class EncryptString {
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
    public static String generateToken(){
        UUID uuid = UUID.randomUUID();
        String intitString = uuid.toString();
        String[] tokenSplit = intitString.split("-");
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < tokenSplit.length; i++) {
            token.append(tokenSplit[i]);
        }
        return token.toString();
    }
    public static String generateSalt() {
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        return salt.substring(0, 6);
    }

    public static String hashPassword(String password) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md5.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            stringBuilder.append(String.format("%x", b));
        }
        return stringBuilder.toString();
    }

}
