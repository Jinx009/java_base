package main.entry.webapp.data.pro;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.pro.ProWechatConn;
import main.entry.webapp.BaseController;
import service.basicFunctions.pro.ProWechatConnService;
import utils.Resp;

@Controller
@RequestMapping(value = "/front/d")
public class ProWechatConnDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ProWechatConnDataController.class);
	
	@Autowired
	private ProWechatConnService proWechatConnService;
	
	@RequestMapping(path = "/userLike")
	@ResponseBody
	public Resp<?> userConn(Integer userId){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ProWechatConn> list = proWechatConnService.findUserLike(userId);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
