package main.entry.webapp;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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
	
	public static void send(String data){
		try {
            Socket socket = new Socket("localhost",8888);
            if(socket!=null&&socket.isConnected()){
            	OutputStream ots = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(ots);
                pw.write(data);
                pw.flush();
                socket.shutdownOutput();
                pw.close();
                ots.close();
                socket.close();
            }else{
            	log.error("socket is not connection");
            }
        } catch (Exception e) {
            log.error("error:{}",e);
            send(data);
        }
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
