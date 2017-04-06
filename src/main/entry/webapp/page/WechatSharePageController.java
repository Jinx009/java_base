package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "wechat/share")
public class WechatSharePageController extends BaseController{

	@RequestMapping(path = "/index")
	public String index(HttpServletRequest request){
		getJsApi(request);
		return "/wechat_share";
	}
	
}
