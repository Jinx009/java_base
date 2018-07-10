package main.entry.webapp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


import database.models.home.HomeResource;
import database.models.home.HomeUser;
import database.models.project.ProDriver;
import utils.ip.IPUtil;
import utils.model.HomeConfigConstant;



@Controller
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	
	/**
	 * 获取客户端ip
	 * @param request
	 * @return
	 */
	public String getClientIp(HttpServletRequest request){
		return IPUtil.getRemortIP(request);
	}
	
	/**
	 * 设置菜单
	 * @param request
	 * @param menus
	 */
	public void setSessionMenu(HttpServletRequest request,List<HomeResource> menus,HomeUser homeUser){
		HttpSession session = request.getSession();
		HomeConfigConstant.putMenu(menus,session.getId());
	}
	
	/**
	 * 退出清空
	 * @param request
	 * @param menus
	 */
	public void setSessionOut(HttpServletRequest request,HomeUser homeUser){
		HttpSession session = request.getSession();
		if(homeUser!=null){
			HomeConfigConstant.putNewSession(homeUser.getUserName(),null);
		}
		HomeConfigConstant.putMenu(null,session.getId());
	}
	
	/**
	 * 设置管理员session
	 * @param request
	 * @param menus
	 */
	public void setSessionAdmin(HttpServletRequest request,HomeUser homeUser){
		HttpSession session = request.getSession();
		session.setAttribute(HomeConfigConstant.HOME_USER, homeUser);
		HomeConfigConstant.putNewSession(homeUser.getUserName(),session.getId());
		logger.warn("[data:{}]",homeUser.getRealName());
		session.setAttribute(HomeConfigConstant.HOME_NAME,homeUser.getRealName());
	}
	
	public void setSessionFront(HttpServletRequest request, ProDriver proUser) {
		HttpSession session = request.getSession();
		session.setAttribute(HomeConfigConstant.FRONT_USER, proUser);
		logger.warn("[data:{} login]",proUser.getMobilePhone());
	}
	
	public ProDriver getSessionFrontUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ProDriver proUser = (ProDriver) session.getAttribute(HomeConfigConstant.FRONT_USER);
		return proUser;
	}
	
	/**
	 * 从session中获取登陆者
	 * @param request
	 * @return
	 */
	public HomeUser getSessionHomeUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		HomeUser homeUser = (HomeUser) session.getAttribute(HomeConfigConstant.HOME_USER);
		return homeUser;
	}
	
}
