package main.entry.webapp;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import database.models.WebTokenFactory;
import database.models.home.HomeResource;
import database.models.home.HomeUser;
import service.basicFunctions.WebTokenFactoryService;
import utils.ip.IPUtil;
import utils.model.HomeConfigConstant;
import utils.wechat.WechatData;
import utils.wechat.WechatJSSign;

@Controller
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private WebTokenFactoryService webTokenFactoryService;

	/**
	 * 微信分享jsapi
	 * @param request
	 */
	public void getJsApi(HttpServletRequest request){
		String url = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		try {
			if (null != queryString && !"".equals(queryString)) 
				url = url + "?" + queryString;
			WebTokenFactory webTokenFactory = webTokenFactoryService.getByTypeAndId(WechatData.APP_ID,1);
			Map<String, String> ret;
			ret = WechatJSSign.createSign(webTokenFactory.getTokenValue(),url, WechatData.APP_ID, WechatData.APP_SECRET);
			request.setAttribute("appId", WechatData.APP_ID);
			request.setAttribute("timestamp", ret.get("timestamp").toString());
			request.setAttribute("nonceStr", ret.get("nonceStr").toString());
			request.setAttribute("signature", ret.get("signature").toString());
			request.setAttribute("desc","");
		} catch (Exception e) {
			logger.error("[BaseController.getJsApi.error:{}]",e);
		} 
	}
	
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
		HomeConfigConstant.putNewSession(homeUser.getUserName(),session.getId());
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
		logger.warn("[BaseController.setSessionAdmin.data:{}]",homeUser.getRealName());
		session.setAttribute(HomeConfigConstant.HOME_NAME,homeUser.getRealName());
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
