package main.entry.webapp.data;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import main.entry.webapp.netty.client.NettyClient;
import main.entry.webapp.netty.client.NettyClientModel;
import main.entry.webapp.netty.server.NettyServer;
import main.entry.webapp.netty.server.NettyServerModel;
import utils.Resp;

@Controller
@RequestMapping(value = "/netty")
public class TcpServerController {
	
	public static List<NettyServer> list = new ArrayList<NettyServer>();
	public static List<NettyClient> list2 = new ArrayList<NettyClient>();

	@RequestMapping(path = "/server")
	@ResponseBody
	public Resp<?>baseDataTcpServer(int port,int dataFrom,boolean autoSend) {
		try {
			NettyServer nettyServer = new NettyServer(port,dataFrom,autoSend);
			list.add(nettyServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Resp<>(true);
	}
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?>list() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<NettyClientModel> clients = new ArrayList<NettyClientModel>();
			List<NettyServerModel> servers = new ArrayList<NettyServerModel>();
			for(NettyServer server : list){
				NettyServerModel s = new NettyServerModel();
				s.setAutoSend(server.isAutoSend());
				s.setDataFrom(server.getDataFrom());
				s.setPort(server.getPort());
				servers.add(s);
			}
			for(NettyClient client : list2){
				NettyClientModel c = new NettyClientModel();
				c.setDataFrom(client.getDataFrom());
				c.setIp(client.getIp());
				c.setPort(client.getPort());
				clients.add(c);
			}
			map.put("client", clients);
			map.put("server", servers);
			return new Resp<>(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Resp<>(true);
	}
	
	
	@RequestMapping(path = "/client")
	@ResponseBody
	public Resp<?>baseDataTcpClient(int port,int dataFrom,String ip) {
		try {
			NettyClient nettyClient = new NettyClient(ip,port,dataFrom);
			list2.add(nettyClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Resp<>(true);
	}
	
	@RequestMapping(path = "/closeServer")
	@ResponseBody
	public Resp<?>closeServer(int port) {
		try {
			for(NettyServer server : list){
				if(server.getPort()==port){
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
	
	@RequestMapping(path = "/closeClient")
	@ResponseBody
	public Resp<?>closeClient(int port) {
		try {
			for(NettyClient client : list2){
				if(client.getDataFrom()==port){
					client.setDataFrom(0);
					list2.remove(client);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Resp<>(true);
	}
	
	@RequestMapping(path = "/tcp")
	public String tcp() {
		return "/tcp";
	}
	
	

	
	
}
