package main.entry.webapp;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import utils.StringUtil;
import utils.enums.PageCode;

/**
 * 全页面跳转
 * 
 * @author jinx
 *
 */
public class PageServer extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(PageServer.class);
	
	@RequestMapping(value = "/p/{type}/{content}/{operation}")
	public String page(@PathVariable(value = "type") String type,
					   @PathVariable(value = "content") String content,
					   @PathVariable(value = "operation") String operation,
									HttpServletRequest request) {
		String code = StringUtil.add(type,"_",content,"_",operation);
		PageCode pageCode = PageCode.getByCode(code);
		if (pageCode == null) {
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
		return "/index";
	}

}
