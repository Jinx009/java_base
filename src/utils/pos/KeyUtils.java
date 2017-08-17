package utils.pos;

import org.apache.commons.codec.binary.Base64;


import common.helper.MD5Util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jinx on 4/18/17.
 */
public class KeyUtils {

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PRIVARE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK2zn7sQeJlpMgKMy3DMObMzCuM8KszqES3zy7PO6kHHm5rAyVrdhh23M+EtrwJVTqcHDfCiIeslbdl0mI3gz2myxgUgjS3MVpOgk60TYBNkktjABegbtEuX1RcPrMU5MROm7shve2NUcRq4ZSKf0nxt6JhmedytYp4uEkI0/IKRAgMBAAECgYEAj9+nHx+OdKtARAJwg7Z02GwfyYXNgbsijahQgleRiviVd0HVxhCUhMp5Czt2qP0Vz501O6pfY9C1wxcjQOkDghAJPdV/4h3mIPYVJujFVm9+c/pCXZ3esofJ/M1iyGPned5/TWjiUIJl3hh+2sLUJMV5KX7jsc0z7W4fM/4yeb0CQQDgzD01qeCIGdzFY1p4hSSCxBfE17V/OAUdGDHsKEcuneThdPjsKWMPI88NDYfRtGINjBH1A4HKEePD4DRZ8u0jAkEAxc/H1q0lEXQvUQ+A/y7JrZoMzol1RWvinrpm+BqRFz9dqIJaDNAHvzk7NWsRCF2cZPIafj8fVZ+nEJLrta8uuwJAGq0B4nlbNKtlAGNu2/howb/FVk2GsycRrEcvvWd2Mvj3rS11UIkEwUotis39PQxbymHBy8Jzx2fiEF9ttvLV4wJAHJGCog2Fkfy+rK1ZiwE93VWnTdjqV+lZ5GS1ZFWp6LqdEy4oRTtyMvrYA0IARr4GTUnt65fANcSGA03Evwua6wJAaEyPwLc13IfIgLj0UA5jUhP2QEkRH9Nz7QiJOAazNILw0rdQKFdCiNsy0v/pvDD5ytLHWcRU31l4KiayeBzDCQ==";

    /**
     * 获取签名
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String sign(Map<String, String> data) throws Exception {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String dataString = getDataString(data);
        String md5Value = MD5Util.md5(dataString);
        String signVaue = sign(Base64.decodeBase64(md5Value), PRIVARE_KEY);
        return signVaue;
    }

    /**
     * map排序
     *
     * @param data
     * @return
     */
    private static String getDataString(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String str1, String str2) {
                        return str1.compareTo(str2);
                    }
                });
        sortMap.putAll(data);
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : sortMap.entrySet()) {
            result.append(entry.getValue());
        }
        return result.toString();
    }


    /**
     * 签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }
    
}
