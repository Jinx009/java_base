package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(BufferUtils.class);
	

	public static String add(String...strings){
		StringBuilder stringBuilder = new StringBuilder();
		try {
			for(String s:strings){
				stringBuilder.append(s);
			}
			logger.warn("data:{}",stringBuilder);
			return stringBuilder.toString();
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return null;
	}
	
}
