package utils.wechat;


import utils.HttpUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class WechatUtil {

	private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);
	
	/**
	 * 获取accessToken
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getAccessToken(String appId, String appSecret)throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
				appId+"&secret="+appSecret;
		String result = HttpUtil.get(url);
		String access = null;
		JSONObject jsonObject = JSON.parseObject(result);
		logger.warn("[WechatUtil.getAccessToken:{}]",result);
		access = jsonObject.getString("access_token");
		return access;
	}

	
	/**
	 * 获取jsApiTicket
	 * @param appId
	 * @param appsecret
	 * @return
	 */
	public static String getJSApiTicket(String appId, String appsecret)throws Exception {
		String currentJSApiTicket = null;
		String accessToken = getAccessToken(appId, appsecret);
		if(accessToken!=null){
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
			String result = HttpUtil.get(url);
			JSONObject jsonObject = JSON.parseObject(result);
			currentJSApiTicket = jsonObject.getString("ticket");
		}
		return currentJSApiTicket;
	}

	
}
