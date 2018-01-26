package main.entry.webapp.data.project.front;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProBookPost;
import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProBookPostService;
import utils.Resp;

@Controller
@RequestMapping(value = "/front/d/pro_book_post")
public class ProBookPostFrontDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProBookPostFrontDataController.class);
	
	@Autowired
	private ProBookPostService proBookPostService;
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> savePost(String postNum,String remark,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			ProUser proUser = getSessionFrontUser(request);
			proBookPostService.saveNew(postNum, remark, proUser.getMobilePhone());
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			ProUser proUser = getSessionFrontUser(request);
			List<ProBookPost> list = proBookPostService.personList(proUser.getMobilePhone());
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	
}
