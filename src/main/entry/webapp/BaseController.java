package main.entry.webapp;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import database.models.project.ProToken;
import service.basicFunctions.HttpService;
import service.basicFunctions.project.ProTokenService;
import utils.BaseConstant;
import utils.HttpData;
import utils.enums.AppInfo;
import utils.ip.IPUtil;



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

	public Integer getInt(Map<String, Object> data,String key){
		return Integer.valueOf(String.valueOf(data.get(key)));
	}
	
	public String getString(Map<String, Object> data,String key){
		return String.valueOf(data.get(key));
	}
	
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
				logger.warn("mofang session id:{}_",MOFANG_SESSION_ID);
				return MOFANG_SESSION_ID;
			}
			JSONObject json1 = json.getJSONObject(BaseConstant.DATA);
			MOFANG_SESSION_ID = json1.getString(BaseConstant.SESSION_ID);
			JSONObject json2 = json1.getJSONObject(BaseConstant.USER);
			MOFANG_COMPANY_ID = json2.getString(BaseConstant.COMPANY_ID);
			MOFANG_STORE_ID = json2.getString(BaseConstant.STORE_ID);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE,20);
			date = calendar.getTime();
			MOFANG_SESSION_END_TIME = date.getTime();
			logger.warn("mofang session id:{}",json1);
			return MOFANG_SESSION_ID;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return null;
	}
	
	public boolean checkToken(String token){
		return proTokenService.checkToken(token);
	}
	
	public ProToken getByToken(String token){
		return proTokenService.getByToken(token);
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
	
}
