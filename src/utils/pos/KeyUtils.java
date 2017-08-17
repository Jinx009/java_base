package utils.pos;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;

import common.helper.MD5Util;
import utils.HttpUtils;
import utils.sign.CipherUtil;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Comparator;
import java.util.HashMap;
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
        String md5Value = MD5Util.md5(dataString).toUpperCase();
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
    
    public static String sendNotice() throws Exception {
    	Map<String,String> data = new HashMap<String,String>();
		data.put("baseOrganId", "200023");
		data.put("path", "/order?applicationCode=MAGNETIC_APPLICATION&baseOrganId=200023");
		data.put("applicationCode", "MAGNETIC_APPLICATION");
        String sign = KeyUtils.sign(data);
        data.put("sign", sign);
        data.remove("path");
        String _path = "applicationCode=MAGNETIC_APPLICATION&baseOrganId=200023&sign="+sign;
        String url = "http://120.92.101.137:8080/trade-api/order?"+_path;
        String json = JSON.toJSONString(data);
        System.out.println(json);
//        return HttpUtils.postJson("http://120.92.101.137:8080/trade-api/order", json);
        return HttpUtils.get(url);
    }
    public static String setCode() throws Exception {
    	Map<String,String> data = new HashMap<String,String>();
		data.put("userId", "100092");
		data.put("baseOrganId", "200023");
		data.put("topOrganId", "200023");
		data.put("limit", "100");
		data.put("start", "1");
		data.put("path", "/order?applicationCode=MAGNETIC_APPLICATION&baseOrganId=200023&&start=1&topOrganId=200023&userId=100092");
		data.put("applicationCode", "MAGNETIC_APPLICATION");
        String sign = KeyUtils.sign(data);
        data.put("sign", sign);
        data.remove("path");
        String _path = "applicationCode=MAGNETIC_APPLICATION&baseOrganId=200023&sign="+sign+"&start=1&topOrganId=200023&userId=100092";
        String url = "http://120.92.101.137:8080/trade-api/order?"+_path;
        String json = JSON.toJSONString(data);
        System.out.println(json);
//        return HttpUtils.postJson("http://120.92.101.137:8080/trade-api/order", json);
        return HttpUtils.get(url);
    }
    
//    public static String sendNotice() throws Exception {
//    	Map<String,String> data = new HashMap<String,String>();
//		data.put("userId", "100092");
//		data.put("baseOrganId", "200023");
//		data.put("topOrganId", "200023");
//		data.put("limit", "100");
//		data.put("start", "1");
//		data.put("path", "/order?applicationCode=MAGNETIC_APPLICATION&baseOrganId=200023&limit=100&sign=&start=1&topOrganId=200023&userId=100092");
//		data.put("applicationCode", "MAGNETIC_APPLICATION");
//        String sign = KeyUtils.sign(data);
//        data.put("sign", sign);
//        data.remove("path");
//        String _path = "applicationCode=MAGNETIC_APPLICATION&baseOrganId=200023&limit=100&sign="+sign+"&start=1&topOrganId=200023&userId=100092";
//        String url = "http://120.92.101.137:8080/trade-api/order?"+_path;
//        String json = JSON.toJSONString(data);
//        System.out.println(json);
////        return HttpUtils.postJson("http://120.92.101.137:8080/trade-api/order", json);
//        return HttpUtils.get(url);
//    }
    
    public static void main(String[] args) throws Exception{
    	String e = "ebm=12296&lt=2017-08-16 17:19:58&mc=030000fffe080303&pm=525400c76ecf&sn=fE2CaLs7sUWCXAxQ&tp=3&mac=525400c76ecf";
    	System.out.println(decrypt(e,"qiO7eqlUrG3yrLiX"));
	}
    
    private  static String decrypt(String body, String appSecret){
        String md5Sign = null;

        try {

            byte[] result = hexStr2ByteArr(body);

            md5Sign = new String(CipherUtil.decrypt(appSecret.getBytes("utf-8"), result), "utf-8");

        } catch (Exception e) {

           e.printStackTrace();

        }

        return md5Sign;
    }
    
	public static String toHexString(byte[] byteArray){
		StringBuffer sha1StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				sha1StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				sha1StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return sha1StrBuff.toString();
	}

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
}
