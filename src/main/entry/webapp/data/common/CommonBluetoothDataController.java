package main.entry.webapp.data.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingBluetooth;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingBluetoothService;
import utils.Resp;

@Controller
@RequestMapping(value = "/common/bluetooth")
public class CommonBluetoothDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(CommonBluetoothDataController.class);
	
	@Autowired
	private ParkingBluetoothService parkingBluetoothService;
	
	@RequestMapping(path = "/findByUuid")
	@ResponseBody
	public Resp<?> getByUuid(String uuid){
		Resp<?> resp = new Resp<>(false);
		try {
			ParkingBluetooth parkingBluetooth = parkingBluetoothService.findByUid(uuid);
			return new Resp<>(parkingBluetooth);
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
