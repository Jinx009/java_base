package main.entry.webapp.page;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import main.entry.webapp.BaseController;

@Controller
@RequestMapping(value = "/home/p")
public class HomePageController extends BaseController{
	
	@RequestMapping(path = "/pro_driver")
	public String user(){
		return "/home/pro_driver";
	}
	
	@RequestMapping(path = "/pro_pic")
	public String pic(){
		return "/home/pro_pic";
	}
	
	@RequestMapping(path = "/pro_pic_new")
	public String picNew(){
		return "/home/pro_pic_new";
	}
	
	@RequestMapping(path = "/pro_book")
	public String book(){
		return "/home/pro_book";
	}
	
	@RequestMapping(path = "/pro_book_new")
	public String bookNew(){
		return "/home/pro_book_new";
	}
	
	@RequestMapping(path = "/pro_book_edit")
	public String bookEdit(){
		return "/home/pro_book_edit";
	}
	
	@RequestMapping(path = "/pro_book_order")
	public String bookOrder(){
		return "/home/pro_book_order";
	}
	
	@RequestMapping(path = "/pro_book_post")
	public String post(){
		return "/home/pro_book_post";
	}
	
	@RequestMapping(path = "/pro_book_trans")
	public String trans(){
		return "/home/pro_book_post";
	}
	
	@RequestMapping(path = "/pro_book_post_edit")
	public String postEdit(){
		return "/home/pro_book_post_edit";
	}

}
