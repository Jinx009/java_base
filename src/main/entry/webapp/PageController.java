package main.entry.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {
	
	private static Logger log = LoggerFactory.getLogger(PageController.class);
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "")
	public String base(){
		return "/index";
	}
	
	@RequestMapping(value = "/iot-admin/welcome")
	public String admin(){
		return "/index";
	}
	
	@RequestMapping(value = "/index")
	public String index(){
		return "/index";
	}
	
	@RequestMapping(value = "/404")
	public String _404(){
		return "/404";
	}
	
	@RequestMapping(value = "/check")
	@ResponseBody
	public String check(HttpServletResponse resp){
		File file = new File("/apps/logs/heart_log_time.txt");
		try {
			StringBuilder result = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(file));;
			String s = null;
	        while ((s = br.readLine()) != null) {
	            result.append(s);
	        }
	        br.close();
	        long timestamp = Long.valueOf(result.toString().replaceAll("\\s*", ""));
	        Date date = new Date();
	        if ((date.getTime() - timestamp) / 1000 / 60 > 3) {
	            log.warn("starttime:{},endtime:{}", timestamp, date.getTime());
	            resp.setStatus(300);
	            return "FALSE";
	        }
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		 resp.setStatus(200);
		return "OK";
	}
	
	/**
	 * spring报错
	 * @return
	 */
	@RequestMapping(value = "DevMgmt/DiscoveryTree.xml")
	public String DevMgmt(){
		return "/DevMgmt/DiscoveryTree";
	}


}
