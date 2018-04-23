package main.entry.webapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;

import database.models.IoTCloudDevice;
import database.models.IotCloudLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import utils.BaseConstant;
import utils.Constant;
import utils.HttpUtils;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.Resp;

@Controller
@RequestMapping(value = "/job")
public class JobController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired
	private IotCloudLogService iotCloudLogService;

	@RequestMapping(path = "/send")
	@ResponseBody
	public Resp<?> job(@RequestBody Map<String, Object> data) {
		Resp<?> resp = new Resp<>(false);
		try {
			String mac = getString(data, "mac");
			String data1 = getString(data, "data");
			IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByMac(mac);
			if (ioTCloudDevice.getType() == 1) {
				String msg = HttpUtils.putUnicomJson(BaseConstant.getSendUrl(ioTCloudDevice.getImei()),
						"{\"resourceValue\":\"" + data1 + "\"}\"", "emhhbndheTpaaGFud2F5ITIz");
				IotCloudLog iotCloudLog = new IotCloudLog();
				iotCloudLog.setData(data1);
				iotCloudLog.setFromSite("unicom");
				iotCloudLog.setImei(ioTCloudDevice.getImei());
				iotCloudLog.setMac(ioTCloudDevice.getMac());
				iotCloudLog.setType(1);
				iotCloudLog.setCreateTime(new Date());
				iotCloudLogService.save(iotCloudLog);
				log.warn("msg:{}", msg);
			} else if (ioTCloudDevice.getType() == 2) {
				HttpsUtil httpsUtil = new HttpsUtil();
				httpsUtil.initSSLConfigForTwoWay();
				String accessToken = login(httpsUtil);
				String urlPostAsynCmd = Constant.POST_ASYN_CMD;
				String appId = Constant.APPID;
				String deviceId = ioTCloudDevice.getDeviceId();
				String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;
				String serviceId = "data";
				String method = "command";
				ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"CMD_DATA\":\"" + data1 + "\"}");
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
				IotCloudLog iotCloudLog = new IotCloudLog();
				iotCloudLog.setData(data1);
				iotCloudLog.setFromSite("telcom");
				iotCloudLog.setCreateTime(new Date());
				iotCloudLog.setImei(ioTCloudDevice.getImei());
				iotCloudLog.setMac(ioTCloudDevice.getMac());
				iotCloudLog.setType(1);
				iotCloudLogService.save(iotCloudLog);
				HttpResponse responsePostAsynCmd = httpsUtil.doPostJson(urlPostAsynCmd, header, jsonRequest);
				String responseBody = httpsUtil.getHttpResponseBody(responsePostAsynCmd);
				log.warn("msg:{}", responseBody);
			}else if (ioTCloudDevice.getType() == 3) {//loraWan
				String  msg = HttpUtils.get("https://api-smg.iot.cn/thingpark/lrc/rest/downlink?DevEUI="+mac+"&FPort=1&Payload="+data1+"&FCntDn=1234");
				IotCloudLog iotCloudLog = new IotCloudLog();
				iotCloudLog.setData(data1);
				iotCloudLog.setFromSite("lorawan");
				iotCloudLog.setImei("");
				iotCloudLog.setMac(ioTCloudDevice.getMac());
				iotCloudLog.setType(1);
				iotCloudLog.setCreateTime(new Date());
				iotCloudLogService.save(iotCloudLog);
				log.warn("msg:{}", msg);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

}
