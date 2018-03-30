package main.entry.webapp;


import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

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
            OutputStream ots = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(ots);
            pw.write(data);
            pw.flush();
            socket.shutdownOutput();
            pw.close();
            ots.close();
            socket.close();
        } catch (Exception e) {
            log.error("error:{}",e);
            send(data);
        }
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
