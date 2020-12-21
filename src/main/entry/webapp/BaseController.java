package main.entry.webapp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import database.models.home.HomeUser;
import utils.Constant;
import utils.HttpsUtil;
import utils.IPUtil;
import utils.JsonUtil;
import utils.StreamClosedHttpResponse;
import utils.StringUtil;
import utils.model.BaseConstant;

@Controller
public class BaseController {

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);

   @SuppressWarnings("unchecked")
	public static String login(HttpsUtil httpsUtil) throws Exception {
        String appId = Constant.APPID;
        String secret = Constant.SECRET;
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);
        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);
        log.warn("------access-token:------{}",responseLogin.getContent());

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("accessToken");
    }
		
	
	/**
	 * 获取客户端ip
	 * 
	 * @param request
	 * @return
	 */
	public String getClientIp(HttpServletRequest request) {
		return IPUtil.getRemortIP(request);
	}

	public HomeUser getSessionHomeUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		HomeUser homeUser = (HomeUser) session.getAttribute(BaseConstant.HOME_USER);
		return homeUser;
	}

	public void setSessionHomeUser(HomeUser homeUser, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(BaseConstant.HOME_USER, homeUser);
		session.setAttribute(BaseConstant.HOME_USER_REAL_NAME, homeUser.getRealName());
	}
	
	public void setSessionNull(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(BaseConstant.HOME_USER, null);
		session.setAttribute(BaseConstant.HOME_USER_REAL_NAME, null);
	}

	public byte[] getRequestPostBytes(HttpServletRequest request) {
		try {
			int contentLength = request.getContentLength();
			if (contentLength < 0) {
				return null;
			}
			byte buffer[] = new byte[contentLength];
			for (int i = 0; i < contentLength;) {
				int readlen = request.getInputStream().read(buffer, i, contentLength - i);
				if (readlen == -1) {
					break;
				}
				i += readlen;
			}
			return buffer;
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return null;
	}

	public String getRequestPostStr(HttpServletRequest request) {
		try {
			byte buffer[] = getRequestPostBytes(request);
			String charEncoding = request.getCharacterEncoding();
			if (charEncoding == null) {
				charEncoding = "UTF-8";
			}
			String result =  new String(buffer, charEncoding);
			if(StringUtil.isNotBlank(result)){
				return result;
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return "{\"app\":\"app\"}";
	}

}
