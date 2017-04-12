package main.entry.webapp.data.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Setter;
import utils.Resp;
import utils.RespData;
import utils.model.HomeConfigConstant;


@Controller
@Setter
@RequestMapping(value = "/home/d")
public class HomeResourceController {

	private static final Logger logger = LoggerFactory.getLogger(HomeResourceController.class);
	
	private Resp<?> resp;
	
	@RequestMapping(path = "/menu")
	@ResponseBody
	public Resp<?> getMenu(HttpServletRequest request){
		HttpSession session = request.getSession();
		resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,HomeConfigConstant.getResourceBySession(session));
		logger.warn(" [HomeResourceController.getMenu][data:{}] ",resp);
		return resp;
	}
	
}
