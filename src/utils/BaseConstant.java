package utils;


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
	
	public static final String PARAMS = "params";
	public static final String APP_ID = "appId";
	public static final int PAGE_SIZE = 20;
	

	public static final String REGISTER_NOTICE_URL = "http://223.167.110.4:8000/m2m/applications/registration";
	public static final String SEND_URL_ONE = "http://223.167.110.4:8000/m2m/endpoints/";
	public static final String SEND_URL_TWO = "/downlinkMsg/0/data";
	
	public static String getSendUrl(String imei){
		return SEND_URL_ONE+imei+SEND_URL_TWO;
	}
	
}
