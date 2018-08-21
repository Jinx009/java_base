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
public class ProResultDataController extends BaseController{

	private static final Logger log =  LoggerFactory.getLogger(ProResultDataController.class);
	
	@Autowired
	private ProResultService proResultService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> selectList(Integer paperId){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proResultService.list(paperId));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
