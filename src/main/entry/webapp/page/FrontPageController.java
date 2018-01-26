package main.entry.webapp.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
public class FrontPageController extends BaseController{

	@RequestMapping(value = "/f/p/login")
	public String login(){
		return "/front/login";
	}
	
	@RequestMapping(value = "/f/p/book")
	public String book(){
		return "/front/book";
	}
	
	@RequestMapping(value = "/f/p/pic")
	public String pic(){
		return "/front/pic";
	}
	
	@RequestMapping(value = "/front/p/me")
	public String me(){
		return "/front/me";
	}
	
	@RequestMapping(value = "/front/p/book_post")
	public String book_post(){
		return "/front/book_post";
	}
	
}
