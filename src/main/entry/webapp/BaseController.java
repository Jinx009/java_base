package main.entry.webapp;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import database.models.home.HomeResource;
import database.models.home.HomeUser;
import database.models.project.ProToken;
import service.basicFunctions.HttpService;
import service.basicFunctions.project.ProTokenService;
import utils.BaseConstant;
import utils.HttpData;
import utils.enums.AppInfo;
import utils.ip.IPUtil;
import utils.model.HomeConfigConstant;



@Controller
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private ProTokenService proTokenService;
	@Autowired
	private HttpService httpService;
	
	public static  String MOFANG_SESSION_ID = "";
	public static  long MOFANG_SESSION_END_TIME = 0;
	public static  String MOFANG_USER_ID = "";
	public static  String MOFANG_COMPANY_ID = "";
	public static  String MOFANG_STORE_ID = "";

	
	
	public String getMyToken(){
		return getToken(BaseConstant.APP_ID_NOW);
	}
	
	public String getMofangSessionId(){
		try {
			Date date = new Date();
			long timestamp = date.getTime();
			JSONObject json = new JSONObject();
			if("".equals(MOFANG_SESSION_ID)){
				json = HttpData.mofang_login();
			}else if(!"".equals(MOFANG_SESSION_ID)&&timestamp>MOFANG_SESSION_END_TIME){
				json = HttpData.mofang_login();
			}else{
				return MOFANG_SESSION_ID;
			}
			JSONObject json1 = json.getJSONObject(BaseConstant.DATA);
			MOFANG_SESSION_ID = json1.getString(BaseConstant.SESSION_ID);
			JSONObject json2 = json1.getJSONObject(BaseConstant.USER);
			MOFANG_COMPANY_ID = json2.getString(BaseConstant.COMPANY_ID);
			MOFANG_STORE_ID = json2.getString(BaseConstant.STORE_ID);
			logger.warn("mofang session id:{}",json1);
			return MOFANG_SESSION_ID;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return null;
	}
	
	/**
	 * 获取token
	 * @param appId
	 * @return
	 */
	public String getToken(String appId){
		ProToken token = proTokenService.getByAppId(appId);
		if(token!=null){
			long timestamp_now = new Date().getTime();
			if(timestamp_now>token.getTimestamp()){
				logger.warn("{} token 已失效 {}",token.getTimestamp(),timestamp_now);
				token = getNewToken(appId);
			}
		}else{
			token = getNewToken(appId);
		}
		if(token!=null){
			return token.getToken();
		}
		return null;
	}
	
	/**
	 * 新建token
	 * @param appId
	 * @return
	 */
	public ProToken getNewToken(String appId){
		AppInfo appInfo = AppInfo.getByAppId(appId);
		if(appInfo!=null){
			try {
				String result = httpService.get(HttpData.getTokenUrl(appInfo));
				logger.error("BaseController.getNewToken[res:{}]",result);
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					JSONObject jsonObject = JSONObject.parseObject(result);
					ProToken token = JSON.parseObject(jsonObject.getString(BaseConstant.PARAMS),ProToken.class);
					if(token!=null){
						return proTokenService.createNew(appId,token);
					}
				}
			} catch (Exception e) {
				logger.error("BaseController.getNewToken[error:{}]",e);
			}
		}
		return null;
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
