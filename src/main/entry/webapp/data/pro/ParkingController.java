package main.entry.webapp.data.pro;

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
import utils.enums.AppInfo;

@Controller
@RequestMapping(value = "/home/d")
public class ParkingController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);
	
	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/locationStatus")
	@ResponseBody
	public Resp<?> locationStatus(Integer locationId,Integer op,String dateStr){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getToken(AppInfo.PARKING_DEMO.getAppId());
			if(token!=null){
				String result = httpService.get(HttpData.locationStatusUrl(token,op,locationId,dateStr));
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
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/device")
	@ResponseBody
	public Resp<?> device(){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getToken(AppInfo.NB_JINGAN.getAppId());
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
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/car")
	@ResponseBody
	public Resp<?> car(Integer areaId,String dateStr,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getToken(AppInfo.COMPANY_TEST.getAppId());
			if(token!=null){
				String result = httpService.get(HttpData.carUrl(token,areaId,dateStr,type));
				if(!BaseConstant.HTTP_ERROR_CODE.equals(result)){
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,JSONArray.parse(result));
					return resp;
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/view")
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
