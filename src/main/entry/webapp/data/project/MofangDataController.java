package main.entry.webapp.data.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.project.model.ProOrderStatisticsModel;
import main.entry.webapp.BaseController;
import utils.BaseConstant;
import utils.HttpData;
import utils.HttpUtils;
import utils.MofangSignUtils;
import utils.Resp;
import utils.UUIDUtils;

@RequestMapping(value = "/home/d")
@Controller
public class MofangDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(MofangDataController.class);
	
	
	
	/**
	 * 获取组织
	 * @param status
	 * @param type
	 * @param companyOrganId
	 * @param name
	 * @return
	 */
	@RequestMapping(path = "/mofang/organ")
	@ResponseBody
	public Resp<?> getOrgan(){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("path", "/core/organ");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.getMofangV2("/core/organ",map)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * 获取POS设备列表
	 * @param companyId
	 * @param page
	 * @return
	 */
	@RequestMapping(path = "/mofang/pos")
	@ResponseBody
	public Resp<?> pos(String companyId){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("path", "/core/device");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			map.put("companyOrganId","10001");
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.getMofangV2("/core/device?companyOrganId=10001",map)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * POS机操作员列表
	 * @param companyOrganId
	 * @return
	 */
	@RequestMapping(path = "/mofang/user")
	@ResponseBody
	public Resp<?> getUser(String companyOrganId){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("path", "/core/user");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			map.put("companyOrganId","10001");
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.getMofangV2("/core/user?companyOrganId=10001",map)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * 更新POS机操作员
	 * @param storeOrganId
	 * @param mobile
	 * @param sex
	 * @param email
	 * @param status
	 * @param userId
	 * @param name
	 * @param birthday
	 * @param password
	 * @return
	 */
	@RequestMapping(path = "/mofang/update_user")
	@ResponseBody
	public Resp<?> updateUser(String storeOrganId,String mobile,String sex,String email,String status,String userId,String name,String birthday,String password){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> data = new HashMap<String,String>();
			data.put("storeOrganId", BaseConstant.STORE_ID);
			if(StringUtil.isNotBlank(password)){
				data.put("password",password);
			}
			data.put("name",name);
			data.put("mobile", mobile);
			data.put("birthday", birthday);
			data.put("sex", sex);
			data.put("email", email);
			data.put("status", status);
			data.put("userId", userId);
			String jsonStr = JSONObject.toJSONString(data);
			String uuid = UUIDUtils.random();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data.put("path", "/core/user/update");
			data.put("X-POS-REQUEST-ID",uuid);
			data.put("X-POS-REQUEST-TIME", sdf.format(date));
			data.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(data));
			data.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.postJsonMofangV2("/core/user/update",data,jsonStr)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	

	/**
	 * 增加POS机操作员
	 * @param name
	 * @param storeOrganId
	 * @param password
	 * @param mobilePhone
	 * @param birthday
	 * @param sex
	 * @param email
	 * @return
	 */
	@RequestMapping(path = "/mofang/add_user")
	@ResponseBody
	public Resp<?> addUser(String name,String storeOrganId,String password,String mobilePhone,String birthday,String sex,String email){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> data = new HashMap<String,String>();
			data.put("storeOrganId", BaseConstant.STORE_ID);
			data.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
			data.put("password",password);
			data.put("name",name);
			data.put("mobile", mobilePhone);
			data.put("birthday", birthday);
			data.put("sex", sex);
			data.put("email", email);
			String jsonStr = JSONObject.toJSONString(data);
			String uuid = UUIDUtils.random();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data.put("path", "/core/user");
			data.put("X-POS-REQUEST-ID",uuid);
			data.put("X-POS-REQUEST-TIME", sdf.format(date));
			data.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(data));
			data.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.postJsonMofangV2("/core/user",data,jsonStr)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 考勤日志
	 * @param userId
	 * @param companyOrganId
	 * @param storeOrganId
	 * @param page
	 * @return
	 */
	@RequestMapping(path = "/mofang/sign_log")
	@ResponseBody
	public Resp<?> signLog(String userId,String companyOrganId,String storeOrganId,Integer page){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			String url = "/core/operation_log?pageSize=25&pageNum="+page+"&storeOrganId="+BaseConstant.STORE_ID+"&companyOrganId="+BaseConstant.BASE_COMPANY_ID;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StringUtil.isBlank(userId)){
				map.put("userId",userId);
				url+= "&userId="+userId;
			}
			map.put("pageNum",String.valueOf(page));
			map.put("pageSize","25");
			map.put("storeOrganId", BaseConstant.STORE_ID);
			map.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
			map.put("path", "/core/operation_log");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.getMofangV2(url,map)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * 订单分析接口
	 * @param beginTime
	 * @param endTime
	 * @param storeOrganId
	 * @return
	 */
	@RequestMapping(path = "/mofang/order/statistics")
	@ResponseBody
	public Resp<?> orderStatistics(String beginTime,String endTime,String storeOrganId){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			String url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.STORE_ID+"&endTime="+endTime+"&status=UNPAY";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("storeOrganId", BaseConstant.STORE_ID);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			map.put("status", "UNPAY");
			map.put("path", "/park/product/statistics");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			JSONObject unpayJson =  JSONObject.parseObject(HttpUtils.getMofangV2(url, map));
			ProOrderStatisticsModel unpay = unpayJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			map = new HashMap<String,String>();
			uuid = UUIDUtils.random();
			date = new Date();
			url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.STORE_ID+"&endTime="+endTime+"&status=PAYED";
			map.put("storeOrganId", BaseConstant.STORE_ID);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			map.put("status", "PAYED");
			map.put("path", "/park/product/statistics");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			JSONObject payedJson =  JSONObject.parseObject(HttpUtils.getMofangV2(url, map));
			ProOrderStatisticsModel payed = payedJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			map = new HashMap<String,String>();
			uuid = UUIDUtils.random();
			date = new Date();
			url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.STORE_ID+"&endTime="+endTime+"&status=NOT_PAY";
			map.put("storeOrganId", BaseConstant.STORE_ID);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			map.put("status", "NOT_PAY");
			map.put("path", "/park/product/statistics");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			JSONObject notPayJson =  JSONObject.parseObject(HttpUtils.getMofangV2(url, map));
			ProOrderStatisticsModel notPay = notPayJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			map = new HashMap<String,String>();
			uuid = UUIDUtils.random();
			date = new Date();
			url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.STORE_ID+"&endTime="+endTime+"&status=IN_PARK";
			map.put("storeOrganId", BaseConstant.STORE_ID);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
			map.put("status", "IN_PARK");
			map.put("path", "/park/product/statistics");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			JSONObject inParkJson = JSONObject.parseObject(HttpUtils.getMofangV2(url, map));
			ProOrderStatisticsModel inPark = inParkJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			Map<String, ProOrderStatisticsModel> data = new HashMap<String, ProOrderStatisticsModel>();
			data.put("unpay", unpay);
			data.put("payed", payed);
			data.put("notPay", notPay);
			data.put("inpark", inPark);
			ProOrderStatisticsModel pModel = new ProOrderStatisticsModel();
			pModel.setCountAmount(unpay.getCountAmount()+payed.getCountAmount()+inPark.getCountAmount()+notPay.getCountAmount());
			pModel.setMinuteAmount(unpay.getMinuteAmount()+notPay.getMinuteAmount()+inPark.getMinuteAmount()+payed.getMinuteAmount());
			pModel.setPriceAmount(unpay.getPriceAmount()+notPay.getPriceAmount()+inPark.getPriceAmount()+payed.getPriceAmount());
			data.put("total", pModel);
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取订单列表
	 * @param companyId
	 * @param page
	 * @return
	 */
	@RequestMapping(path = "/mofang/order")
	@ResponseBody
	public Resp<?> order(String companyId,Integer page){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			String url = "/park/product?pageSize=25&pageNum="+page+"&companyOrganId="+BaseConstant.BASE_COMPANY_ID;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("pageNum",String.valueOf(page));
			map.put("pageSize","25");
			map.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
			map.put("path", "/park/product");
			map.put("X-POS-REQUEST-ID",uuid);
			map.put("X-POS-REQUEST-TIME", sdf.format(date));
			map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
			map.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.getMofangV2(url,map)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * 增加收费规则
	 * @param period
	 * @param amountOfMoney
	 * @param amountOfMoneyForNotEnough
	 * @param storeOrganId
	 * @param companyOrganId
	 * @return
	 */
	@RequestMapping(path = "/mofang/add_rule")
	@ResponseBody
	public Resp<?> addRole(String period,String amountOfMoney,String amountOfMoneyForNotEnough,String storeOrganId,String companyOrganId){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(storeOrganId)){
				storeOrganId = BaseConstant.BASE_STORE_ID;
			}
			if(StringUtil.isBlank(companyOrganId)){
				companyOrganId = BaseConstant.BASE_COMPANY_ID;
			}
			return new Resp<>(HttpData.mofang_add_rule(getMofangSessionId(), companyOrganId, storeOrganId, period, amountOfMoney, amountOfMoneyForNotEnough));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * 更新收费规则
	 * @param period
	 * @param amountOfMoney
	 * @param amountOfMoneyForNotEnough
	 * @param storeOrganId
	 * @param companyOrganId
	 * @return
	 */
	@RequestMapping(path = "/mofang/update_rule")
	@ResponseBody
	public Resp<?> updateRole(String period,String amountOfMoney,String amountOfMoneyForNotEnough,String ruleId){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(HttpData.mofang_update_rule(getMofangSessionId(),period, amountOfMoney, amountOfMoneyForNotEnough,ruleId));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 增加公司组织
	 * @param companyId
	 * @param name
	 * @return
	 */
	@RequestMapping(path = "/mofang/add_company_organ")
	@ResponseBody
	public Resp<?> addOrgan(String companyId,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(HttpData.mofang_add_company_organ(getMofangSessionId(),"10000", "新疆立昂"));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 增加门店组织
	 * @param companyId
	 * @param name
	 * @return
	 */
	@RequestMapping(path = "/mofang/add_store_organ")
	@ResponseBody
	public Resp<?> addStoreOrgan(String companyId,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(HttpData.mofang_add_store_organ(getMofangSessionId(),BaseConstant.BASE_COMPANY_ID, "新疆立昂"));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	

	/**
	 * 获取收费规则列表
	 * @param companyId
	 * @param page
	 * @return
	 */
	@RequestMapping(path = "/mofang/rule")
	@ResponseBody
	public Resp<?> ruleList(String companyId){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(HttpData.mofang_get_rule(getMofangSessionId(), BaseConstant.BASE_COMPANY_ID));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	

	
	
	
	
}
