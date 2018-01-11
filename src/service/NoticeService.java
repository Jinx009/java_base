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
	        String callbackurl_deviceInfoChanged = Constant.NOTICE_URL;
	        String notifyType_deviceDataChanged = Constant.DEVICE_DATA_CHANGED;

	        Map<String, Object> paramSubscribe_deviceInfoChanged = new HashMap<>();
	        paramSubscribe_deviceInfoChanged.put("notifyType", notifyType_deviceDataChanged);
	        paramSubscribe_deviceInfoChanged.put("callbackurl", callbackurl_deviceInfoChanged);

	        String jsonRequest_deviceInfoChanged = JsonUtil.jsonObj2Sting(paramSubscribe_deviceInfoChanged);

	        Map<String, String> header_deviceInfoChanged = new HashMap<>();
	        header_deviceInfoChanged.put(Constant.HEADER_APP_KEY, appId);
	        header_deviceInfoChanged.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

	        HttpResponse httpResponse_deviceDataChanged = httpsUtil.doPostJson(urlSubscribe, header_deviceInfoChanged, jsonRequest_deviceInfoChanged);

	        String bodySubscribe_deviceDataChanged = httpsUtil.getHttpResponseBody(httpResponse_deviceDataChanged);
	        log.warn("res:{}",bodySubscribe_deviceDataChanged);
	        return bodySubscribe_deviceDataChanged;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
}
