package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProDriver;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProDriverService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_driver")
public class ProDiverHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProDiverHomeDataController.class);
	
	@Autowired
	private ProDriverService proDriverService;
	
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(Integer p,String mobilePhone,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProDriver> list = proDriverService.homeList(p, name, mobilePhone);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(String mobilePhone,String name,String pwd,String plateNumber){
		Resp<?> resp = new Resp<>(false);
		try {
			proDriverService.save(mobilePhone,name,pwd,plateNumber);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "update")
	@ResponseBody
	public Resp<?> update(String mobilePhone,String name,String pwd,String plateNumber,Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			proDriverService.update(mobilePhone,name,pwd,plateNumber,id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
}
