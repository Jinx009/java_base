package main.entry.webapp.data.pro;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.pro.ProAdminUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.pro.ProAdminUserService;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/front/d")
public class ProAdminUserDataController extends BaseController{

	@Autowired
	private ProAdminUserService proAdminUserService;
	
	/**
	 * 后台login
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String userName,String pwd,HttpServletRequest request){
		Resp<?> resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,BaseConstant.HTTP_ERROR_MSG,"");
		try {
			ProAdminUser proAdminUser = proAdminUserService.login(userName, pwd);
			if(proAdminUser!=null){
				setSession(request, "admin_session", proAdminUser);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			
		}
		return resp;
	}
	
}
