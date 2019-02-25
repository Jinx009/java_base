package utils.ip;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	public static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip != null && ip.length() > 0){
			String[] ipArray = ip.split(",");
			if (ipArray != null && ipArray.length > 1) {
				return ipArray[0];
			}
			return ip;
		}
		
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(System.getenv("mysqlUrl"));
		  Map<String, String> map = System.getenv();
	        for(Iterator<String> itr = map.keySet().iterator();itr.hasNext();){
	            String key = itr.next();
	            System.out.println(key + "=" + map.get(key));
	        }   
	}
}
