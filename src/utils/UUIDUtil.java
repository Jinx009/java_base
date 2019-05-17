package utils;

import java.util.Random;
import java.util.UUID;


/**
 * UUID
 * 
 * @author jinx
 *
 */
public class UUIDUtil {
	

	public static String random(){
		return UUID.randomUUID().toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getCph());
	}
	
    public static String getCph(){
    	String str2 = "ABCDEFGHJKLPQS";
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<4;i++){
            int number=random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return "沪"+str2.charAt(random.nextInt(14))+sb.toString()+"警";
    }
	
}
