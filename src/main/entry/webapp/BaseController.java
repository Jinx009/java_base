package main.entry.webapp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.StreamClosedHttpResponse;
import utils.StringUtil;
import utils.ip.IPUtil;

@Controller
public class BaseController {

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);
	
	public String getString(Map<String,Object> data, String key){
		String value = String.valueOf(data.get(key));
		if(StringUtil.isBlank(value)){
			return "";
		}
		return value;
	}
	
//	private final static int PORT = 8888;
//	private static final String HOSTNAME = "139.196.205.157";
	    
	
	public static void send(String data,String udpIp,int udpPort){
		  try (DatagramSocket socket = new DatagramSocket(0)) {
	            socket.setSoTimeout(10000);
	            InetAddress host = InetAddress.getByName(udpIp);
	            byte[] buf = toBytes(data);
	            DatagramPacket packet = new DatagramPacket(buf, buf.length,host, udpPort);
//	            socket.receive(packet);
	            socket.send(packet);
	            socket.close();
	            log.warn("send alreay");
	        } catch (IOException e) {
	            log.error("error:{}",e);
	        }
	}
	

    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }
    
	
    @SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {
        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);
        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);
        log.warn("access-token:{},{}",responseLogin.getStatusLine(),responseLogin.getContent());

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("accessToken");
    }
	
	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public String getClientIp(HttpServletRequest request){
		return IPUtil.getRemortIP(request);
	}
	
}
