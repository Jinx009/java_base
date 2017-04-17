package main.entry.webapp.data.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.web.WebUse;
import service.basicFunctions.web.WebUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/home/d")
public class HomeWebUserController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeWebUserController.class);

	@Autowired
	private WebUserService webUserService;
	
	@RequestMapping(path = "/web_user_list",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> user(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			List<WebUse> list = webUserService.findAll();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			logger.warn(" [HomeWebUserController.user][data:{}] ",resp);
		} catch (Exception e) {
			resp = new Resp<>(false);
			resp.setMsg(e.getMessage());
			logger.warn(" [HomeWebUserController.user][error:{}] ",e);
		}
		return resp;
	}
	
}
