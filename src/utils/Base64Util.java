package utils;

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Util {

	/**
	* BASE64解密
	* 
	* @param key
	* @return
	* @throws Exception
	*/
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decodeBase64(key);
	}

	/**
	* BASE64 加密
	* 
	* @param key
	* @return
	* @throws Exception
	*/
	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encodeBase64String(key);
	}
	
}
