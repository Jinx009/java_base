package main.entry.webapp.data.project;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProUserService;
import utils.Resp;

@RequestMapping(value = "/data/commom")
@Controller
public class CommonDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(CommonDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String userName,String pwd,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			ProUser proUser = proUserService.login(userName, pwd);
			if(proUser!=null){
				setSessionFront(request, proUser);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
