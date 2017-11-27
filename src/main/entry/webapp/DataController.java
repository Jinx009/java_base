package main.entry.webapp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeUser;
import service.basicFunctions.home.HomeUserService;
import utils.model.BaseConstant;
import utils.model.Resp;

@Controller
@RequestMapping(value = "/data")
public class DataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(DataController.class);

	@Autowired
	private HomeUserService homeUserService;
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String username,String pwd,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = homeUserService.login(username, pwd);
			if(homeUser!=null){
				setSessionHomeUser(homeUser,request);
				return new Resp<>(true);
			}else{
				return new Resp<>(BaseConstant.NOT_VALIDATE_COE,BaseConstant.NOT_VALIDATE_MSG,null);
			}
		} catch (Exception e) {
			log.error("error:{]",e);
		}
		return resp;
	}
	
}
