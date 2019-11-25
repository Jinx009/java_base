package main.entry.webapp.data.project.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProVedioService;
import utils.Resp;

@Controller
@RequestMapping(value = "/f/pro_vedio")
public class FrontProVedioDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(FrontProVedioDataController.class);
	
	@Autowired
	private ProVedioService proVedioService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> findByLevel(Integer level,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proVedioService.findByLevel(level,p));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
