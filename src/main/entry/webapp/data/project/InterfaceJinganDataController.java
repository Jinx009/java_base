package main.entry.webapp.data.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.home.HomeUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;
import utils.Resp;
import utils.enums.AppInfo;

/**
 * 公用接口集散
 * 
 * @author jinx
 *
 */
@Controller
@RequestMapping(value = "/interface/ja")
public class InterfaceJinganDataController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceJinganDataController.class);

	@Autowired
	private HttpService httpService;
	
	@RequestMapping(path = "/session")
	@ResponseBody
	public Resp<?> session(HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
				HomeUser homeUser = getSessionHomeUser(request);
				if(homeUser!=null){
					return new Resp<>(true);
				}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	

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

	@RequestMapping(path = "/getLog")
	@ResponseBody
	public Resp<?> logs(String dateStr, String mac) {
		Resp<?> resp = new Resp<>("未找到相关日志！");
		try {
			String pathname = "/root/nb/heart_log/heart_log_" + dateStr + "_" + mac + ".txt";
			File fileName = new File(pathname);
			if (fileName.exists()) {
				return new Resp<>(getLog(fileName));
			} else {
				pathname = "/root/nb/heart_log/log/" + dateStr + "/heart_log_" + dateStr + "_"
						+ mac + ".txt";
				fileName = new File(pathname);
				if (fileName.exists()) {
					return new Resp<>(getLog(fileName));
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}
	
	private String getLog(File fileName){
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));
			BufferedReader br = new BufferedReader(reader);
			StringBuilder result = new StringBuilder();
			String s = null;
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			br.close();
			return result.toString();
		} catch (IOException e) {
			logger.error("error:{}",e);
		}
		return null;
	}

	@RequestMapping(path = "/locationStatus")
	@ResponseBody
	public Resp<?> locationStatus(Integer locationId, Integer op, String dateStr) {
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getToken(AppInfo.NB_JINGAN.getAppId());
			if (token != null) {
				String result = httpService.get(HttpData.locationStatusUrl(token, op, locationId, dateStr));
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

	@RequestMapping(path = "/device")
	@ResponseBody
	public Resp<?> device() {
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getToken(AppInfo.NB_JINGAN.getAppId());
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

	@RequestMapping(path = "/car")
	@ResponseBody
	public Resp<?> car(Integer areaId, String dateStr, Integer type) {
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getToken(AppInfo.NB_JINGAN.getAppId());
			if (token != null) {
				String result = httpService.get(HttpData.carUrl(token, areaId, dateStr, type));
				if (!BaseConstant.HTTP_ERROR_CODE.equals(result)) {
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG, JSONArray.parse(result));
					return resp;
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}

	/**
	 * 
	 * @param areaId
	 * @return
	 */
	@RequestMapping(path = "/view")
	@ResponseBody
	public Resp<?> views(Integer areaId) {
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getToken(AppInfo.NB_JINGAN.getAppId());
			if (token != null) {
				String result = httpService.get(HttpData.detail(token, areaId));
				if (!BaseConstant.HTTP_ERROR_CODE.equals(result)) {
					JSONObject jsonObject = JSON.parseObject(result);
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG,
							JSONArray.parse(jsonObject.getString(BaseConstant.PARAMS)));
					return resp;
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}

	/**
	 * 按星期占有率分析
	 * 
	 * @param dateStr
	 * @return
	 */
	@RequestMapping(path = "/rush")
	@ResponseBody
	public Resp<?> rush(String dateStr) {
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getToken(AppInfo.NB_JINGAN.getAppId());
			if (token != null) {
				String result = httpService.get(HttpData.jinganRush(token, dateStr));
				if (!BaseConstant.HTTP_ERROR_CODE.equals(result)) {
					JSONObject jsonObject = JSON.parseObject(result);
					resp = new Resp<>(BaseConstant.HTTP_OK_CODE, BaseConstant.HTTP_OK_MSG,
							JSONArray.parse(jsonObject.getString(BaseConstant.PARAMS)));
					return resp;
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}

}
