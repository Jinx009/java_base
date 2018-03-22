package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProOrderService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/order")
public class ProOrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProOrderDataController.class);
	
	@Autowired
	private ProOrderService proOrderService;
	
	@RequestMapping(path = "/pageList")
	@ResponseBody
	public Resp<?> getClass(String orderDate,Integer p,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderService.homeList(orderDate, p,type));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/saveRemark")
	@ResponseBody
	public Resp<?> saveRemark(Integer id,String remark,Integer realNum){
		Resp<?> resp = new Resp<>(true);
		try {
			proOrderService.saveRemark(id,remark,realNum);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
