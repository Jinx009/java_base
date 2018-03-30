package main.entry.webapp.data;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import utils.Base64Utils;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/unicom/notice")
public class UnicomNoticeController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(UnicomNoticeController.class);

	private static final String REGISTER_NOTICE_URL = "http://223.167.110.4:8000/m2m/applications/registration";
	
	/**
	 * 电信数据上报
	 * @param pushData
	 * @return
	 */
	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> getNotice(@RequestBody String pushData) {
		Resp<?> resp = new Resp<>(true);
		log.warn("data:{}",pushData);
		return resp;
	}
	
	/**
	 * 修改上报地址
	 * @param data
	 * @return
	 */
	@RequestMapping(path = "/updateRegisterUrl")
	@ResponseBody
	public Resp<?> setNoticeUrl(@RequestBody Map<String,Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String url = getString(data, "url");
			String account = getString(data, "account");
			String password = getString(data, "password");
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> data1 = new HashMap<String, String>();
			data.put("authorization", "Basic " + Base64Utils.getEncodedBase64(account+":"+password));
			map.put("headers", data1);
			map.put("url",url);
			String msg = HttpUtils.putJson(REGISTER_NOTICE_URL, JSONObject.toJSONString(map));
			return new Resp<>(JSON.parseObject(msg));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	

}
