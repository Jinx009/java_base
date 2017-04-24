package main.entry.webapp.data.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeResource;
import lombok.Setter;
import service.basicFunctions.home.HomeResourceService;
import utils.Resp;
import utils.RespData;
import utils.model.HomeConfigConstant;


@Controller
@Setter
@RequestMapping(value = "/home/d")
public class HomeResourceController {

	private static final Logger logger = LoggerFactory.getLogger(HomeResourceController.class);
	
	@Autowired
	private HomeResourceService homeResourceService;
	
	@RequestMapping(path = "/menu",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> getMenu(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			HttpSession session = request.getSession();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,HomeConfigConstant.getResourceBySession(session));
//			logger.warn(" [HomeResourceController.getMenu][data:{}] ",resp);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	/**
	 * 后台管理菜单列表
	 * @return
	 */
	@RequestMapping(path = "/page")
	@ResponseBody
	public Resp<?> getResource(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<HomeResource> list = homeResourceService.getPageResource();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			logger.warn("[data:{}] ",resp);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	/**
	 * 后台管理菜单列表
	 * @return
	 */
	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> getDataResource(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<HomeResource> list = homeResourceService.getDataResource();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			logger.warn("[data:{}] ",resp);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
}
