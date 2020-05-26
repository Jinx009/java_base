package main.entry.webapp.data.project.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.Resp;

@RequestMapping(value = "/f/weapp")
@Controller
public class FrontWeappController {

	private static final Logger log = LoggerFactory.getLogger(FrontWeappController.class);

	@RequestMapping(path = "/notice")
	@ResponseBody
	public Resp<?> notice(HttpServletRequest request, HttpServletResponse response) {
		Resp<?> resp = new Resp<>(false);
		String inputLine = "";
		String notityXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.warn("异步回调XML信息：{}", notityXml);
		return resp;
	}

}
