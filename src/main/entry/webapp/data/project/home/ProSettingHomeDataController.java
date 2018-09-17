package main.entry.webapp.data.project.home;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProLogService;
import service.basicFunctions.project.ProSettingService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_setting")
public class ProSettingHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProSettingHomeDataController.class);
	
	
	@Autowired
	private ProSettingService proSettingService;
	@Autowired
	private ProLogService proLogService;
	
	@RequestMapping(path = "/get")
	@ResponseBody
	public Resp<?> getStatus(String name){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proSettingService.findByName(name));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/change")
	@ResponseBody
	public Resp<?> changeStatus(String name,Integer status,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			proLogService.saveLog(getSessionHomeUser(req).getRealName(), "更变配置"+name+"为:"+status);
			return new Resp<>(proSettingService.change(name,status));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
