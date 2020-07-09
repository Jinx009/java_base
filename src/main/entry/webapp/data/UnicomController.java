package main.entry.webapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import database.models.IoTCloudDevice;
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
	@RequestMapping(path = "/notice/data")
	@ResponseBody
	public Resp<?> getNotice(@RequestBody String pushData) {
		Resp<?> resp = new Resp<>(true);
		log.warn("data:{}",pushData);
		try {
			List<UnicomPushDataModel> list = JSONArray.parseArray(JSONObject.parseObject(pushData).getString("reports"),UnicomPushDataModel.class);
			if(list!=null&&!list.isEmpty()){
				for(UnicomPushDataModel unicomPushDataModel : list){
					IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByMac(iotCloudDeviceService.getByImei(unicomPushDataModel.getSerialNumber()));
					IotCloudLog iotCloudLog = new IotCloudLog();
					iotCloudLog.setData(unicomPushDataModel.getValue());
					iotCloudLog.setFromSite("unicom");
					iotCloudLog.setImei(ioTCloudDevice.getImei());
					iotCloudLog.setType(0);
					iotCloudLog.setCreateTime(new Date());
					iotCloudLog.setMac(ioTCloudDevice.getMac());
					iotCloudLogService.save(iotCloudLog);
					send(unicomPushDataModel.getValue(),ioTCloudDevice.getUdpIp(),ioTCloudDevice.getUdpPort());
				}
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
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
	
	public static void main(String[] args) {
//		String sn = "0009200050800003";
//		String acc_x = "-8";
//		String acc_y ="33";
//		String acc_z = "23";
//		String x = "44";
//		String y = "16";
//		System.out.println(sn.indexOf("0508"));
//		if(sn.indexOf("0508")>-1){
//			Double acc_x_d = Double.valueOf(acc_x);
//			Double acc_y_d = Double.valueOf(acc_y);
//			Double acc_z_d = Double.valueOf(acc_z);
//			Double x_d = Double.valueOf(x);
//			Double y_d = Double.valueOf(y);
//			if(Math.abs(acc_x_d)>=2.5||Math.abs(acc_y_d)>=2.5){
//				int ran = new Random().nextInt(200)-100;
//				double random = 0.00;
//				System.out.println(ran);
//				random =Double.valueOf(ran)/100-1.40;
//				if(ran>0){
//					random = Double.valueOf(ran)/100+1.40;
//				}
//				System.out.println(random);
//				acc_x = String.valueOf(random);
//				acc_y = String.valueOf(acc_y_d/(acc_x_d/random));
//				acc_z = String.valueOf(acc_z_d/(acc_x_d/random));
//				x = String.valueOf(x_d/(acc_x_d/random));
//				y = String.valueOf(y_d/(acc_x_d/random));
//			}
//		}
		System.out.println(Math.abs(Double.valueOf("6.61")));
	}
}
