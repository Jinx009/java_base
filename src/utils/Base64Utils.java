package utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utils {

	public static String getEncodedBase64(String plainText){
        String encoded = null;
        try {
            byte[] bytes =plainText.getBytes("UTF-8");
            encoded = Base64.getEncoder().encodeToString(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        return encoded;
    }

    public static byte[] getDecodedBase64(String plainText){
        byte[] decoded = null;
        try {
            byte[] bytes =plainText.getBytes("UTF-8");
            decoded = Base64.getDecoder().decode(bytes);
        } catch (Exception e) {
        }
        return decoded;
    }
}
