package main.entry.webapp.data.pro;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.pro.Pro;
import database.models.home.pro.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.pro.ProService;
import service.basicFunctions.pro.ProUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/data/")
public class ProDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(ProDataController.class);
	
	@Autowired
	private ProService proService;
	@Autowired
	private ProUserService proUserService;
	
	/**
	 * 后端使用列表
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/pro/home/list",method = RequestMethod.POST) 
	@ResponseBody
	public Resp<?> findHomePro(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			ProUser proUser = getSessionProUser(request);
			if(proUser!=null){
				List<Pro> list = proService.findByAppId(proUser.getAppId());
				resp = new Resp<>(RespData.OK_CODE, RespData.OK_MSG, list);
				return resp;
			}
			return resp;
		} catch (Exception e) {
			resp.setMsg(e.getMessage());
			logger.error("[error:{}] ", e);
			return resp;
		}
	}
	
	/**
	 * 前端使用列表
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/pro/web/list",method = RequestMethod.POST) 
	@ResponseBody
	public Resp<?> findWebPro(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			String domain = request.getRemoteHost();
			logger.warn("domain:{}",domain);
			ProUser proUser = proUserService.findByDomain(domain);
			if(proUser!=null){
				List<Pro> list = proService.findByAppId(proUser.getAppId());
				resp = new Resp<>(RespData.OK_CODE, RespData.OK_MSG, list);
				return resp;
			}
			return resp;
		} catch (Exception e) {
			resp.setMsg(e.getMessage());
			logger.error("[error:{}] ", e);
			return resp;
		}
	}
	
}
