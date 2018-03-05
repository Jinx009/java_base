package utils;


import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import utils.enums.AppInfo;

public class HttpData {
	

	public static final String BASE_URL = "http://wx.zhanway.com/gtw";
	public static final String BASE_URL_TEST = "http://wx.zhanway.com/gtw";
	public static final String GET_TOKEN_URL = "/rest/token";
	public static final String GET_LOCATION_URL = "/rest/locations";
	public static final String GET_AREA_URL = "/rest/areas";
	
	private static final String LOCATION_STATUS_URL = "/rest/parking/locationStatus";
	private static final String LOCATION_RUSH_URL = "/rest/parking/locationRush";
	private static final String DEVICE_URL = "/rest/parking/device";
	private static final String CAR_URL = "/ge/carInOutLog";
	private static final String LOCATION_LIST_URL = "/rest/locations";
	private static final String LOCATION_UPDATE_URL = "/rest/location/update";
	private static final String AREA_LIST_URL = "/rest/area/list";
	private static final String AREA_UPDATE_URL = "/rest/area/update";
	public static final String VIEW_URL = "/rest/parking/detail";
	public static final String JOB_ADD_URL = "/rest/job/save";
	public static final String JOB_FIND_URL = "/rest/job/find";
	public static final String INOUT_URL = "/rest/sensor/inOutLog";
	private static final String UPDATE_SENSOR_DESC = "/rest/sensor/updateDesc";
	private static final String SENSOR_ERROR_FLOW = "/rest/sensor/error";
	
	
	
