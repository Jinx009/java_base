package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProSwitchService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/switch")
public class ProSwitchController extends BaseController{

	private Logger log = LoggerFactory.getLogger(ProSwitchController.class);
	
	@Autowired
	private ProSwitchService proSwitchService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> page(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proSwitchService.pageList(p));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/find")
	@ResponseBody
	public Resp<?> findByDateStr(String dateStr,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proSwitchService.findByDateStr(dateStr, type));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			proSwitchService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(Integer type,String dateStr,String reason){
		Resp<?> resp = new Resp<>(false);
		try {
			proSwitchService.save(dateStr, type,reason);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
