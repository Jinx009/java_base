package main.entry.webapp.data;

import java.net.ServerSocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.Resp;

@Controller
@RequestMapping(value = "/server")
public class TcpServerController {

	@RequestMapping(path = "/dataServer")
	@ResponseBody
	public Resp<?>baseDataTcpServer() {
		try {
			ServerSocket socket = new ServerSocket(8888);
			BaseDataTcpServer st = new BaseDataTcpServer(socket);
			st.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(path = "/deviceServer")
	@ResponseBody
	public Resp<?>deviceTcpServer() {
		try {
			ServerSocket socket = new ServerSocket(9999);
			DeviceTcpServer st = new DeviceTcpServer(socket);
			st.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
