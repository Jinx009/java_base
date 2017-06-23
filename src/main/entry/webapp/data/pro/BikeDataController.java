package main.entry.webapp.data.pro;

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
import utils.HttpData;
import utils.Resp;
import utils.enums.AppInfo;

@Controller
@RequestMapping(value = "/home/d")
public class BikeDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(BikeDataController.class);

	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/bikes")
	@ResponseBody
	public Resp<?> car(String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getToken(AppInfo.PARKING_DEMO.getAppId());
			if(token!=null){
				String result = httpService.get(HttpData.bikeUrl(token,mac));
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(JSON.parseObject(result).getString("params")));
					return resp;
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	
}
