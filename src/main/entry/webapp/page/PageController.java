package main.entry.webapp.page;





import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class PageController {
	

	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(){
		return "/index";
	}
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "/test")
	public String test(){
		return "/test";
	}
	

}
