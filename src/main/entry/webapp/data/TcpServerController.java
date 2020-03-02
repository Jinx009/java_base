package main.entry.webapp.data;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.socket.Server;
import utils.Resp;

@Controller
@RequestMapping(value = "/server")
public class TcpServerController {

	@RequestMapping(path = "/dataServer")
	@ResponseBody
	public Resp<?>baseDataTcpServer() {
		try {
			new main.entry.webapp.datasocket.Server(8888);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(path = "/tcpServer")
	@ResponseBody
	public Resp<?>tcpServer() {
		try {
			new Server(9999);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	
}
