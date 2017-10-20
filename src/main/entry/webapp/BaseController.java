package main.entry.webapp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import database.models.pro.ProWechatToken;
import service.basicFunctions.pro.ProWechatTokenService;
import utils.ip.IPUtil;
import utils.wechat.WechatData;
import utils.wechat.WechatJSSign;

@Controller
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private ProWechatTokenService proWechatTokenService;

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
			ProWechatToken proWechatToken = proWechatTokenService.getByTypeAndId(WechatData.APP_ID,1);
			Map<String, String> ret;
			ret = WechatJSSign.createSign(proWechatToken.getTokenValue(),url, WechatData.APP_ID, WechatData.APP_SECRET);
			request.setAttribute("appId", WechatData.APP_ID);
			request.setAttribute("timestamp", ret.get("timestamp").toString());
			request.setAttribute("nonceStr", ret.get("nonceStr").toString());
			request.setAttribute("signature", ret.get("signature").toString());
			request.setAttribute("desc","");
		} catch (Exception e) {
			logger.error("[error:{}]",e);
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
	 * 设置session
	 * @param request
	 * @param key
	 * @param value
	 */
	public void setSession(HttpServletRequest request,String key,Object value){
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}
	
}
