package main.entry.webapp.data.project.home;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProUserService;
import utils.Resp;

@Controller
@RequestMapping(value = "/h/pro_user")
public class HomeProUserDataController extends BaseController{

	private static Logger log = LoggerFactory.getLogger(HomeProUserDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proUserService.findList(p));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	

	
	
}
