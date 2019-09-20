package main.entry.webapp.data.wenshidu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.wenshidu.WenshiduDevice;
import main.entry.webapp.BaseController;
import service.basicFunctions.wenshidu.WenshiduDeviceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/wsdDevice")
public class WenshiduDeviceDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(WenshiduDeviceDataController.class);
	
	@Autowired
	private WenshiduDeviceService wenshiduDeviceService;
	
	@ResponseBody
	@RequestMapping(path = "list")
	public Resp<?> list(){
		List<WenshiduDevice> list = wenshiduDeviceService.findAll();
		log.warn("list:{}",list);
		return new Resp<>(list);
	}
	
}
