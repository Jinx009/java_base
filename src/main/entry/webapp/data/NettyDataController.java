package main.entry.webapp.data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import main.entry.webapp.netty.server.NettyServer;

@Controller
@RequestMapping(value = "/d/netty")
public class NettyDataController extends  BaseController{

	
	@RequestMapping(path = "/start")
	@ResponseBody
	public String start(int port) {
		new Thread() {
			public void run() {
				new NettyServer(port);
			}
		}.start();
		return "ok";
	}
	
}
