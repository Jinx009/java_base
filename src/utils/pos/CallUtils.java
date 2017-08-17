package utils.pos;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import utils.HttpUtils;

public class CallUtils {
	
    public static String getOrders2() throws Exception {
    	Map<String,String> data = new HashMap<String,String>();
    	data.put("applicationCode", "MAGNETIC_APPLICATION");
    	data.put("path", "/order");
		data.put("baseOrganId", "200023");
        String sign = KeyUtils.sign(data);
        System.out.println(sign);
        data.put("sign", sign);
        data.remove("path");
        String _path = "baseOrganId=200023&applicationCode=MAGNETIC_APPLICATION&sign="+getMyURIEncoder(sign);
        String url = "http://120.92.101.137:8080/trade-api/order?"+_path;
        System.out.println(url);
        return HttpUtils.get(url);
    }
    
    public static String getOrders3() throws Exception {
    	Map<String,String> data = new HashMap<String,String>();
    	data.put("applicationCode", "MAGNETIC_APPLICATION");
    	data.put("path", "/product");
		data.put("size", "40");
        String sign = KeyUtils.sign(data);
        System.out.println(sign);
        data.put("sign", sign);
        data.remove("path");
        String _path = "applicationCode=MAGNETIC_APPLICATION&size=40&sign="+getMyURIEncoder(sign);
        String url = "http://120.92.101.137:8080/park-charge-api/product?"+_path;
        System.out.println(url);
        return HttpUtils.get(url);
    }
    
    public static String insert(String mac, String code, String place, String remark) throws Exception {
    	Map<String,String> data = new HashMap<String,String>();
    	data.put("applicationCode", "MAGNETIC_APPLICATION");
    	data.put("parkPlaceId",place);
    	data.put("code", code);
    	data.put("magneticStripeId", mac);
    	data.put("remark",remark);
    	data.put("path", "/park_place");
        String sign = KeyUtils.sign(data);
        System.out.println(sign);
        data.put("sign", sign);
        data.remove("path");
        String json = JSON.toJSONString(data);
        String url = "http://120.92.101.137:8080/park-charge-api/park_place";
        System.out.println(url);
        return HttpUtils.postJson(url,json);
	}
    
    public static String getPlace() throws Exception {
    	Map<String,String> data = new HashMap<String,String>();
    	data.put("applicationCode", "MAGNETIC_APPLICATION");
    	data.put("path", "/park_place");
        String sign = KeyUtils.sign(data);
        System.out.println(sign);
        data.put("sign", sign);
        data.remove("path");
        String _path = "applicationCode=MAGNETIC_APPLICATION&sign="+getMyURIEncoder(sign);
        String url = "http://120.92.101.137:8080/park-charge-api/park_place?"+_path;
        System.out.println(url);
        return HttpUtils.get(url);
    }

    public static String getOrders() throws Exception {
    	Map<String,String> data = new HashMap<String,String>();
    	data.put("applicationCode", "MAGNETIC_APPLICATION");
    	data.put("path", "/order");
		data.put("baseOrganId", "200023");
		data.put("userId", "100092");
		data.put("start", "1");
		data.put("limit", "100");
        String sign = KeyUtils.sign(data);
        System.out.println(sign);
        data.put("sign", sign);
        data.remove("path");
        String _path = "applicationCode=MAGNETIC_APPLICATION&baseOrganId=200023&userId=100092&start=1&limit=100&sign="+getMyURIEncoder(sign);
        String url = "http://120.92.101.137:8080/trade-api/order?"+_path;
        System.out.println(url);
        return HttpUtils.get(url);
    }

    public static String getMyURIEncoder(String _params){
    	try {  
            String urlString = URLEncoder.encode(_params, "utf-8");  //输出%C4%E3%BA%C3  
            System.out.println(urlString);  
            return urlString; 
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }
    	return "";
    }
    
    
    
    public static void main(String[] args) throws Exception{
    	getPlace();
//    	getMyURIEncoder("WRjKoUXCulXIr9/wY3gRPx+ssW5hnT720xEdDkaki503tN+ZQYtLaVdE1861orsCU4uzEBbY8sf+SDTRX4BylHOKhXUDMh2xdPaJ9LWeHSApiHtGd1sRGPnPldsFpuA9u369KfmWh/eHX7VCI6rFaZFOY46LygE4jYMekrDl+hQ=");
	}

	
	
}
