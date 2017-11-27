package main.entry.webapp;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import database.models.home.HomeUser;
import utils.StringUtil;
import utils.enums.PageCode;

/**
 * 全页面跳转
 * 
 * @author jinx
 *
 */
@Controller
public class PageServer extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PageServer.class);

	@RequestMapping(value = "/p/{type}/{content}/{verion}")
	public String page(@PathVariable(value = "type") String type, @PathVariable(value = "content") String content,
			@PathVariable(value = "verion") String verion, HttpServletRequest request) {
		String code = StringUtil.add(type, "_", content, "_", verion);
		PageCode pageCode = PageCode.getByCode(code);
		if (pageCode == null) {
			return "/404";
		} else {
			try {
				Enumeration<?> enu = request.getParameterNames();
				while (enu.hasMoreElements()) {
					String paraName = (String) enu.nextElement();
					request.setAttribute(request.getParameter(paraName), request.getParameter(paraName));
				}
				HomeUser homeUser = getSessionHomeUser(request);
				if (homeUser != null||!pageCode.isNeedLogin()) {
					return pageCode.getPage();
					
				}
				return "/index";
			} catch (Exception e) {
				logger.error("error:{}", e);
			}
		}
		return "/index";
	}

}
