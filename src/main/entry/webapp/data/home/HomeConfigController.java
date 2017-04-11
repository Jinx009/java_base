package main.entry.webapp.data.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeResource;
import database.models.home.HomeUser;
import lombok.Setter;
import main.entry.webapp.BaseController;
import service.basicFunctions.home.HomeResourceService;
import service.basicFunctions.home.HomeUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/home/config")
@Setter
public class HomeConfigController extends BaseController{
	
	@Autowired
	private HomeUserService homeUserService;
	@Autowired
	private HomeResourceService homeResourceService;

	private Resp<?> resp;
	
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
		resp = new Resp<>(false);
		HomeUser homeUser = homeUserService.login(userName, pwd);
		if(null!=homeUser){
			List<HomeResource> menus = homeResourceService.getMenu(homeUser.getId());
			setSessionMenu(request,menus,homeUser);
			resp.setCode(RespData.OK_CODE);
			resp.setMsg(RespData.OK_MSG);
		}else{
			resp.setCode(RespData.OK_CODE);
			resp.setMsg(RespData.LOGIN_FAILE_MSG);
		}
		return resp;
	}
	
}
