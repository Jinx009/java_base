package main.entry.webapp.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.IotCloudLog;
import database.models.vo.UnicomPushDataModel;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import utils.Base64Utils;
import utils.BaseConstant;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/unicom")
public class UnicomController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(UnicomController.class);
	
	@Autowired(required=false)
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired(required=false)
	private IotCloudLogService iotCloudLogService;

	/**
	 * 联通数据上报
	 * @param pushData
	 * @return
	 */
	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> getNotice(@RequestBody String pushData) {
		Resp<?> resp = new Resp<>(true);
		log.warn("data:{}",pushData);
		try {
			List<UnicomPushDataModel> list = JSONArray.parseArray(JSONObject.parseObject(pushData).getString("reports"),UnicomPushDataModel.class);
			if(list!=null&&!list.isEmpty()){
				for(UnicomPushDataModel unicomPushDataModel : list){
					IotCloudLog iotCloudLog = new IotCloudLog();
					iotCloudLog.setData(unicomPushDataModel.getValue());
					iotCloudLog.setFromSite("unicom");
					iotCloudLog.setImei(unicomPushDataModel.getSerialNumber());
					iotCloudLog.setType(0);
					iotCloudLog.setMac(iotCloudDeviceService.getByImei(unicomPushDataModel.getSerialNumber()));
					iotCloudLogService.save(iotCloudLog);
					send(unicomPushDataModel.getValue());
				}
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		send(pushData);
		return resp;
	}
	
	
	/**
	 * 上行设置
	 * @param account
	 * @param password
	 */
	public void updatePushMsg(){
		try {
			String jsonStr = "{" +
	                "  \"criteria\": {" +
	                "    \"serialNumbers\": [" +
	                "      \"863703031463248\"" +
	                "    ]" +
	                "  }," +
	                "  \"deletionPolicy\": 0," +
	                "  \"groupName\": \"DM.TEST.ZHANWAY\"," +
	                "  \"resources\": [" +
	                "    {" +
	                "      \"conditions\": {" +
	                "        \"pmin\": 0," +
	                "        \"steps\": 0" +
	                "      }," +
	                "      \"resourcePath\": \"uplinkMsg/0/data\"" +
	                "    }" +
	                "  ]," +
	                "  \"subscriptionType\": \"resources\"" +
	                "}";
			HttpUtils.putUnicomJson("http://223.167.110.4:8000/m2m/subscriptions?type=resources", jsonStr, "emhhbndheTpaaGFud2F5ITIz");
		} catch (Exception e) {
			System.out.println("error:{}"+e);
		}
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
			String msg = HttpUtils.putJson(BaseConstant.REGISTER_NOTICE_URL, JSONObject.toJSONString(map));
			return new Resp<>(JSON.parseObject(msg));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	

}
