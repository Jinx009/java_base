package utils;

import java.util.HashMap;
import java.util.Map;

import utils.enums.AppInfo;
import utils.pos.KeyUtils;

public class HttpData {

	public static final String BASE_URL = "http://wx.zhanway.com/gtw";
	public static final String GET_TOKEN_URL = "/rest/token";
	public static final String GET_LOCATION_URL = "/rest/locations";
	public static final String GET_AREA_URL = "/rest/areas";
	private static final String GET_LID_URL = "/rest/intersection/lids";
	private static final String GET_POS_URL = "/rest/intersection/poss";
	private static final String GET_CAR_NUMS_URL = "/rest/intersection/nums";
	private static final String GET_CAR_NUMS_VIEW_URL = "/rest/intersection/numsView";
	
	private static final String LOCATION_STATUS_URL = "/rest/parking/locationStatus";
	private static final String DEVICE_URL = "/rest/parking/device";
	private static final String CAR_URL = "/ge/carInOutLog";
	private static final String BIKE_URL = "/rest/parking/bikes";
	public static final String VIEW_URL = "/rest/parking/detail";
	
	/**
	 * 实况图
	 * @param token
	 * @param routerMac
	 * @return
	 */
	public static String detail(String token, Integer areaId) {
		String url = BASE_URL+VIEW_URL+"?token="+token+"&areaId="+areaId;
		return url;
	}
	
	/**
	 * 自行车
	 * @param token
	 * @param routerMac
	 * @return
	 */
	public static String bikeUrl(String token, String routerMac) {
		String url = BASE_URL+BIKE_URL+"?token="+token+"&mac="+routerMac;
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
	public static String carUrl(String token, Integer areaId, String dateStr,Integer type) {
		String url = BASE_URL+CAR_URL+"?areaId="+areaId+"&dateStr="+dateStr+"&type="+type;
		return url;
	}
	
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
	 * 曲线图location
	 * @param token
	 * @param op
	 * @param locationId
	 * @param dateStr
	 * @return
	 */
	public static String locationStatusUrl(String token, Integer op, Integer locationId, String dateStr) {
		String url = BASE_URL+LOCATION_STATUS_URL+"?token="+token+"&op="+op+"&locationId="+locationId+"&date="+dateStr;
		return url;
	}
	
	/**
	 * 获取location
	 * @param appInfo
	 * @return
	 */
	public static String getLocationUrl(String token){
		String url = BASE_URL+GET_LOCATION_URL+"?token="+token;
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
	 * 获取area
	 * @param id
	 * @return
	 */
	public static String getAreaUrl(Integer id,String token) {
		String url = BASE_URL+GET_AREA_URL+"?id="+id+"&token="+token;
		return url;
	}

	/**
	 * 获取车道
	 * @param id
	 * @return
	 */
	public static String getLidUrl(String token,Integer area) {
		String url = BASE_URL+GET_LID_URL+"?area="+area+"&token="+token;
		return url;
	}

	/**
	 * 获取方向
	 * @param token
	 * @param area
	 * @return
	 */
	public static String getPosUrl(String token, Integer area) {
		String url = BASE_URL+GET_POS_URL+"?area="+area+"&token="+token;
		return url;
	}
	
	/**
	 * 首页绘图数据
	 * @param token
	 * @param location
	 * @param area
	 * @param lid
	 * @param dateStr
	 * @param type
	 * @return
	 */
	public static String getCarNumUrl(String token,Integer location,Integer area,Integer lid,String dateStr,Integer type) {
		String url = BASE_URL+GET_CAR_NUMS_URL+"?area="+area+"&token="+token+"&location="+location+"&lid="+lid+"&type="+type+"&dateStr="+dateStr;
		return url;
	}
	
	/***
	 * 实景图绘制
	 * @param token
	 * @param location
	 * @param area
	 * @param dateStr
	 * @param type
	 * @return
	 */
	public static String getCarNumViewsUrl(String token,Integer location,Integer area,String dateStr,Integer type) {
		String url = BASE_URL+GET_CAR_NUMS_VIEW_URL+"?area="+area+"&token="+token+"&location="+location+"&type="+type+"&dateStr="+dateStr;
		return url;
	}

	
	
	/**
	 * 获取pos机列表
	 * @param token
	 * @param mac
	 * @return
	 */
	public static String posUrl() {
		return "http://120.92.101.137:8080/user-api/device?baseOrganId=200023";
	}

	/**
	 * pos机登录账号列表
	 * @return
	 */
	public static String accountUrl() {
		return "http://120.92.101.137:8080/user-api/user?organId=200023&limit=10";
	}

	/**
	 * pos机账单信息
	 * @return
	 */
	public static String order() {
//		{
//		    "userId":xxx,
//		    "baseOrganId":xxx,
//		    "topOrganId":xxx,
//		    "limit":xxx,
//		    "start":xxx
//		}
		Map<String,String> data = new HashMap<String,String>();
		data.put("userId", "100092");
		data.put("baseOrganId", "200023");
		data.put("topOrganId", "200023");
		data.put("limit", "100");
		data.put("start", "1");
//		return "http://120.92.101.137:8080/trade-api/order?baseOrganId=200023&topOrganId=200023&&limit=10&userId=100092";
		try {
			return KeyUtils.sendNotice(data, "http://120.92.101.137:8080/trade-api/order", "/trade-api/order");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
