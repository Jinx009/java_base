package main.entry.webapp.data.parking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingSensorInfoService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingSensorInfoDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ParkingSensorInfoDataController.class);
	
	@Autowired
	private ParkingSensorInfoService parkingSensorInfoService;
	
	@RequestMapping(path = "/sensorInfoList")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(parkingSensorInfoService.list());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
