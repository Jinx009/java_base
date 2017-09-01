package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.WebTokenFactory;
import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import service.redis.RedisService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;
import utils.enums.AppInfo;

@Controller
public class RedisController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(RedisController.class);
	
	@Autowired
	private HttpService httpService;
	@Autowired(required = false)
	private RedisService redisService;
	
	@RequestMapping(value = "redis/get")
	@ResponseBody
	public WebTokenFactory getWebTokenFactory(@RequestParam(value = "key",required = false)String key){
		WebTokenFactory webTokenFactory = (WebTokenFactory) redisService.getObj(key);
		return webTokenFactory;
	}
	

	
	@RequestMapping(value = "/home/u/view")
	@ResponseBody
	public Resp<?> views(Integer areaId){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getToken(AppInfo.PARKING_DEMO.getAppId());
			if(token!=null){
				String result = httpService.get(HttpData.detail(token, areaId));
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					JSONObject jsonObject = JSON.parseObject(result);
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(jsonObject.getString(BaseConstant.PARAMS)));
					return resp;
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
}
