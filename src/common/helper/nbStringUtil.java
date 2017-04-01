package common.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

public class nbStringUtil {

	
	/**
	 * MD5加密
	 *  
	 * @param data
	 * @return
	 * @throws Exception
	 */

	public static byte[] encryptMD5(byte[] data) throws Exception {
	
		return DigestUtils.md5Digest(data);
		
	}
	
	/**
	 * MD5加密
	 *  
	 * @param data
	 * @return
	 * @throws Exception
	 */

	public static String encryptMD5(String text) throws Exception {
		
		byte[] data = text.getBytes();
		String cryptedData = DigestUtils.md5DigestAsHex(data);
		String crypted = Base64.encodeBase64String(cryptedData.getBytes());
		
		return crypted;
	}
	
	
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
	
	public static String DateTime2String(Date theDate){
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.format(theDate);
	}
	
	public static Date String2DateTime(String dateString) throws ParseException{
		
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.parse(dateString);
	}
	
}
