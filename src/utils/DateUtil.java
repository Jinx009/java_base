package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String DateTime2String(Date theDate){
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.format(theDate);
	}
	
	public static Date String2DateTime(String dateString) throws ParseException{
		
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.parse(dateString);
	}
	
}
