package service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;


public class NoticeService {

	private static final Logger log = LoggerFactory.getLogger(NoticeService.class);
	
	@SuppressWarnings("resource")
	public static String notice(){
		try {
	        HttpsUtil httpsUtil = new HttpsUtil();
	        httpsUtil.initSSLConfigForTwoWay();
	        String accessToken = AccessTokenService.getAccessToken();

	        String appId = Constant.APPID;
	        String urlSubscribe = Constant.SUBSCRIBE_NOTIFYCATION;
	        String callbackurl_deviceDataChanged = Constant.NOTICE_URL;
	        String notifyType_deviceDataChanged = Constant.DEVICE_DATA_CHANGED;

	        Map<String, Object> paramSubscribe_deviceDataChanged = new HashMap<>();
	        paramSubscribe_deviceDataChanged.put("notifyType", notifyType_deviceDataChanged);
	        paramSubscribe_deviceDataChanged.put("callbackurl", callbackurl_deviceDataChanged);

	        String jsonRequest_deviceInfoChanged = JsonUtil.jsonObj2Sting(paramSubscribe_deviceDataChanged);
	        
	        Map<String, String> header_deviceDataChanged = new HashMap<>();
	        header_deviceDataChanged.put(Constant.HEADER_APP_KEY, appId);
	        header_deviceDataChanged.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

	        HttpResponse httpResponse_deviceDataChanged = httpsUtil.doPostJson(urlSubscribe, header_deviceDataChanged, jsonRequest_deviceInfoChanged);

	        String bodySubscribe_deviceDataChanged = httpsUtil.getHttpResponseBody(httpResponse_deviceDataChanged);
	        log.warn("res:{},{}",bodySubscribe_deviceDataChanged,httpResponse_deviceDataChanged);
	        return "success";
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
}
