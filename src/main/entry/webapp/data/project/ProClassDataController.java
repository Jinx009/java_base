package main.entry.webapp.data.project;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProClass;
import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProClassService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/class")
public class ProClassDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProClassDataController.class);
	
	@Autowired
	private ProClassService proClassService;
	
	@RequestMapping(path = "/pageList")
	@ResponseBody
	public Resp<?> getClass(String classDate,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proClassService.homeList(classDate, p));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/add")
	@ResponseBody
	public Resp<?> addClass(String classDate,String name,String time,String desc){
		Resp<?> resp = new Resp<>(true);
		try {
			proClassService.save(classDate, name, time, desc);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "del")
	@ResponseBody
	public Resp<?> deleteClass(Integer id){
		Resp<?> resp = new Resp<>(true);
		try {
			proClassService.delete(id);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "getStatus")
	@ResponseBody
	public Resp<?> getStatus(String userId){
		Resp<?> resp = new Resp<>(true);
		try {
			List<ProClass> list =  proClassService.getStatus(userId);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
