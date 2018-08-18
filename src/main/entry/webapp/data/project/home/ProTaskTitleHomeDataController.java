package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProTaskTitleService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_task_title")
public class ProTaskTitleHomeDataController extends BaseController{

	private static final Logger log =  LoggerFactory.getLogger(ProTaskTitleHomeDataController.class);
	
	@Autowired
	private ProTaskTitleService proTaskTitleService;
	
	@RequestMapping(path = "/selectList")
	@ResponseBody
	public Resp<?> selectList(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proTaskTitleService.list());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
