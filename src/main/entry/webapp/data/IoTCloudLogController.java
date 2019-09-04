package main.entry.webapp.data;

import java.util.Date;
import java.util.HashMap;
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

@Controller
@RequestMapping(value = "/iot/log")
public class IoTCloudLogController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(IoTCloudLogController.class);
	
	@Autowired
	private IotCloudLogService iotCloudLogService;
	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p,Integer type,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudLogService.pageList(p,type,mac));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/typeList")
	@ResponseBody
	public Resp<?> typeList(Integer p,Integer type,String mac,String fromSite){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudLogService.pageList(p,type,mac,fromSite));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/cmdList")
	@ResponseBody
	public Resp<?> cmdList(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudLogService.cmdList(p));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	public static void main(String[] args) {
		String s = "001E2FE4".substring(0, 8);
		System.out.println(Long.parseLong(s, 16));
	}
	
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String mac,String value1,String value2,Integer cmdName){
		Resp<?> resp = new Resp<>(false);
		try {
			IoTCloudDevice device = iotCloudDeviceService.findByMac(mac);
			String name = getName(cmdName);
			IotCloudLog iotCloudLog = new IotCloudLog();
			iotCloudLog.setCmdName(name);
			iotCloudLog.setCmdType(1);
			iotCloudLog.setCreateTime(new Date());
			iotCloudLog.setFromSite("telcom");
			iotCloudLog.setImei(device.getImei());
			iotCloudLog.setMac(mac);
			iotCloudLog.setStatus(0);
			iotCloudLog.setType(1);
			iotCloudLog = iotCloudLogService.saveA(iotCloudLog);
			String sn =  getMore(Integer.toHexString(iotCloudLog.getId()));
			String data = "";
			if(1==cmdName){
				data = "48003600"+sn;
			}
			if(2==cmdName){
				data = "48003200"+sn;
			}
			if(3==cmdName){
				String ax = FloatToHexString(Float.valueOf(value1.split(",")[0]));
				String ay = FloatToHexString(Float.valueOf(value1.split(",")[1]));
				String az = FloatToHexString(Float.valueOf(value1.split(",")[2]));
				String x = FloatToHexString(Float.valueOf(value2.split(",")[0]));
				String y = FloatToHexString(Float.valueOf(value2.split(",")[1]));
				String z = FloatToHexString(Float.valueOf(value2.split(",")[2]));
				data = "48007B0501"+ax+ay+az+x+y+z+sn;
			}
			if(4==cmdName){
				data = "48007A0201"+value1+  sn;
			}
			if(5==cmdName){
				data = "48003901"+value1+ sn;
			}
			iotCloudLog.setData(data);
			sendCmd(iotCloudLog, device);
			iotCloudLogService.update(iotCloudLog);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 浮点数按IEEE754标准转16进制字符串
	 * @param f
	 * @return
	 */
	public  String FloatToHexString(float f){
		int i  = Float.floatToIntBits(f);
        String str = Integer.toHexString(i).toUpperCase();
        return str;
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

	private String getName(Integer cmdName) {
		if(1==cmdName){
			return "重启";
		}
		if(2==cmdName){
			return "校准";
		}
		if(3==cmdName){
			return "设置阈值";
		}
		if(4==cmdName){
			return "设置采样频率";
		}
		if(5==cmdName){
			return "开启关闭温度上报";
		}
		return null;
	}
	
}
