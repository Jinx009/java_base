package main.entry.webapp.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import database.models.subcribe.SubcribeUser;
import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/subcribe/p")
public class SubcribePageController extends BaseController {

	@RequestMapping(path = "login")
	public String login(){
		return "/subcribe/login";
	}
	
	@RequestMapping(path = "register")
	public String register(){
		return "/subcribe/register";
	}
	
	@RequestMapping(path = "order")
	public String order(HttpServletRequest request){
		HttpSession session = request.getSession();
		SubcribeUser subcribeUser = (SubcribeUser) session.getAttribute("subcribeUser");
		if(subcribeUser==null){
			return "/subcribe/login";
		}else{
			return "/subcribe/order";
		}
	}
	
	@RequestMapping(path = "subcribe")
	public String subcribe(HttpServletRequest request){
		HttpSession session = request.getSession();
		SubcribeUser subcribeUser = (SubcribeUser) session.getAttribute("subcribeUser");
		if(subcribeUser==null){
			return "/subcribe/login";
		}else{
			return "/subcribe/subcribe";
		}
	}
	
}
