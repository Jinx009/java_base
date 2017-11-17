package main.entry.webapp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import utils.enums.PageCode;

/**
 * 全页面跳转
 * 
 * @author jinx
 *
 */
public class PageServer extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(PageServer.class);
	
	@RequestMapping(value = "/p/{page}")
	public String page(@PathVariable(value = "page") String page,
									HttpServletRequest request) {
		PageCode pageCode = PageCode.getByCode(page);
		if (page == null) {
			return "/404";
		} else {
			try {
				if(!pageCode.isNeedLogin()){
					return pageCode.getPage();
				}
			} catch (Exception e) {
				logger.error("error:{}",e);
			}
		}
		return "/home/index";
	}

}
