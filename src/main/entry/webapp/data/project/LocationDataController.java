package main.entry.webapp.data.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/location")
public class LocationDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(LocationDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	/**
	 * 获取location列表
	 * @return
	 */
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> locations(){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getMyToken();
			if(token!=null){
				String result = httpService.get(HttpData.locationListeUrl(token));
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
	 * 获取location列表
	 * @return
	 */
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> locationUpdate(Integer id,String name,String desc){
		Resp<?> resp = new Resp<>(false);
		try {
			String token  = getMyToken();
			if(token!=null){
				String result = httpService.postParams(HttpData.locationUpdateUrl(token,id,name,desc));
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
	
}
