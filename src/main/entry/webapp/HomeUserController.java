package main.entry.webapp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.HomeUser;
import service.basicFunctions.HomeUserService;
import utils.model.Resp;

/**
 * 后台账户
 * @author jinx
 *
 */
@Controller
public class HomeUserController extends BaseController{

	private Logger LOG = LoggerFactory.getLogger(HomeUserController.class);
	
	@Autowired
	private HomeUserService homeUserService;
	
	/**
	 * 用户登录
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "/d/user/login")
	@ResponseBody
	public Resp<?>  login(String userName,String pwd,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = homeUserService.login(userName, pwd);
			if(homeUser!=null){
				setSessionHomeUser(homeUser, req);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 用户登出
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/d/user/loginOut")
	@ResponseBody
	public Resp<?> loginOut(HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			setSessionNull(req);
		} catch (Exception e) {
			LOG.error("e:{}",e);
		}
		return resp;
	}
	
}
