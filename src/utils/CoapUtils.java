package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import com.alibaba.fastjson.JSONObject;

public class CoapUtils {

	public static void main(String[] args) throws URISyntaxException {
		CoapClient client = new CoapClient("coap://219.142.70.215:5683/device/auth");
		Map<String, Object> map = new HashMap<>();
		map.put("deviceId", "779046");
		map.put("apikey", "a39ccc4d75c9997f61ae");
		String json = JSONObject.toJSONString(map);
		System.out.println(json);
		CoapResponse response = client.post(json, MediaTypeRegistry.APPLICATION_JSON,MediaTypeRegistry.APPLICATION_JSON);
		System.out.println(response.toString());  
		if(response !=null){
//			System.out.println(response.toString());  
			System.out.println(response.getCode());  //打印请求状态码
			System.out.println(response.getOptions());  //选项参数
			System.out.println(response.getResponseText());  //获取内容文本信息
			System.out.println("\nAdvanced\n");    // 
			System.out.println(Utils.prettyPrint(response));  //打印格式良好的输出
		}
	}

	
}
