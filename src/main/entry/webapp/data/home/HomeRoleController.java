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
	
	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping(path = "/role")
	@ResponseBody
	public Resp<?> getRoles(){
		Resp<?> resp = new Resp<>(false);
		resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,homeRoleService.findAll());
		logger.warn("[data:{}] ",resp);
		return resp;
	}
	
	/**
	 * 新增角色
	 * @param level
	 * @param name
	 * @return
	 */
	@RequestMapping(path = "/role_add")
	@ResponseBody
	public Resp<?> add(Integer level,String name,String description){
		Resp<?> resp = new Resp<>(false);
		try {
			homeRoleService.add(level,name,description);
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
			logger.warn("[data:{},{}]",level,name);
		} catch (Exception e) {
			logger.error("[error:{}]",e);
		}
		return resp;
	}
}
