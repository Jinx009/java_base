package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;
import utils.Resp;

@Controller
@RequestMapping(value = "/rest/order")
public class GatewayOrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayOrderDataController.class);
	
	public Resp<?> getLastOrder(String token,Integer orderId,Integer payId,Integer status){
		Resp<?> resp = new Resp<>(false);
		log.warn("token:{},status",token,status);
		return resp;
	}
	
}
