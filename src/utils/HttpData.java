package utils;


import utils.enums.AppInfo;
import utils.pos.CallUtils;

public class HttpData {
	
	public static final String FACE_BASE_URL = "http://106.14.94.245:8090/gtw";
	public static final String FACE_FACTORY_ADD_USER_URL = "/rest/face/addUser";
	public static final String FACE_FACTORY_ADD_IMAGE_URL = "/rest/face/uploadImg";
	public static final String FACE_FACTORY_USERS_URL = "/rest/face/users";
	public static final String COMPARE_USERS_URL = "/rest/face/compares";
	
	
	public static final String MOFANG_AK = "430834bc0f044a69b3f5127c577035ea";
	public static final String MOFANG_SK = "6GlpuxgsVbMabMTX";
	public static final String MOFANG_URL = "http://api.pos.bizcube.com.cn";
	

	public static final String BASE_URL = "http://wx.zhanway.com/gtw";
	public static final String GET_TOKEN_URL = "/rest/token";
	public static final String GET_LOCATION_URL = "/rest/locations";
	public static final String GET_AREA_URL = "/rest/areas";
	private static final String GET_LID_URL = "/rest/intersection/lids";
	private static final String GET_POS_URL = "/rest/intersection/poss";
	private static final String GET_CAR_NUMS_URL = "/rest/intersection/nums";
	private static final String GET_CAR_NUMS_VIEW_URL = "/rest/intersection/numsView";
	
	private static final String LOCATION_STATUS_URL = "/rest/parking/locationStatus";
	private static final String LOCATION_RUSH_URL = "/rest/parking/locationRush";
	private static final String DEVICE_URL = "/rest/parking/device";
	private static final String CAR_URL = "/ge/carInOutLog";
	private static final String BIKE_URL = "/rest/parking/bikes";
	public static final String VIEW_URL = "/rest/parking/detail";
	public static final String JOB_ADD_URL = "/rest/job/save";
	public static final String JOB_FIND_URL = "/rest/job/find";
	public static final String INOUT_URL = "/rest/sensor/inOutLog";
	private static final String MONEY_URL = "/rest/parking/money";
	
	public static String jobFind(Integer id){
		String url = BASE_URL+JOB_FIND_URL+"?id="+id;
		return url;
	}
	
	public static String jobAdd(){
		String url = BASE_URL+JOB_ADD_URL;
		return url;
	}
	
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
	
	public static String jinganRush(String token, String dateStr) {
		String url = BASE_URL+LOCATION_RUSH_URL+"?locationId=16&token="+token+"&date="+dateStr;
		return url;
	}

	public static String rush(String token, String dateStr) {
		String url = BASE_URL+LOCATION_RUSH_URL+"?locationId=18&token="+token+"&date="+dateStr;
		return url;
	}
	
	public static String puruanRush(String token, String dateStr) {
		String url = BASE_URL+LOCATION_RUSH_URL+"?locationId=22&token="+token+"&date="+dateStr;
		return url;
	}
	
	public static String modouRush(String token, String dateStr) {
		String url = BASE_URL+LOCATION_RUSH_URL+"?locationId=21&token="+token+"&date="+dateStr;
		return url;
	}

	public static String inOutUrl(String dateStr, Integer areaId) {
		String url = BASE_URL+INOUT_URL+"?areaId="+areaId+"&dateStr="+dateStr;
		return url;
	}

	public static String moneyUrl(String dateStr, Integer areaId,String token, Integer type) {
		String url = BASE_URL+MONEY_URL+"?areaId="+areaId+"&dateStr="+dateStr+"&token="+token+"&type="+type;
		return url;
	}

	public static String getFaceUpLoadUrl(String data) {
		return FACE_BASE_URL+FACE_FACTORY_ADD_IMAGE_URL+"?base64_content="+data;
	}

	public static String getCreateFaceFactoryUrl(String imagePath, String name, String address, String mobilePhone,String uid){
		return FACE_BASE_URL + FACE_FACTORY_ADD_USER_URL+"?imagePath="+imagePath+"&name="+name+"address="+address+"&mobilePhone="+mobilePhone+"&uid="+uid;
	}
	
	public static String getFaceFactoryUsersUrl(Integer p){
		return FACE_BASE_URL + FACE_FACTORY_USERS_URL+"?p="+p;
	}

	public static String getCompareUsersUrl(Integer p) {
		return FACE_BASE_URL + COMPARE_USERS_URL+"?p="+p;
	}

	
}
