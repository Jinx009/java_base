package utils;

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
		System.out.println(random());
	}
	
}
