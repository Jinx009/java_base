package main.entry.webapp.data.project.front;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProPic;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProPicService;
import utils.Resp;

@Controller
@RequestMapping(value = "/front/d/pro_pic")
public class ProPicFrontDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProPicFrontDataController.class);
	
	@Autowired
	private ProPicService proPicService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ProPic> list = proPicService.list();
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
