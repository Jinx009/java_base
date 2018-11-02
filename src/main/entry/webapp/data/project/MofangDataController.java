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
	public Resp<?> signLog(String companyOrganId,String storeOrganId,Integer page){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			String url = "/core/operation_log?pageSize=25&pageNum="+page+"&storeOrganId="+BaseConstant.BASE_STORE_ID+"&companyOrganId="+BaseConstant.BASE_COMPANY_ID;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("pageNum",String.valueOf(page));
			map.put("pageSize","25");
			map.put("storeOrganId", BaseConstant.BASE_STORE_ID);
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
			String url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.BASE_STORE_ID+"&endTime="+endTime+"&status=UNPAY";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("storeOrganId", BaseConstant.BASE_STORE_ID);
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
			url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.BASE_STORE_ID+"&endTime="+endTime+"&status=PAYED";
			map.put("storeOrganId", BaseConstant.BASE_STORE_ID);
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
			url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.BASE_STORE_ID+"&endTime="+endTime+"&status=NOT_PAY";
			map.put("storeOrganId", BaseConstant.BASE_STORE_ID);
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
			url = "/park/product/statistics?beginTime="+beginTime+"&storeOrganId="+BaseConstant.BASE_STORE_ID+"&endTime="+endTime+"&status=IN_PARK";
			map.put("storeOrganId", BaseConstant.BASE_STORE_ID);
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
			return new Resp<>(data);
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
			Map<String, String> map = new HashMap<String,String>();
			String uuid = UUIDUtils.random();
			Date date = new Date();
			String url = "/park/charging_rule?companyOrganId="+BaseConstant.BASE_COMPANY_ID+"&storeOrganId="+BaseConstant.BASE_STORE_ID;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("storeOrganId",BaseConstant.BASE_STORE_ID);
			map.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
			map.put("path", "/park/charging_rule");
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
	public Resp<?> addRole(String period,String amountOfMoneyForNotEnough,String beginTime,String endTime,String freeMoneyAmountPeriod,
			String chargingRuleGroup,String maxAmountOfMoney,String amountOfMoney,String type){
		Resp<?> resp = new Resp<>(false);
		try {
//			"period":900, // 计费周期 
//			"amountOfMoney":"0.02", // 每个周期金额
//			"amountOfMoneyForNotEnough":"0.01", // 不满足整个周期金额 
//			“type":"IMMEDIATELY", //IMMEDIATELY ⼀一⼝口价只能存在⼀一个 ACCUMULATIVE 累计
//			"beginTime":0, // ⼀一天内的起⽌止秒数 00:00:00 为0 0 <= beginTime < endTime <= 86400 type= ACCUMULATIVE时有效
//			"endTime":0 // 一天内的起止秒数 00:00:00 为0 0 <= beginTime < endTime <= 86400 type= ACCUMULATIVE时有效，
//			“freeMoneyAmountPeriod":0,//不不计费时间 
//			“maxAmountOfMoney”:0, //最⾼高计费时⻓长
//			“chargingRuleGroup”:”DAY”⾦金金额，相同组使⽤用同⼀一个最⼤大⾦金金额
			Map<String, String> data = new HashMap<String,String>();
			data.put("storeOrganId", BaseConstant.BASE_STORE_ID);
			data.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
			data.put("period",period);
			data.put("type",type);
			data.put("amountOfMoney",amountOfMoney);
			data.put("amountOfMoneyForNotEnough",amountOfMoneyForNotEnough);
			data.put("beginTime", beginTime);
			data.put("endTime", endTime);
			data.put("freeMoneyAmountPeriod",freeMoneyAmountPeriod);
			data.put("maxAmountOfMoney", maxAmountOfMoney);
			data.put("chargingRuleGroup", chargingRuleGroup);
			String jsonStr = JSONObject.toJSONString(data);
			String uuid = UUIDUtils.random();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data.put("path", "/park/charging_rule");
			data.put("X-POS-REQUEST-ID",uuid);
			data.put("X-POS-REQUEST-TIME", sdf.format(date));
			data.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(data));
			data.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.postJsonMofangV2("/park/charging_rule",data,jsonStr)));
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
	public Resp<?> updateRole(String period,String amountOfMoneyForNotEnough,String beginTime,String endTime,String freeMoneyAmountPeriod,
			String chargingRuleGroup,String maxAmountOfMoney,String amountOfMoney,String ruleId,String type){
		Resp<?> resp = new Resp<>(false);
		try {
			Map<String, String> data = new HashMap<String,String>();
			data.put("storeOrganId", BaseConstant.BASE_STORE_ID);
			data.put("companyOrganId",BaseConstant.BASE_COMPANY_ID);
			data.put("ruleId",ruleId);
			data.put("period",period);
			data.put("type",type);
			data.put("amountOfMoney",amountOfMoney);
			data.put("amountOfMoneyForNotEnough",amountOfMoneyForNotEnough);
			data.put("beginTime", beginTime);
			data.put("endTime", endTime);
			data.put("maxAmountOfMoney", maxAmountOfMoney);
			data.put("freeMoneyAmountPeriod",freeMoneyAmountPeriod);
			data.put("chargingRuleGroup", chargingRuleGroup);
			String jsonStr = JSONObject.toJSONString(data);
			String uuid = UUIDUtils.random();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			data.put("path", "/park/charging_rule/update");
			data.put("X-POS-REQUEST-ID",uuid);
			data.put("X-POS-REQUEST-TIME", sdf.format(date));
			data.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
			String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(data));
			data.put("X-POS-REQUEST-SIGN", sign);
			return new Resp<>(JSON.parseObject(HttpUtils.postJsonMofangV2("/park/charging_rule/update",data,jsonStr)));
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
	


	

	
	
	
	
}
