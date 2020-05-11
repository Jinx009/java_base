package utils.bearhunting;

import utils.MD5Util;

public class KeyUtils {

	 public static final String STATUS_URL = "http://bearhuting.natapp1.cc/api/earthMagnetic/statusChangePush";
//	    public static final String STATUS_FIRE_URL = "http://58.247.128.138:8091/api/earthMagnetic/statusChangePush";
	 public static final String STATUS_FIRE_URL = "http://139.224.69.150:18088/api/earthMagnetic/statusChangePush";

	    public static String get(String s){
	        String str = s+"BEARHUNTING";
	        return MD5Util.toMD5(str).toLowerCase();
	    }
	
}
