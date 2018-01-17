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
import utils.HttpData;
import utils.Resp;

@RequestMapping(value = "/home/d")
@Controller
public class ErrorFlowDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ErrorFlowDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/error")
	@ResponseBody
	public Resp<?> errors(Integer page){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(JSON.parse(httpService.get(HttpData.errorFlow(page, null))));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
