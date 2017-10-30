package main.entry.webapp.data.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import utils.Resp;
import utils.guodong.OpenApi;

@Controller
@RequestMapping(value = "/interface/gd")
public class InterfaceGuodongDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(InterfaceGuodongDataController.class);

//	private static final String[] devIds = new String[]{"0000000000004f90","0000000000004f91","0000000000004f92","0000000000004f93","0000000000004f94","0000000000004f95"};
	
	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> getData(){
		Resp<?> resp = new Resp<>(false);
		try {
			Date date = new Date();
			Calendar calendar=Calendar.getInstance();  
			calendar.setTime(date); 
			calendar.add(Calendar.MINUTE, -10);
			date = calendar.getTime();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			JSONObject jsonObject = OpenApi.getAppEuiDataListInfo("000000000000006c", simpleDateFormat.format(date));
			resp = new Resp<>(jsonObject);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
}
