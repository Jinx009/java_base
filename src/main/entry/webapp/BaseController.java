package main.entry.webapp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import database.models.WebTokenFactory;
import service.basicFunctions.WebTokenFactoryService;
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
	
}
