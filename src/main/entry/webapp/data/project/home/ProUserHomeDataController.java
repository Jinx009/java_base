package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProUserService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_user")
public class ProUserHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProUserHomeDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProUser> list = proUserService.all(p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
