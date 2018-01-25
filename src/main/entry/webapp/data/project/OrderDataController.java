package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProIoTOrderService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d")
public class OrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(OrderDataController.class);
	
	@Autowired
	private ProIoTOrderService proIoTOrderService;
	
	@RequestMapping(path = "/iot_order_list")
	@ResponseBody
	public Resp<?> findPage(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proIoTOrderService.findUse(p));
		} catch (Exception e) {
			log.error("errpor:{}",e);
		}
		return resp;
	}
	
}
