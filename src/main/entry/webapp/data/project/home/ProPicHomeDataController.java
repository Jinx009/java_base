package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import database.common.PageDataList;
import database.models.project.ProPic;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProPicService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/p")
public class ProPicHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProPicHomeDataController.class);
	
	@Autowired
	private ProPicService proPicService;
	
	@RequestMapping(path = "/list")
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProPic> list = proPicService.homeList(p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
