package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingCharge;
import service.basicFunctions.parking.ParkingChargeService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingChargeDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingChargeDataController.class);

	@Autowired
	private ParkingChargeService parkingChargeService;
	
	/**
	 * 收费规则列表
	 * @return
	 */
	@RequestMapping(path = "/charge")
	@ResponseBody
	public Resp<?> findAll(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingCharge> list = parkingChargeService.findAll();
			logger.warn("charge data:{]",list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("charge error:{} ",e);
		}
		return resp;
	}
	
	/**
	 * 保存一条新的收费规则
	 * @param parkingCharge
	 * @return
	 */
	public Resp<?> save(ParkingCharge parkingCharge){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("save data:{}",parkingCharge);
			parkingChargeService.save(parkingCharge);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("save error:{}",e);
		}
		return resp;
	}
	
	public Resp<?> dele(@RequestParam(value = "id",required = false)Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingChargeService.del(id);
			resp = new Resp<>("");
			return resp;
		} catch (Exception e) {
			logger.error("dele error:{}",e);
		}
		return resp;
	}
	
}