	private static final String MOFANG_BASE_URL = "http://120.92.101.137:8081";
	private static final String LOGIN = "/user_auth/login";
	private static final String MOFANG_BASE_URL_1 = "http://120.92.101.137:8083";
	
	
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
	 * 错误日志
	 * @param p
	 * @param areaId
	 * @return
	 */
	public static String errorFlow(Integer p,Integer areaId){
		if(p==null){
			p = 1;
		}
		if(areaId == null){
			areaId = BaseConstant.AREA_ID;
		}
		return BASE_URL_TEST+SENSOR_ERROR_FLOW+"?areaId="+areaId+"&p="+p;
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
	
	/**
	 * 曲线图表
	 * @param token
	 * @param dateStr
	 * @return
	 */
	public static String appRush(String token, String dateStr) {
		String url = BASE_URL+LOCATION_RUSH_URL+"?locationId="+BaseConstant.LOCATION_ID+"&token="+token+"&date="+dateStr;
		return url;
	}
	
	/**
	 * 车进车出日志
	 * @param dateStr
	 * @return
	 */
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
	public static String deviceUpdateUrl(String token, String mac, String desc) {
		String url = BASE_URL+UPDATE_SENSOR_DESC+"?token="+token+"&mac="+mac+"&desc="+desc;
		return url;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String mofang_login_url(){
		return MOFANG_BASE_URL+LOGIN;
	}
	
	public static String mofang_get_user(String companyOrganId){
		return MOFANG_BASE_URL+"/user?companyOrganId="+companyOrganId;
	}
	
	public static String mofang_add_user_post(){
		return MOFANG_BASE_URL+"/user";
	}
	
	public static String mofang_update_organ_post(){
		return MOFANG_BASE_URL+"/organ/update";
	}
	
	public static String mofang_update_park_url(){
		return MOFANG_BASE_URL_1+"/park_place";
	}
	
	public static String mofang_update_park_update_url(){
		return MOFANG_BASE_URL_1+"/park_place/update";
	}
	
	public static String mofang_add_rule_url(){
		return MOFANG_BASE_URL_1+"/charging_rule";
	}
	
	public static String mofang_order_statistics_url(){
		return MOFANG_BASE_URL_1+"/product/statistics";
	}
	
	private static String mofang_update_user_url() {
		return MOFANG_BASE_URL+"/user/update";
	}
	
	private static String mofang_sign_log_url() {
		return MOFANG_BASE_URL+"/operation_log";
	}
	
	public static String mofang_add_organ(){
		return MOFANG_BASE_URL+"/organ";
	}
	
	private static String mofang_update_rule_url() {
		return MOFANG_BASE_URL_1+"/charging_rule/update";
	}
	private static String mofang_get_device_url() {
		return MOFANG_BASE_URL+"/device";
	}
	
	
	/**
	 * pos机列表
	 * @param mofangSessionId
	 * @param companyOrganId
	 * @return
	 */
	public static JSONObject mofang_get_device(String mofangSessionId, String companyOrganId) {
		String params = "?&companyOrganId="+companyOrganId;
		return JSONObject.parseObject(HttpUtils.getMofang(mofangSessionId, mofang_get_device_url()+params));
	}
	
	/**
	 * 收费规则列表
	 * @param mofangSessionId
	 * @param companyOrganId
	 * @return
	 */
	public static JSONObject mofang_get_rule(String mofangSessionId, String companyOrganId) {
		String params = "?&companyOrganId="+companyOrganId;
		return JSONObject.parseObject(HttpUtils.getMofang(mofangSessionId, mofang_add_rule_url()+params));
	}
	
	/**
	 * 考勤记录
	 * @param mofangSessionId
	 * @param companyOrganId
	 * @param storeOrganId
	 * @param userId
	 * @param page
	 * @return
	 */
	public static JSONObject mofang_get_sign(String mofangSessionId, String companyOrganId, String storeOrganId,
			String userId, Integer page) {
		String params = "?&storeOrganId="+storeOrganId+"&pageNum="+page+"&pageSize=25";
		if(StringUtil.isNotBlank(userId)){
			params+= "&userId="+userId;
		}
		return JSONObject.parseObject(HttpUtils.getMofang(mofangSessionId, mofang_sign_log_url()+params));
	}
	
	/**
	 * 订单分析
	 * @param sessionId
	 * @param beginTime
	 * @param endTime
	 * @param status
	 * @param storeOrganId
	 * @return
	 */
	public static JSONObject getOrderStatistics(String sessionId,String beginTime,String endTime,String status, String storeOrganId){
		String params = "?beginTime="+beginTime+"&endTime="+endTime+"&status="+status+"&storeOrganId="+storeOrganId;
		return  JSONObject.parseObject(HttpUtils.getMofang(sessionId, mofang_order_statistics_url()+params));
	}
	
	/**
	 * 增加收费规则
	 * @param mofangSessionId
	 * @param companyOrganId
	 * @param storeOrganId
	 * @param period
	 * @param amountOfMoney
	 * @param amountOfMoneyForNotEnough
	 * @return
	 */
	public static Object mofang_add_rule(String mofangSessionId, String companyOrganId, String storeOrganId,
			String period, String amountOfMoney, String amountOfMoneyForNotEnough) {
		Map<String, String> data = new HashMap<String,String>();
		data.put("companyOrganId", companyOrganId);
		data.put("storeOrganId", storeOrganId);
		data.put("period", period);
		data.put("amountOfMoney",amountOfMoney);
		data.put("amountOfMoneyForNotEnough",amountOfMoneyForNotEnough);
		return  JSONObject.parseObject(HttpUtils.postMofangJson(mofangSessionId,mofang_add_rule_url(),JSON.toJSONString(data)));
	}
	
	/**
	 * 更新收费规则
	 * @param mofangSessionId
	 * @param period
	 * @param amountOfMoney
	 * @param amountOfMoneyForNotEnough
	 * @param ruleId
	 * @return
	 */
	public static Object mofang_update_rule(String mofangSessionId, String period, String amountOfMoney,
			String amountOfMoneyForNotEnough,String ruleId) {
		Map<String, String> data = new HashMap<String,String>();
		data.put("period", period);
		data.put("ruleId", ruleId);
		data.put("amountOfMoney",amountOfMoney);
		data.put("amountOfMoneyForNotEnough",amountOfMoneyForNotEnough);
		return  JSONObject.parseObject(HttpUtils.postMofangJson(mofangSessionId,mofang_update_rule_url(),JSON.toJSONString(data)));
	}
	

	/**
	 * 车位信息
	 * @param sessionId
	 * @param mac
	 * @return
	 */
	public static JSONObject getPark(String sessionId,String mac){
		String params = "?companyOrganId="+BaseConstant.BASE_COMPANY_ID+"&storeOrganId="+BaseConstant.BASE_STORE_ID+"&magneticStripeId="+mac;
		return  JSONObject.parseObject(HttpUtils.getMofang(sessionId, mofang_update_park_url()+params));
	}
	
	/**
	 * 增加车位
	 * @param sessionId
	 * @param mac
	 * @param desc
	 * @return
	 */
	public static JSONObject addPark(String sessionId,String mac,String desc){
		Map<String, String> data = new HashMap<String,String>();
		data.put("code", desc);
		data.put("magneticStripeId", mac);
		data.put("storeOrganId",BaseConstant.BASE_STORE_ID);
		data.put("companyOrganId", BaseConstant.BASE_COMPANY_ID);
		data.put("status", "EMPTY");
		return  JSONObject.parseObject(HttpUtils.postMofangJson(sessionId,mofang_update_park_url(),JSON.toJSONString(data)));
	}
	
	/**
	 * 更新车位
	 * @param sessionId
	 * @param mac
	 * @param desc
	 * @param parkId
	 * @return
	 */
	public static JSONObject updatePark(String sessionId,String mac,String desc,String parkId){
		Map<String, String> data = new HashMap<String,String>();
		data.put("code", desc);
		data.put("magneticStripeId", mac);
		data.put("parkPlaceId", parkId);
		return  JSONObject.parseObject(HttpUtils.postMofangJson(sessionId,mofang_update_park_update_url(),JSON.toJSONString(data)));
	}
	

	/**
	 * 更新组织名称
	 * @param sessionId
	 * @param type
	 * @param name
	 * @param status
	 * @return
	 */
	public static JSONObject mergeOrgan(String sessionId,Integer type,String name,String status){
		Map<String, String> data = new HashMap<String,String>();
		data.put("organId", BaseConstant.BASE_STORE_ID);
		if(1==type){
			data.put("organId",BaseConstant.BASE_COMPANY_ID);
		}
		data.put("status", status);
		data.put("name", name);
		return  JSONObject.parseObject(HttpUtils.postMofangJson(sessionId,mofang_update_organ_post(),JSON.toJSONString(data)));
	}
	
	/**
	 * 添加POS机用户
	 * @param sessionId
	 * @param name
	 * @param storeOrganId
	 * @param password
	 * @param mobilePhone
	 * @param birthday
	 * @param sex
	 * @param email
	 * @return
	 */
	public static JSONObject mofang_add_user(String sessionId,String name,String storeOrganId,String password,String mobilePhone,String birthday,String sex, String email){
		Map<String, String> data = new HashMap<String,String>();
		data.put("storeOrganId", storeOrganId);
		data.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
		data.put("name",name);
		data.put("password", password);
		data.put("birthday", birthday);
		data.put("email", email);
		data.put("sex", sex);
		data.put("mobile", mobilePhone);
		return  JSONObject.parseObject(HttpUtils.postMofangJson(sessionId,mofang_add_user_post(),JSON.toJSONString(data)));
	}
	
	/**
	 * 获取订单列表
	 * @param companyOrganId
	 * @param page
	 * @return
	 */
	public static String mofang_get_order(String companyOrganId, Integer page,String eventId){
		StringBuffer params = new StringBuffer("a=a");
		if(StringUtil.isNotBlank(eventId)){
			params.append("&eventId="+eventId);
		}
		if(StringUtil.isNotBlank(companyOrganId)){
			params.append("&companyOrganId="+companyOrganId);
		}else{
			params.append("&companyOrganId="+BaseConstant.BASE_COMPANY_ID);
		}
		params.append("&pageNum="+page);
		params.append("&pageSize=25");
		return MOFANG_BASE_URL_1+"/product?"+params.toString();
	}
	
	/**
	 * 获取组织列表
	 * @param status
	 * @param type
	 * @param companyOrganId
	 * @param name
	 * @return
	 */
	public static String mofang_get_organ(String status,String type, String companyOrganId, String name){
		StringBuffer params = new StringBuffer("a=a");
		if(StringUtil.isNotBlank(type)){
			params.append("&type="+type);
		}
		if(StringUtil.isNotBlank(companyOrganId)){
			params.append("&companyOrganId="+companyOrganId);
		}
		if(StringUtil.isNotBlank(name)){
			params.append("&name="+name);
		}
		if(StringUtil.isNotBlank(status)){
			params.append("&status="+status);
		}
		return MOFANG_BASE_URL+"/organ?"+params.toString();
	}
	
	/**
	 * 更新POS机操作员
	 * @param mofangSessionId
	 * @param name
	 * @param storeOrganId
	 * @param password
	 * @param mobilePhone
	 * @param birthday
	 * @param sex
	 * @param email
	 * @param userId
	 * @param status
	 * @return
	 */
	public static JSONObject mofang_update_user(String mofangSessionId, String name, String storeOrganId, String password,
			String mobilePhone, String birthday, String sex, String email,String userId, String status) {
		Map<String, String> data = new HashMap<String,String>();
		data.put("storeOrganId", storeOrganId);
		if(StringUtil.isNotBlank(password)){
			data.put("password",password);
		}
		data.put("name",name);
		data.put("mobile", mobilePhone);
		data.put("birthday", birthday);
		data.put("sex", sex);
		data.put("email", email);
		data.put("status", status);
		data.put("userId", userId);
		return JSONObject.parseObject(HttpUtils.postMofangJson(mofangSessionId,mofang_update_user_url(),JSON.toJSONString(data)));
	}
	
	/**
	 * 添加公司组织
	 * @param sessionId
	 * @param companyOrganId
	 * @param name
	 * @return
	 */
	public static JSONObject mofang_add_company_organ(String sessionId,String companyOrganId,String name){
		Map<String, String> data = new HashMap<String,String>();
		data.put("companyOrganId", companyOrganId);
		data.put("name",name);
		data.put("type", "COMPANY");
		return  JSONObject.parseObject(HttpUtils.postMofangJson(sessionId,mofang_add_organ(),JSON.toJSONString(data)));
	}
	
	/**
	 * 添加街道组织
	 * @param sessionId
	 * @param companyOrganId
	 * @param name
	 * @return
	 */
	public static Object mofang_add_store_organ(String sessionId,String companyOrganId, String name) {
		Map<String, String> data = new HashMap<String,String>();
		data.put("companyOrganId", companyOrganId);
		data.put("name",name);
		data.put("type", "STORE");
		return  JSONObject.parseObject(HttpUtils.postMofangJson(sessionId,mofang_add_organ(),JSON.toJSONString(data)));
	}
	
	/**
	 * 登录获取sessionId
	 * @return
	 */
	public static JSONObject mofang_login(){
		Map<String, String> data = new HashMap<String,String>();
		data.put("mobile", "13800138000");
		data.put("password", "123456");
		return JSONObject.parseObject(HttpUtils.postJson(mofang_login_url(),JSON.toJSONString(data)));
	}
	
	
	

	public static void main(String[] args) {
		mofang_add_store_organ("USI_5e4c2a17-aeef-42d6-a19b-915368df0f41", "10254", "潮州智信");
	}

	
	









	

	
	

	
	

	
}
