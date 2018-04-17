package main.entry.webapp.data.accessControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.Resp;

@RequestMapping(value = "/home/cloud/accessControl")
@Controller()
public class AccessControlDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(AccessControlDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/log")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get("http://106.14.94.245:8090/gtw/rest/accessControl/log?p="+p);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(JSON.parseObject(result).getString("data")));
			return resp;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/person")
	@ResponseBody
	public Resp<?> person(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get("http://106.14.94.245:8090/gtw/rest/accessControl/person?p="+p);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(JSON.parseObject(result).getString("data")));
			return resp;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
