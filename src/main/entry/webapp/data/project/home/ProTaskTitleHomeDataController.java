package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProResultService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/result")
public class ProTaskTitleHomeDataController extends BaseController{

	private static final Logger log =  LoggerFactory.getLogger(ProTaskTitleHomeDataController.class);
	
	@Autowired
	private ProResultService proResultService;
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> selectList(String test){
		Resp<?> resp = new Resp<>(false);
		try {
			
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
