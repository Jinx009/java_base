package service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.StreamClosedHttpResponse;

public class AccessTokenService {
	
	private static final Logger log = LoggerFactory.getLogger(AccessTokenService.class);

	@SuppressWarnings({ "unchecked", "resource" })
	public static String getAccessToken(){
		try {
			HttpsUtil httpsUtil = new HttpsUtil();
			httpsUtil.initSSLConfigForTwoWay();

			Map<String, String> param = new HashMap<>();
			param.put("appId", Constant.APPID);
			param.put("secret", Constant.SECRET);

			StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(Constant.APP_AUTH, param);
			log.warn("msg:{},{}",responseLogin.getStatusLine(),responseLogin.getContent());

			Map<String, String> data = new HashMap<>();
			data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
			String accessToken = data.get("accessToken");
			log.warn("accessToken:",accessToken);
			return accessToken;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static String refreshToken(){
		try {
	        HttpsUtil httpsUtil = new HttpsUtil();
	        httpsUtil.initSSLConfigForTwoWay();
	        String refreshToken = getRefreshToken(httpsUtil); 

	        Map<String, Object> param_reg = new HashMap<>();
	        param_reg.put("appId", Constant.APPID);
	        param_reg.put("secret",  Constant.SECRET);
	        param_reg.put("refreshToken", refreshToken);

	        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
	        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(Constant.REFRESH_TOKEN, jsonRequest);

	        log.warn("msg:{},{}",bodyRefreshToken.getStatusLine(),bodyRefreshToken.getContent());
	        Map<String, String> data = new HashMap<>();
	        data = JsonUtil.jsonString2SimpleObj(bodyRefreshToken.getContent(), data.getClass());
	        return data.get("refreshToken");
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
    @SuppressWarnings("unchecked")
	public static String getRefreshToken(HttpsUtil httpsUtil) throws Exception {

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", Constant.APPID);
        paramLogin.put("secret", Constant.SECRET);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(Constant.APP_AUTH, paramLogin);

        log.warn("msg:{},{}",responseLogin.getStatusLine(),responseLogin.getContent());

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("refreshToken");
    }
	
}
