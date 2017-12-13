package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/account")
public class AccountDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(AccountDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/pos")
	@ResponseBody
	public Resp<?> account(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get(HttpData.accountUrl());
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
}
