package main.entry.webapp.data.home;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.home.HomeUserService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/homeUser")
public class HomeUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(HomeUserController.class);

	@Autowired
	private HomeUserService homeUserService;

	/**
	 * 用户登录
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> login(String userName,String pwd,HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = homeUserService.login(userName, pwd);
			if(homeUser!=null){
				setSessionHomeUser(request, homeUser);
				return new Resp<>(true);
			}else{
				resp.setMsg("Your user name or password is not correct ！");
				return resp;
			}
		} catch (Exception e) {
			logger.warn("[error:{}] ", e);
		}
		return resp;
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> register(String userName,String pwd,String realName) {
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = homeUserService.findByUserName(userName);
			if(homeUser!=null){
				resp.setMsg("user name has exits!");
				return resp;
			}else{
				homeUserService.register(userName, pwd, realName);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			logger.error("[error:{}] ", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/loginOut", method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> loginOut(HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
			setSessionHomeUser(request, null);
			return new Resp<>(true);
		} catch (Exception e) {
			logger.error("[error:{}] ", e);
		}
		return resp;
	}

	
}
