package utils;


import utils.enums.AppInfo;
import utils.pos.CallUtils;

public class HttpData {

	public static final String BASE_URL = "http://wx.zhanway.com/gtw";
	public static final String GET_TOKEN_URL = "/rest/token";
	public static final String GET_LOCATION_URL = "/rest/locations";
	public static final String GET_AREA_URL = "/rest/areas";
	
	private static final String LOCATION_STATUS_URL = "/rest/parking/locationStatus";
	private static final String LOCATION_RUSH_URL = "/rest/parking/locationRush";
	private static final String DEVICE_URL = "/rest/parking/device";
	private static final String CAR_URL = "/ge/carInOutLog";
	private static final String LOCATION_LIST_URL = "/rest/locations";
	private static final String LOCATION_UPDATE_URL = "/rest/location/update";
	private static final String AREA_LIST_URL = "/rest/areas";
	private static final String AREA_UPDATE_URL = "/rest/area/update";
	public static final String VIEW_URL = "/rest/parking/detail";
	public static final String JOB_ADD_URL = "/rest/job/save";
	public static final String JOB_FIND_URL = "/rest/job/find";
	public static final String INOUT_URL = "/rest/sensor/inOutLog";
	
	
	private static final String POS_SERVER_IP = "http://120.92.101.137:8080";
	private static final String baseOrganId = "200023";
	private static final String organId = "200023";
	private static final String  limit = "10";
	
	
	/**
	 * 获取设备信息
	 * @param token
	 * @return
	 */
	public static String deviceUrl(String token) {
		String url = BASE_URL+DEVICE_URL+"?token="+token;
		return url;
	}
	
	/**
	 * 获取pos机列表
	 * @param token
	 * @param mac
	 * @return
	 */
	public static String posUrl() {
		return POS_SERVER_IP+"/user-api/device?baseOrganId="+baseOrganId;
	}
	
	public static String appRush(String token, String dateStr) {
		String url = BASE_URL+LOCATION_RUSH_URL+"?locationId="+BaseConstant.LOCATION_ID+"&token="+token+"&date="+dateStr;
		return url;
	}
	public static String inOutUrl(String dateStr) {
		String url = BASE_URL+INOUT_URL+"?areaId="+BaseConstant.AREA_ID+"&dateStr="+dateStr;
		return url;
	}
	/**
	 * pos机登录账号列表
	 * @return
	 */
	public static String accountUrl() {
		return POS_SERVER_IP+"/user-api/user?organId="+organId+"&limit="+limit;
	}
	/**
	 * 曲线图location
	 * @param token
	 * @param op
	 * @param locationId
	 * @param dateStr
	 * @return
	 */
	public static String locationStatusUrl(String token, Integer op, String dateStr) {
		String url = BASE_URL+LOCATION_STATUS_URL+"?token="+token+"&op="+op+"&locationId="+BaseConstant.LOCATION_ID+"&date="+dateStr;
		return url;
	}
	/**
	 * 车辆统计
	 * @param token
	 * @param areaId
	 * @param dateStr
	 * @param type
	 * @return
	 */
	public static String carUrl(String token,String dateStr,Integer type) {
		String url = BASE_URL+CAR_URL+"?areaId="+BaseConstant.AREA_ID+"&dateStr="+dateStr+"&type="+type;
		return url;
	}
	/**
	 * 获取token链接
	 * @param appInfo
	 * @return
	 */
	public static String getTokenUrl(AppInfo appInfo) {
		String url = BASE_URL+GET_TOKEN_URL+"?appId="+appInfo.getAppId()+"&secret="+appInfo.getSecret();
		return url;
	}
	
	/**
	 * 获取location列表
	 * @param token
	 * @return
	 */
	public static String locationListeUrl(String token) {
		String url = BASE_URL+LOCATION_LIST_URL+"?token="+token;
		return url;
	}
	
	/**
	 * 修改location信息
	 * @param token
	 * @param id
	 * @param name
	 * @param desc
	 * @return
	 */
	public static String locationUpdateUrl(String token, Integer id, String name, String desc) {
		String url = BASE_URL+LOCATION_UPDATE_URL+"?token="+token+"&id="+id+"&name="+name+"&desc="+desc;
		return url;
	}
	
	/**
	 * 获取Area列表
	 * @param token
	 * @return
	 */
	public static String areaListeUrl(Integer id,String token) {
		String url = BASE_URL+AREA_LIST_URL+"?id="+id+"&token="+token;
		return url;
	}
	
	/**
	 * 修改Area信息
	 * @param token
	 * @param id
	 * @param name
	 * @param desc
	 * @return
	 */
	public static String areaUpdateUrl(String token, Integer id, String name, String desc) {
		String url = BASE_URL+AREA_UPDATE_URL+"?token="+token+"&id="+id+"&name="+name+"&desc="+desc;
		return url;
	}
	
	
	
	
	
	
	
	


	
	




	/**
	 * pos机账单信息
	 * @return
	 */
	public static String order() {
		try {
			return CallUtils.getOrders3();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 车位信息
	 * @return
	 */
	public static String place() {
		try {
			return CallUtils.getPlace();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String insert(String mac, String code, String place, String remark) {
		try {
			return CallUtils.insert(mac,code,place,remark);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	

	
	

	
}
