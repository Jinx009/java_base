package common.helper;

import java.security.MessageDigest;

public class MD5Util {

	/**
	 * 小写MD5方法
	 * @param s
	 * @return
	 */
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
     * 大写MD5方法
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 和方法一结果一致
     * @param s
     * @return
     */
    public final static String md5(String s) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return null;
        }
        char[] charArray = s.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
      
    public static void main(String[] args){  
         System.out.println(toMD5("jinx007"));  
         System.out.println(MD5("jinx007"));  
         System.out.println(md5("jinx007"));  
         double a = 1;
         for(int i = 1;i<=36;i++){
        	 a = a*i;
        	 System.out.println(a);
         }
         long b = 1;
         for(int i = 1;i<=6;i++){
        	 b = b*i;
         }
         System.out.println(b/a);
    }  
}
