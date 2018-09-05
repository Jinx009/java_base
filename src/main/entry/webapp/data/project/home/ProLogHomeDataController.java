package main.entry.webapp.data.project.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProLogService;
import utils.Resp;

@RequestMapping(value = "/home/d/pro_log")
@Controller
public class ProLogHomeDataController extends BaseController{

	@Autowired
	private ProLogService proLogService;
	
	@RequestMapping(path = "/homeList")
	@ResponseBody
	public Resp<?> homeList(Integer p){
		return new Resp<>(proLogService.homeList(p));
	}
	
}
