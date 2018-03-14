package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/parking_data")
public class ParkingDataDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ParkingDataDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/rush")
	@ResponseBody
	public Resp<?> locationStatus(Integer op,String dateStr){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getMyToken();
			if(token!=null){
				String result = httpService.get(HttpData.locationStatusUrl(token,op,dateStr));
				JSONObject jsonObject = JSONObject.parseObject(result);
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,jsonObject.get(BaseConstant.PARAMS));
					return resp;
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/rate")
	@ResponseBody
	public Resp<?> rush(String dateStr){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getMyToken();
			if(token!=null){
				String result = httpService.get(HttpData.appRush(token,dateStr));
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					JSONObject jsonObject = JSON.parseObject(result);
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(jsonObject.getString(BaseConstant.PARAMS)));
					return resp;
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/inOut")
	@ResponseBody
	public Resp<?> inout(String dateStr,String mac) {
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get(HttpData.inOutUrl(dateStr,mac));
			JSONObject jsonObject = JSONObject.parseObject(result);
			if (!BaseConstant.HTTP_ERROR_CODE.equals(result)) {
				if ("00".equals(jsonObject.getString(BaseConstant.RESP_CODE))) {
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG,
							jsonObject.get(BaseConstant.PARAMS));
				} else {
					resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE, jsonObject.getString(BaseConstant.MSG),
							jsonObject.getString(BaseConstant.PARAMS));
				}
				return resp;
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	

	@RequestMapping(path = "/time")
	@ResponseBody
	public Resp<?> views(String dateStr,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getMyToken();
			if(token!=null){
				String result = httpService.get(HttpData.carUrl(token,dateStr,type));
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(result));
					return resp;
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	
}
