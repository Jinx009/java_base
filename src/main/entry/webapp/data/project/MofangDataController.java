package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Resp<?> getOrgan(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(httpService.getMofang(getMofangSessionId(),HttpData.mofang_get_organ(MOFANG_COMPANY_ID)));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
