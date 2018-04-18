package main.entry.webapp.data.fireControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/fireControl")
public class FireControlDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(FireControlDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> data(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get("http://wx.zhanway.com/gtw/rest/sensors/byAppId?appId=bearhunting");
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(JSON.parseObject(result).getString("params")));
			return resp;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(String dateStr){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get("http://wx.zhanway.com/gtw/rest/sensor/inOutLog?areaId=34&dateStr="+dateStr);
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(JSON.parseObject(result).getString("params")));
			return resp;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
