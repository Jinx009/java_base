package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProPic;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProPicService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_pic")
public class ProPicHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProPicHomeDataController.class);
	
	@Autowired
	private ProPicService proPicService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
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
	
	@RequestMapping(path = "/add")
	@ResponseBody
	public Resp<?> save(String name,String desc,String picPath,String autor,String mobilePhone){
		Resp<?> resp = new Resp<>(false);
		try {
			proPicService.saveNew(name, desc, picPath, autor, mobilePhone);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
