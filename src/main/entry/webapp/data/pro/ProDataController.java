package main.entry.webapp.data.pro;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import database.models.pro.ProBluetoothDevice;
import service.basicFunctions.pro.ProBluetoothDeviceService;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class ProDataController {

	private static final Logger logger = LoggerFactory.getLogger(ProDataController.class);
	
	@Autowired
	private ProBluetoothDeviceService proBluetoothDeviceService;
	
	@RequestMapping(path = "/blue")
	@ResponseBody
	public Resp<?> device(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ProBluetoothDevice> list = proBluetoothDeviceService.findAll();
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,list);
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/carNo")
	@ResponseBody
	public Resp<?> carNo(String uuid){
		Resp<?> resp = new Resp<>(false);
		try {
			String carNo = proBluetoothDeviceService.findCarNo(uuid);
			return new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,carNo);
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
}
