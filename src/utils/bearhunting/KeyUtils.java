package utils.bearhunting;

import utils.MD5Util;

public class KeyUtils {

	 public static final String STATUS_URL = "http://bearhuting.natapp1.cc/api/earthMagnetic/statusChangePush";
//	    public static final String STATUS_FIRE_URL = "http://58.247.128.138:8091/api/earthMagnetic/statusChangePush";
	 public static final String STATUS_FIRE_URL = "http://dataservice.bearhunting.cn/public/api/sis/v1/event/nbiot/earthmagnetic/zhanway";

	    public static String get(String s){
	        String str = "pk=d1a66e2630e618ecd0c6995d6a6a905f&"+s+"&salt=94hpnV_G$-SN+xRw";
	        return MD5Util.toMD5(str).toLowerCase();
	    }
	
}