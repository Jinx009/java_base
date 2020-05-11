package main.entry.webapp.data;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.socket.Server;
//import main.entry.webapp.qxwz.NettyClient;
import main.entry.webapp.socket.ServerQxwz;
import main.entry.webapp.socket.ServerQxwzMain;
import utils.Resp;

@Controller
@RequestMapping(value = "/server")
public class TcpServerController {

	@RequestMapping(path = "/start")
	@ResponseBody
	public Resp<?>baseDataTcpServer() {
		try {
			new main.entry.webapp.datasocket.Server(1123);//监测院数据收
			new main.entry.webapp.qxwzdata.Server(6666);//千寻本地转发
//			new NettyClient("rtk.ntrip.qxwz.com", 8002).run();
//			new main.entry.webapp.qxwz.Server(7777);//千寻
//			new Server(9999);//监测院数据对外
			new ServerQxwz(9999);
			new ServerQxwzMain(7777);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	
	
}
