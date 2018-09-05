package main.entry.webapp.data.project.front;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.MD5Util;
import common.helper.StringUtil;
import database.models.project.ProDriver;
import database.models.project.ProSetting;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProDriverService;
import service.basicFunctions.project.ProSettingService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/front/d/pro_driver")
public class ProDriverFrontDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProDriverFrontDataController.class);
	
	@Autowired
	private ProDriverService proDriverService;
	@Autowired
	private ProSettingService proSettingService;
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String mobilePhone,String pwd,HttpServletRequest request){
		Resp<?> resp = new Resp<>(RespData.ERROR_CODE,"账号或密码有误！",null);
		try {
			if(StringUtil.isBlank(mobilePhone)){
				return new Resp<>(RespData.ERROR_CODE,"手机号码不能为空！",null);
			}else if(StringUtil.isBlank(pwd)){
				return new Resp<>(RespData.ERROR_CODE,"密码不能为空！",null);
			}
			ProDriver proDriver = proDriverService.login(mobilePhone, pwd);
			if(proDriver!=null){
				setSessionFront(request, proDriver);
				return new Resp<>(RespData.OK_CODE,RespData.OK_MSG,MD5Util.md5(pwd));
			}
			proDriver = proDriverService.findByMobilePhone(mobilePhone);
			if(proDriver!=null){
				return new Resp<>(RespData.ERROR_CODE,"账号或密码有误！",null);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/register")
	@ResponseBody
	public Resp<?> register(String mobilePhone,String pwd,String name,String plateNumber,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			ProSetting proSetting = proSettingService.findByName("register");
			if(proSetting!=null&&0==proSetting.getStatus()) {
				return new Resp<>(RespData.ERROR_CODE,"注册通道已经关闭！",null);
			}
			if(StringUtil.isBlank(mobilePhone)){
				return new Resp<>(RespData.ERROR_CODE,"手机号码不能为空！",null);
			}else if(StringUtil.isBlank(pwd)){
				return new Resp<>(RespData.ERROR_CODE,"密码不能为空！",null);
			}
			ProDriver proDriver = proDriverService.findByMobilePhone(mobilePhone);
			if(proDriver!=null){
				return new Resp<>(RespData.ERROR_CODE,"该号码已经注册！","");
			}
			proDriverService.save(mobilePhone, name, pwd, plateNumber);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}

	@RequestMapping(path = "/login_m")
	@ResponseBody
	public Resp<?> login_m(String mobilePhone,String pwd,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(mobilePhone)){
				return new Resp<>(RespData.ERROR_CODE,"手机号码不能为空！",null);
			}else if(StringUtil.isBlank(pwd)){
				return new Resp<>(RespData.ERROR_CODE,"密码不能为空！",null);
			}
			ProDriver proDriver = proDriverService.login_m(mobilePhone, pwd);
			if(proDriver!=null){
				setSessionFront(request, proDriver);
				return new Resp<>(RespData.OK_CODE,RespData.OK_MSG,MD5Util.md5(pwd));
			}
			proDriver = proDriverService.findByMobilePhone(mobilePhone);
			if(proDriver!=null){
				return new Resp<>(RespData.ERROR_CODE,"账号或密码有误！",null);
			}else{
				return new Resp<>(RespData.ERROR_CODE,"账户不存在！",null);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
