package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/config")
public class HomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(HomeDataController.class);
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String userName,String pwd){
		Resp<?> resp = new Resp<>(false);
		try {
			if("zhanway".equals(userName)&&"admin".equals(pwd)){
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/loginOut")
	@ResponseBody
	public Resp<?> loginOut(){
		Resp<?> resp = new Resp<>(false);
		try {
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
