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
import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/front/d")
public class ProUserFrontDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProUserFrontDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String mobilePhone,String pwd,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(mobilePhone)){
				return new Resp<>(RespData.ERROR_CODE,"手机号码不能为空！",null);
			}else if(StringUtil.isBlank(pwd)){
				return new Resp<>(RespData.ERROR_CODE,"密码不能为空！",null);
			}
			ProUser proUser = proUserService.login(mobilePhone, pwd);
			if(proUser!=null){
				setSessionFront(request, proUser);
				return new Resp<>(RespData.OK_CODE,RespData.OK_MSG,MD5Util.md5(pwd));
			}
			return new Resp<>(RespData.ERROR_CODE,"账号或密码有误！",null);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}

	
	
}
