package utils;

import utils.enums.AppInfo;

/**
 * 常用静态参数
 * @author jinx
 */
public class BaseConstant {

	/**
	 * http部分
	 */
	public static final String HTTP_OK_CODE = "200";
	public static final String HTTP_ERROR_CODE = "500";
	public static final String HTTP_OK_MSG = "请求成功";
	public static final String HTTP_ERROR_MSG = "请求失败";
	public static final String RESP_CODE = "respCode";
	
	public static final String PARAMS = "params";
	public static final String APP_ID = "appId";
	public static final String MSG = "msg";
	
	public static final Integer AREA_ID = 30;
	public static final Integer LOCATION_ID = 28;
	public static final String APP_ID_NOW = AppInfo.XINJIANG.getAppId();
	public static final String DATA = "data";
	public static final String SESSION_ID = "sessionId";
	public static final String USER = "user";
	public static final String COMPANY_ID = "companyOrganId";
	public static final String STORE_ID = "storeOrganId";
	
	public static final String BASE_COMPANY_ID = "10071";
	public static final String BASE_STORE_ID = "10072";
	
}
