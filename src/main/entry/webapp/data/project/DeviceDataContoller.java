package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/device")
public class DeviceDataContoller extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(DeviceDataContoller.class);

	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/sensor")
	@ResponseBody
	public Resp<?> sensor(){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getMyToken();
			if(token!=null){
				String result = httpService.get(HttpData.deviceUrl(token));
				JSONObject jsonObject = JSONObject.parseObject(result);
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					if(BaseConstant.HTTP_OK_CODE.equals(jsonObject.getString(BaseConstant.RESP_CODE))){
						resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,jsonObject.get(BaseConstant.PARAMS));
					}else{
						resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,jsonObject.getString(BaseConstant.MSG),jsonObject.getString(BaseConstant.PARAMS));
					}
					return resp;
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/sensor_update")
	@ResponseBody
	public Resp<?> sensorUpdate(String mac,String desc){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getMyToken();
			if(token!=null){
				String result = httpService.get(HttpData.deviceUpdateUrl(token,mac,desc));
				JSONObject jsonObject = JSONObject.parseObject(result);
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					if(BaseConstant.HTTP_OK_CODE.equals(jsonObject.getString(BaseConstant.RESP_CODE))){
						resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,jsonObject.get(BaseConstant.PARAMS));
					}else{
						resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,jsonObject.getString(BaseConstant.MSG),jsonObject.getString(BaseConstant.PARAMS));
					}
					return resp;
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	
	/**
	 * pos列表
	 * @return
	 */
	@RequestMapping(path = "/pos")
	@ResponseBody
	public Resp<?> car(){
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get(HttpData.posUrl());
			resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSON.parse(result));
			return resp;
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	
	
	
	
	
}
