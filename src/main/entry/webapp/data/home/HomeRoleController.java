package main.entry.webapp.data.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Setter;
import service.basicFunctions.home.HomeRoleService;
import utils.Resp;
import utils.RespData;

@Controller
@Setter
@RequestMapping(value = "/home/d")
public class HomeRoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeRoleController.class);

	@Autowired
	private HomeRoleService homeRoleService;
	
	private Resp<?> resp;
	
	@RequestMapping(path = "/role")
	@ResponseBody
	public Resp<?> getRoles(){
		resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,homeRoleService.findAll());
		logger.warn(" [HomeRoleController.getRoles][data:{}] ",resp);
		return resp;
	}
}
