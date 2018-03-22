package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProClassOrderService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/classOrder")
public class ProClassOrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProClassOrderDataController.class);
	
	@Autowired
	private ProClassOrderService proClassOrderService;
	
	@RequestMapping(path = "/pageList")
	@ResponseBody
	public Resp<?> getClass(String classDate,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proClassOrderService.homeList(classDate, p));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/saveRemark")
	@ResponseBody
	public Resp<?> saveRemark(Integer id,String remark){
		Resp<?> resp = new Resp<>(true);
		try {
			proClassOrderService.saveRemark(id,remark);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/changeStatus")
	@ResponseBody
	public Resp<?> changeType(Integer id,Integer status){
		Resp<?> resp = new Resp<>(true);
		try {
			proClassOrderService.changeStatus(id,status);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
