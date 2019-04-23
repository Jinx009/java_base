package utils;

import java.security.MessageDigest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.DigestUtils;

public class MD5Util {

    public final static String toMD5(String s)   {  
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };  
        try{  
            byte[] strTemp = s.getBytes();  
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
            mdTemp.update(strTemp);  
            byte[] md = mdTemp.digest();  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++){  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        }   
        catch (Exception e){  
            return null;  
        }  
    }  
    
    
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
	
      
    public static void main(String[] args){  
         System.out.println(toMD5("admin").toLowerCase());  
    }  
}
