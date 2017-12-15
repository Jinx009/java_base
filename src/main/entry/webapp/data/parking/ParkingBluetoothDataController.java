package main.entry.webapp.data.parking;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.parking.ParkingBluetooth;
import main.entry.webapp.BaseController;
import service.basicFunctions.parking.ParkingBluetoothService;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingBluetoothDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ParkingBluetoothDataController.class);
	
	@Autowired
	private ParkingBluetoothService parkingBluetoothService;
	
	@RequestMapping(path = "/bluetooth/list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ParkingBluetooth> list = parkingBluetoothService.findAll();
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,list);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/bluetooth/del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingBluetoothService.delete(id);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,null);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/bluetooth/saveOrUpdate")
	@ResponseBody
	public Resp<?> update(String uuid,String carNo,String mobilePhone){
		Resp<?> resp = new Resp<>(false);
		try {
			parkingBluetoothService.saveOrUpdate(uuid,carNo,mobilePhone);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,null);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	
}
