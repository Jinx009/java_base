package main.entry.webapp.data.parking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingPosDeviceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingPosDeviceDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ParkingPosDeviceDataController.class);
	
	@Autowired
	private ParkingPosDeviceService parkingPosDeviceService;
	
	@RequestMapping(path = "/posDevice/list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(parkingPosDeviceService.list());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/posDevice/updateStatus")
	@ResponseBody
	public Resp<?> updateStatus(Integer id,Integer status){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingPosDeviceService.update(id, status);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
