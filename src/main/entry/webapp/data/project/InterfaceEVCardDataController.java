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
import utils.enums.AppInfo;

@Controller
@RequestMapping(value = "/interface/evcard")
public class InterfaceEVCardDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(InterfaceEVCardDataController.class);

	@Autowired
	private HttpService httpService;

	@RequestMapping(path = "/inout")
	@ResponseBody
	public Resp<?> inout(String dateStr, Integer areaId) {
		Resp<?> resp = new Resp<>(false);
		try {
			String result = httpService.get(HttpData.inOutUrl(dateStr,areaId));
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
			logger.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/device")
	@ResponseBody
	public Resp<?> device() {
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getToken(AppInfo.EVCARD.getAppId());
			if (token != null) {
				String result = httpService.get(HttpData.deviceUrl(token));
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (!BaseConstant.HTTP_ERROR_CODE.equals(result)) {
					if (BaseConstant.HTTP_OK_CODE.equals(jsonObject.getString(BaseConstant.RESP_CODE))) {
						resp = new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG,
								jsonObject.get(BaseConstant.PARAMS));
					} else {
						resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE, jsonObject.getString(BaseConstant.MSG),
								jsonObject.getString(BaseConstant.PARAMS));
					}
					return resp;
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
}
