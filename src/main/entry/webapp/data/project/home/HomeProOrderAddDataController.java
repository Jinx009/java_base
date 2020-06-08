package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProOrderAddService;
import utils.Resp;

@Controller
@RequestMapping(value = "/h/pro_order_add")
public class HomeProOrderAddDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(HomeProOrderAddDataController.class);
	
	@Autowired
	private ProOrderAddService proOrderAddService;

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> getOrder( Integer p) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderAddService.findByPage(p));
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save( String date,String time,String address,String mobilePhone,String userName,Integer week) {
		Resp<?> resp = new Resp<>(false);
		try {
			proOrderAddService.add(date, time, address, mobilePhone, userName,week);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}
	
}
