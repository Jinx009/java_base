package main.entry.webapp.data.project;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.project.model.ProOrderStatisticsModel;
import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@RequestMapping(value = "/home/d")
@Controller
public class MofangDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(MofangDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	
	
	@RequestMapping(path = "/mofang/organ")
	@ResponseBody
	public Resp<?> getOrgan(String status,String type,String companyOrganId,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(companyOrganId)){
				companyOrganId = BaseConstant.BASE_COMPANY_ID;
			}
			return new Resp<>(JSON.parseObject(httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_organ(status,type,companyOrganId,name))));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/mofang/update_user")
	@ResponseBody
	public Resp<?> updateUser(String storeOrganId,String mobile,String sex,String email,String status,String userId,String name,String birthday,String password){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(storeOrganId)){
				storeOrganId = BaseConstant.BASE_STORE_ID;
			}
			return new Resp<>(HttpData.mofang_update_user(getMofangSessionId(),name,storeOrganId,password,mobile,birthday,sex,email,userId,status));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/mofang/user")
	@ResponseBody
	public Resp<?> getUser(String companyOrganId){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(companyOrganId)){
				companyOrganId = BaseConstant.BASE_COMPANY_ID;
			}
			return new Resp<>(JSON.parseObject(httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_user(companyOrganId))));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/mofang/add_user")
	@ResponseBody
	public Resp<?> addUser(String name,String storeOrganId,String password,String mobilePhone,String birthday,String sex,String email){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(storeOrganId)){
				storeOrganId = BaseConstant.BASE_STORE_ID;
			}
			return new Resp<>(HttpData.mofang_add_user(getMofangSessionId(), name, storeOrganId, password, mobilePhone, birthday,sex,email));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/mofang/sign_log")
	@ResponseBody
	public Resp<?> signLog(String userId,String companyOrganId,String storeOrganId,Integer page){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(storeOrganId)){
				storeOrganId = BaseConstant.BASE_STORE_ID;
			}
			if(StringUtil.isBlank(companyOrganId)){
				companyOrganId = BaseConstant.BASE_COMPANY_ID;
			}
			return new Resp<>(HttpData.mofang_get_sign(getMofangSessionId(), companyOrganId, storeOrganId, userId,page));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
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
	
	@RequestMapping(path = "/mofang/order")
	@ResponseBody
	public Resp<?> order(String companyId,Integer page){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(JSON.parseObject(httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_order(companyId,page))));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/mofang/order/statistics")
	@ResponseBody
	public Resp<?> orderStatistics(String beginTime,String endTime,String storeOrganId){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(storeOrganId)){
				storeOrganId = "10352";
			}
			JSONObject unpayJson =  HttpData.getOrderStatistics(getMofangSessionId(), beginTime, endTime, "UNPAY",storeOrganId);
			ProOrderStatisticsModel unpay = unpayJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			JSONObject payedJson =  HttpData.getOrderStatistics(getMofangSessionId(), beginTime, endTime, "PAYED",storeOrganId);
			ProOrderStatisticsModel payed = payedJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			JSONObject notPayJson =  HttpData.getOrderStatistics(getMofangSessionId(), beginTime, endTime, "NOT_PAY",storeOrganId);
			ProOrderStatisticsModel notPay = notPayJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			JSONObject inParkJson =  HttpData.getOrderStatistics(getMofangSessionId(), beginTime, endTime, "IN_PARK",storeOrganId);
			ProOrderStatisticsModel inPark = inParkJson.getJSONObject("data").getObject("statisticsProduct", ProOrderStatisticsModel.class);
			Map<String, ProOrderStatisticsModel> map = new HashMap<String, ProOrderStatisticsModel>();
			map.put("unpay", unpay);
			map.put("payed", payed);
			map.put("notPay", notPay);
			map.put("inpark", inPark);
			ProOrderStatisticsModel pModel = new ProOrderStatisticsModel();
			pModel.setCountAmount(unpay.getCountAmount()+payed.getCountAmount()+inPark.getCountAmount()+notPay.getCountAmount());
			pModel.setMinuteAmount(unpay.getMinuteAmount()+notPay.getMinuteAmount()+inPark.getMinuteAmount()+payed.getMinuteAmount());
			pModel.setPriceAmount(unpay.getPriceAmount()+notPay.getPriceAmount()+inPark.getPriceAmount()+payed.getPriceAmount());
			map.put("total", pModel);
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
