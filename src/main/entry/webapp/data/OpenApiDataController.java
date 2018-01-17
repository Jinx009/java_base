package main.entry.webapp.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import main.entry.webapp.BaseController;
import utils.HttpUtils;

@Controller
public class OpenApiDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(OpenApiDataController.class);

	private static final String NOTICE_URL = "http://120.92.101.137:8083/magnetic_striple_event";

	@RequestMapping(value = "/mofang/session")
	@ResponseBody
	public String getSession(){
		try {
			String sessionId = getMofangSessionId();
			return sessionId;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
	@RequestMapping(value = "/mofang/notice")
	@ResponseBody
	 public String sendNotice(){
	        try {
	            Map<String,String> map = new HashMap<String, String>();
	            map.put("magneticStripleId","0001171116000006");
	            map.put("status","EMPTY");
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            map.put("occurrenceTime",sdf.format(new Date()));
	            map.put("companyOrganId","10351");
	            map.put("storeOrganId","10352");
	            String jsonStr = JSON.toJSONString(map);
	            return HttpUtils.postMofangJson(getMofangSessionId(), NOTICE_URL,jsonStr);
	        }catch (Exception e){
	            log.error("error:{}",e);
	        }
	        return null;
	    }
}
