package utils.chaozhou;

import utils.SHA1Utils;

public class KeyUtils {

	 public static final String DEVICE_URL = "http://carstop.jmwyw.com/action";
	    public static final String STATUS_URL = "http://backsite.czparking.com/api/parkPlaceChange";


	    public static String get(String s){
	        return SHA1Utils.encode(s);
	    }
	
}
