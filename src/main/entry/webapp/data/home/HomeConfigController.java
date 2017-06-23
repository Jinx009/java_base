package main.entry.webapp.data.home;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeUser;
import lombok.Setter;
import main.entry.webapp.BaseController;
import service.basicFunctions.home.HomeUserService;
import utils.BaseConstant;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/home/config")
@Setter
public class HomeConfigController extends BaseController{
	
	@Autowired
	private HomeUserService homeUserService;

	@RequestMapping(path = "/ip")
	@ResponseBody
	public String getIp(HttpServletRequest request){
		return getClientIp(request);
	}
	
	/**
	 * 登陆
	 * @param request
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(HttpServletRequest request,
						@RequestParam(name = "userName",required = false)String userName,
						@RequestParam(name = "pwd",required = false)String pwd){
		Resp<?> resp = new Resp<>(false);
		HomeUser homeUser = homeUserService.login(userName, pwd);
		if(null!=homeUser){
			setSessionAdmin(request,homeUser);
			resp.setCode(RespData.OK_CODE);
			resp.setMsg(RespData.OK_MSG);
		}else{
			resp.setCode(RespData.OK_CODE);
			resp.setMsg(RespData.LOGIN_FAILE_MSG);
		}
		return resp;
	}
	
	/**
	 * 登出
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/loginOut")
	@ResponseBody
	public Resp<?> loginOut(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		HomeUser homeUser = getSessionHomeUser(request);
		setSessionOut(request, homeUser);
		resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,null);
		return resp;
	}
	
}
