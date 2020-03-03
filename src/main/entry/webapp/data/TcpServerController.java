package main.entry.webapp.data;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.qxwz.NettyClient;
import main.entry.webapp.socket.Server;
import utils.Resp;

@Controller
@RequestMapping(value = "/server")
public class TcpServerController {

	@RequestMapping(path = "/start")
	@ResponseBody
	public Resp<?>baseDataTcpServer() {
		try {
			new main.entry.webapp.datasocket.Server(8888);
			new NettyClient("rtk.ntrip.qxwz.com", 8002).run();
			new main.entry.webapp.qxwz.Server(7777);
			new Server(9999);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	
	
}
