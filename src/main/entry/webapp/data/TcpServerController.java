package main.entry.webapp.data;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.nettyTcp.NettyServer;
import main.entry.webapp.socket.Server;
import main.entry.webapp.socket.ServerA;
//import main.entry.webapp.qxwz.NettyClient;
import main.entry.webapp.socket.ServerQxwz;
import main.entry.webapp.socket.ServerQxwzMain;
import utils.Resp;

@Controller
@RequestMapping(value = "/server")
public class TcpServerController {
	
	public static List<NettyServer> list = new ArrayList<NettyServer>();

	@RequestMapping(path = "/start")
	@ResponseBody
	public Resp<?>baseDataTcpServer(int port,int dataFrom,boolean autoSend) {
		try {
//			main.entry.webapp.datasocket.Server server = new main.entry.webapp.datasocket.Server(5555);//监测院数据收1123
			NettyServer nettyServer = new NettyServer(port,dataFrom,autoSend);
			list.add(nettyServer);
//			new main.entry.webapp.qxwzdata.Server(6666);//千寻本地转发
//			new main.entry.webapp.datasocket.ServerA(3333);
//			new NettyClient("rtk.ntrip.qxwz.com", 8002).run();
//			new main.entry.webapp.qxwz.Server(7777);//千寻
//			new Server(9999);//监测院数据对外
//			new ServerA(4444);
//			new ServerQxwz(9999);
//			new ServerQxwzMain(7777);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Resp<>(true);
	}
	
	@RequestMapping(path = "/close")
	@ResponseBody
	public Resp<?>closeServer(int port) {
		try {
			System.out.println(JSONObject.toJSONString(list));
			for(NettyServer server : list){
				if(server.getPort()==port){
					System.out.println("123");
					server.closeServer();
					list.remove(server);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Resp<>(true);
	}
	
	

	
	
}
