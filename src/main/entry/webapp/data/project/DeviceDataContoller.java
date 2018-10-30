package main.entry.webapp.data.project;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import utils.HttpUtils;
import utils.MofangSignUtils;
import utils.Resp;
import utils.UUIDUtils;

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
						Map<String, String> map = new HashMap<String,String>();
						String uuid = UUIDUtils.random();
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						map.put("path", "/park/park_place");
						map.put("X-POS-REQUEST-ID",uuid);
						map.put("X-POS-REQUEST-TIME", sdf.format(date));
						map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
						map.put("magneticStripeId", mac);
						String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
						map.put("X-POS-REQUEST-SIGN", sign);
						JSONObject json1 = JSON.parseObject(HttpUtils.getMofangV2("/park/park_place?magneticStripeId="+mac,map));
						JSONArray jsonArray = JSON.parseArray(JSON.parseObject(json1.getString("data")).getString("parkPlaces"));
						if(jsonArray.size()<1){
							map = new HashMap<String,String>();
							map.put("code", desc);
							map.put("magneticStripeId", mac);
							map.put("storeOrganId",BaseConstant.BASE_STORE_ID);
							map.put("companyOrganId", BaseConstant.BASE_COMPANY_ID);
							map.put("status", "EMPTY");
							String jsonStr = JSONObject.toJSONString(map);
							uuid = UUIDUtils.random();
							date = new Date();
							map.put("path", "/park/park_place");
							map.put("X-POS-REQUEST-ID",uuid);
							map.put("X-POS-REQUEST-TIME", sdf.format(date));
							map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
							sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
							map.put("X-POS-REQUEST-SIGN", sign);
							HttpUtils.postJsonMofangV2("/park/park_place", map, jsonStr);
						}else{
							String parkId = jsonArray.getJSONObject(0).getString("parkPlaceId");
							map = new HashMap<String,String>();
							map.put("code", desc);
							map.put("magneticStripeId", mac);
							map.put("parkPlaceId",parkId);
							String jsonStr = JSONObject.toJSONString(map);
							uuid = UUIDUtils.random();
							date = new Date();
							map.put("path", "/park/park_place/update");
							map.put("X-POS-REQUEST-ID",uuid);
							map.put("X-POS-REQUEST-TIME", sdf.format(date));
							map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
							sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
							map.put("X-POS-REQUEST-SIGN", sign);
							HttpUtils.postJsonMofangV2("/park/park_place/update", map, jsonStr);
						}
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
