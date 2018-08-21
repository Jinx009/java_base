package main.entry.webapp;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


import database.models.home.HomeUser;



@Controller
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	
	/**
	 * 设置管理员session
	 * @param request
	 * @param menus
	 */
	public void setSessionHomeUser(HttpServletRequest request,HomeUser homeUser){
		HttpSession session = request.getSession();
		session.setAttribute("paperUser", homeUser);
		logger.warn("[data:{}]",homeUser.getRealName());
		session.setAttribute("paperUserRealName",homeUser.getRealName());
	}
	
	
	/**
	 * 从session中获取登陆者
	 * @param request
	 * @return
	 */
	public HomeUser getSessionHomeUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		HomeUser homeUser = (HomeUser) session.getAttribute("paperUser");
		return homeUser;
	}
	
}
