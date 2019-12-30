package main.entry.webapp.data;

import java.net.DatagramSocket;
import java.util.Date;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingArea;
import service.basicFunctions.parking.ParkingAreaService;
import utils.Resp;

@RequestMapping(value = "/home/cloud/server")
@Controller
public class ServerCheckController {

//	private static final Logger log = LoggerFactory.getLogger(ServerCheckController.class);
	
	@Autowired
	private ParkingAreaService parkingAreaService;
	
	@RequestMapping(path = "check")
	@ResponseBody
	public Resp<?> check(Integer id){
//		log.warn("id:{}",id);
		Resp<?> resp = new Resp<>(true);
		ParkingArea parkingArea = parkingAreaService.find(id);
		parkingArea.setCreateTime(new Date());
		parkingAreaService.update(parkingArea);
		return resp;
	}
	
	/**
	 * 启动原始联通版本的udp倾角传感器服务器
	 * @return
	 */
	@RequestMapping(path = "/udpServer")
	@ResponseBody
	public Resp<?> udpServer() {
		try {
			DatagramSocket socket = new DatagramSocket(1121);
			UDPServerThread st = new UDPServerThread(socket);
			st.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(parkingAreaService.findAll());
		return resp;
	}
	
}
