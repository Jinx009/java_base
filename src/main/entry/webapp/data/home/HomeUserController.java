package main.entry.webapp.data.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.vo.HomeUserVo;
import main.entry.webapp.BaseController;
import service.basicFunctions.home.HomeUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/home/d")
public class HomeUserController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(HomeUserController.class);
	
	@Autowired
	private HomeUserService homeUserService;
	
	@RequestMapping(path = "/user",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> user(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			List<HomeUserVo> list = homeUserService.getHomeUser();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			logger.warn(" [HomeUserController.user][data:{}] ",resp);
		} catch (Exception e) {
			resp = new Resp<>(false);
			resp.setMsg(e.getMessage());
			logger.warn(" [HomeUserController.user][error:{}] ",e);
		}
		return resp;
	}
	
	
	
}