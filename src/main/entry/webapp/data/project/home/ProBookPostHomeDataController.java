package main.entry.webapp.data.project.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProBookPost;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProBookPostService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_book_post")
public class ProBookPostHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProBookPostHomeDataController.class);
	
	@Autowired
	private ProBookPostService proBookPostService;
	
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProBookPost> list = proBookPostService.homeList(p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "edit")
	@ResponseBody
	public Resp<?> update(Integer id,Integer points,Integer sellPoints,String pointsRemark){
		Resp<?> resp = new Resp<>(false);
		try {
			proBookPostService.update(id, points, pointsRemark, sellPoints);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
}
