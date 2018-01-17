package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import common.helper.StringUtil;
import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
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
				companyOrganId = "10351";
			}
			return new Resp<>(JSON.parseObject(httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_organ(status,type,companyOrganId,name))));
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
				companyOrganId = "10351";
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
				storeOrganId = "10352";
			}
			return new Resp<>(HttpData.mofang_add_user(getMofangSessionId(), name, storeOrganId, password, mobilePhone, birthday,sex,email));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/mofang/rule_user")
	@ResponseBody
	public Resp<?> addRole(String period,String amountOfMoney,String amountOfMoneyForNotEnough,String storeOrganId,String companyOrganId){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(storeOrganId)){
				storeOrganId = "10352";
			}
			if(StringUtil.isBlank(companyOrganId)){
				companyOrganId = "10351";
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
			return new Resp<>(HttpData.mofang_add_store_organ(getMofangSessionId(),"10351", "新疆立昂"));
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
	
}
