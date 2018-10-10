package main.entry.webapp.data.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.home.HomeUserLoginLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/homeUserLoginLog")
public class HomeUserLoginLogConreoller extends BaseController{

	@Autowired
	private HomeUserLoginLogService homeUserLoginLogService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> findAll(){
		return new Resp<>(homeUserLoginLogService.find());
	}
	
}
