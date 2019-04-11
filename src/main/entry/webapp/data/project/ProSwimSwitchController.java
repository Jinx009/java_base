package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProSwimSwitchService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/swimSwitch")
public class ProSwimSwitchController extends BaseController{

	private Logger log = LoggerFactory.getLogger(ProSwimSwitchController.class);
	
	@Autowired
	private ProSwimSwitchService proSwimSwitchService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> page(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proSwimSwitchService.findByPage(p));
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
			return new Resp<>(proSwimSwitchService.findByDateStr(dateStr));
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
			proSwimSwitchService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(Integer type,String dateStr){
		Resp<?> resp = new Resp<>(false);
		try {
			proSwimSwitchService.save(dateStr);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
