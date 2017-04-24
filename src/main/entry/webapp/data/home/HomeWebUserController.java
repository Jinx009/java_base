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

import database.models.web.WebUser;
import service.basicFunctions.web.WebUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/home/d")
public class HomeWebUserController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeWebUserController.class);

	@Autowired
	private WebUserService webUserService;
	
	/**
	 * 前端用户列表
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/web_user_list",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> user(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			List<WebUser> list = webUserService.findAll();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			logger.warn("[data:{}] ",resp);
		} catch (Exception e) {
			logger.warn("[error:{}] ",e);
		}
		return resp;
	}
	
	/**
	 * 更改前端用户状态
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/web_user_status",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> status(Integer status,Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			webUserService.changeStatus(status,id);
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
		} catch (Exception e) {
			logger.warn("[error:{}] ",e);
		}
		return resp;
	}
	
}
