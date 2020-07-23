package main.entry.webapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.models.IoTCloudDevice;
import database.models.IotCloudLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.Resp;
import utils.StringUtil;

@Controller
@RequestMapping(value = "/weapp/d")
public class WeappDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(WeappDataController.class);
	
	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired
	private IotCloudLogService iotCloudLogService;
	
	/**
	 * 微信小程序给倾角传感器校准
	 * @param mac
	 * @param lng
	 * @param lat
	 * @param parkName
	 * @param secret
	 * @return
	 */
	@RequestMapping(path = "/new")
	@ResponseBody
	public  Resp<?> saveNew(String mac,double lng,double lat,String parkName,String secret){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(mac)){
				resp.setMsg("mac号不能为空！");
				return resp;
			}
			IoTCloudDevice device = iotCloudDeviceService.findByMac(mac);
			if(device==null){
				resp.setMsg("设备尚未注册！");
				return resp;
			}
			if(!secret.equals(device.getSecret())){
				resp.setMsg("秘钥不匹配！");
				return resp;
			}
			device.setParkName(parkName);
			device.setIsCorrect(-1);
			device.setLng(lng);
			device.setLat(lat);
			iotCloudDeviceService.update(device);
			IotCloudLog iotCloudLog = new IotCloudLog();
			iotCloudLog.setCmdName("校准");
			iotCloudLog.setCmdType(1);
			iotCloudLog.setCreateTime(new Date());
			iotCloudLog.setFromSite("telcom");
			iotCloudLog.setImei(device.getImei());
			iotCloudLog.setMac(mac);
			iotCloudLog.setStatus(0);
			iotCloudLog.setType(1);
			iotCloudLog = iotCloudLogService.saveA(iotCloudLog);
			String sn =  getMore(Integer.toHexString(iotCloudLog.getId()));
			String data =  "48003600"+sn;
			iotCloudLog.setData(data);
			sendCmd(iotCloudLog, device);
			iotCloudLogService.update(iotCloudLog);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取同一秘钥下所有设备列表
	 * @param secret
	 * @return
	 */
	@RequestMapping(path = "devices")
	@ResponseBody
	public Resp<?> getDevices(String secret){
		Resp<?> resp = new Resp<>(false);
		try {
			if(StringUtil.isBlank(secret)){
				return resp;
			}
			List<IoTCloudDevice> list = iotCloudDeviceService.findBySecret(secret);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取失联的设备列表
	 * @return
	 */
	@RequestMapping(path = "lost")
	@ResponseBody
	public Resp<?> getLost(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.getBroken());
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	private  void sendCmd(IotCloudLog iotCloudLog,IoTCloudDevice ioTCloudDevice){
		try {
			HttpsUtil httpsUtil = new HttpsUtil();
			httpsUtil.initSSLConfigForTwoWay();
			String accessToken = login(httpsUtil);
			String urlPostAsynCmd = Constant.POST_ASYN_CMD;
			String appId = Constant.APPID;
			String deviceId = ioTCloudDevice.getDeviceId();
			String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;
			String serviceId = "data";
			String method = "command";
			ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"CMD_DATA\":\"" + iotCloudLog.getData() + "\"}");
			Map<String, Object> paramCommand = new HashMap<>();
			paramCommand.put("serviceId", serviceId);
			paramCommand.put("method", method);
			paramCommand.put("paras", paras);
			Map<String, Object> paramPostAsynCmd = new HashMap<>();
			paramPostAsynCmd.put("deviceId", deviceId);
			paramPostAsynCmd.put("command", paramCommand);
			paramPostAsynCmd.put("callbackUrl", callbackUrl);
			String jsonRequest = JsonUtil.jsonObj2Sting(paramPostAsynCmd);
			Map<String, String> header = new HashMap<>();
			header.put(Constant.HEADER_APP_KEY, appId);
			header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
			HttpResponse responsePostAsynCmd = httpsUtil.doPostJson(urlPostAsynCmd, header, jsonRequest);
			String responseBody = httpsUtil.getHttpResponseBody(responsePostAsynCmd);
			log.warn("msg:{}", responseBody);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
	}

	private static String getMore(String hexString) {
		hexString = hexString.toUpperCase();
		if(hexString.length()==1){
			return "0000000"+hexString;
		}
		if(hexString.length()==2){
			return "000000"+hexString;
		}
		if(hexString.length()==3){
			return "00000"+hexString;
		}
		if(hexString.length()==4){
			return "0000"+hexString;
		}
		if(hexString.length()==5){
			return "000"+hexString;
		}
		if(hexString.length()==6){
			return "00"+hexString;
		}
		if(hexString.length()==7){
			return "0"+hexString;
		}
		return hexString;
	}


	
}
