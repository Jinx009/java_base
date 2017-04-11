package main.entry.webapp.page.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import database.models.home.HomeResource;
import utils.model.HomeConfigConstant;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	
	/**
	 * 登录页
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/index")
	public String index(HttpServletRequest request){
		return "/home/index";
	}
	
	/**
	 * 登录后首页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/page/index")
	public String page(HttpServletRequest request){
		List<HomeResource> list = (List<HomeResource>) HomeConfigConstant.getResourceBySession(request.getSession());
		if(null!=list&&!list.isEmpty()){
			for(HomeResource homeResource:list){
				if(0!=homeResource.getParentId()){
					return homeResource.getUri();
				}
			}
		}
		return "redirect:/home/error";
	}

	/**
	 * 错误页
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "/error")
	public String error(HttpServletRequest request){
		return "/home/error";
	}
}
