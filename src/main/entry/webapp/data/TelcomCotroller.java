package main.entry.webapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.IoTCloudDevice;
import database.models.IotCloudLog;
import database.models.vo.PushModel;
import database.models.vo.TModel;
import database.models.vo.TelcomPushDataModel;
import utils.Constant;
import utils.HttpUtils;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.MD5Util;
import utils.StreamClosedHttpResponse;

import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/telcom")
public class TelcomCotroller extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(TelcomCotroller.class);

	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired
	private IotCloudLogService iotCloudLogService;
	
	
	@RequestMapping(path = "/na/iocm/devNotify/v1.1.0/reportCmdExecResult",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> cmd(@RequestBody String r){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("notice msg:{}",r);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}",e);
		}
		return resp;
		
	}
	
	/**
	 * 设置上报地址
	 * @return
	 */
	@RequestMapping(path = "/setCallbakUrl",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> setCallUrl(){
		Resp<?> resp = new Resp<>(false);
		try {
	        HttpsUtil httpsUtil = new HttpsUtil();
	        httpsUtil.initSSLConfigForTwoWay();
	        String accessToken = login(httpsUtil);
	        String appId = Constant.APPID;
	        String urlSubscribe = Constant.SUBSCRIBE_NOTIFYCATION;
	        
	        String callbackurl_deviceDataChanged = Constant.DEVICE_DATA_CHANGED_CALLBACK_URL;
	        String notifyType_deviceDataChanged = Constant.DEVICE_DATA_CHANGED;

	        Map<String, Object> paramSubscribe_deviceDataChanged = new HashMap<>();
	        paramSubscribe_deviceDataChanged.put("notifyType", notifyType_deviceDataChanged);
	        paramSubscribe_deviceDataChanged.put("callbackurl", callbackurl_deviceDataChanged);

	        String jsonRequest_deviceDataChanged = JsonUtil.jsonObj2Sting(paramSubscribe_deviceDataChanged);

	        Map<String, String> header_deviceDataChanged = new HashMap<>();
	        header_deviceDataChanged.put(Constant.HEADER_APP_KEY, appId);
	        header_deviceDataChanged.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

	        HttpResponse httpResponse_deviceDataChanged = httpsUtil.doPostJson(urlSubscribe, header_deviceDataChanged, jsonRequest_deviceDataChanged);

	        String bodySubscribe_deviceDataChanged = httpsUtil.getHttpResponseBody(httpResponse_deviceDataChanged);
	        log.warn("msg:{}",bodySubscribe_deviceDataChanged);
	        
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}",e);
		}
		return resp;
		
	}

	/**
	 * 电信上报数据
	 * @param r
	 * @return
	 */
	@RequestMapping(path = "/na/iocm/devNotify/v1.1.0/updateDeviceDatas")
	@ResponseBody
	public Resp<?> notice(@RequestBody String r){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("notice test msg:{}",r);
//			TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r,TelcomPushDataModel.class);
//			if(telcomPushDataModel!=null){
//				List<PushModel> list = telcomPushDataModel.getServices();
//				if(list!=null&&!list.isEmpty()){
//					for(PushModel pushModel : list){
//						TModel tModel = pushModel.getData();
//						IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByDeviceId(telcomPushDataModel.getDeviceId());
//						IotCloudLog iotCloudLog = new IotCloudLog();
//						iotCloudLog.setData(tModel.getData());
//						iotCloudLog.setFromSite("telcom");
//						iotCloudLog.setCreateTime(new Date());
//						iotCloudLog.setImei(ioTCloudDevice.getImei());
//						iotCloudLog.setType(0);
//						iotCloudLog.setMac(ioTCloudDevice.getMac());
//						iotCloudLogService.save(iotCloudLog);
//						send(tModel.getData());
//					}
//				}
//			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}",e);
		}
		return resp;
		
	}
	
	@RequestMapping(path = "/notice/na/iocm/devNotify/v1.1.0/updateDeviceDatas")
	@ResponseBody
	public Resp<?> noticeN(@RequestBody String r){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("notice msg:{}",r);
			TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r,TelcomPushDataModel.class);
			if(telcomPushDataModel!=null){
				List<PushModel> list = telcomPushDataModel.getServices();
				PushModel pushModel2 = telcomPushDataModel.getService();
				if(list!=null&&!list.isEmpty()){
					for(PushModel pushModel : list){
						TModel tModel = pushModel.getData();
						IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByDeviceId(telcomPushDataModel.getDeviceId());
						IotCloudLog iotCloudLog = new IotCloudLog();
						iotCloudLog.setData(tModel.getData());
						iotCloudLog.setFromSite("telcom");
						iotCloudLog.setCreateTime(new Date());
						iotCloudLog.setImei(ioTCloudDevice.getImei());
						iotCloudLog.setType(0);
						iotCloudLog.setMac(ioTCloudDevice.getMac());
						iotCloudLogService.save(iotCloudLog);
						if(ioTCloudDevice.getLocalIp()!=null&&ioTCloudDevice.getLocalIp().equals("SM_CZ")){
							String _s = "content="+tModel.getData()+"&key=gdzxxxkjgfyxgs9981n";
							HttpUtils.get("http://zhxf.gdzxkj.net:8003/api/devices_get_single_info?sign="+MD5Util.toMD5(_s).toLowerCase()+"&"+_s);
						}else if(ioTCloudDevice.getLocalIp()!=null&&ioTCloudDevice.getLocalIp().equals("QJ")){
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/push?data="+tModel.getData());
						}else if(ioTCloudDevice.getLocalIp()!=null&&ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY")){
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data="+tModel.getData());
						}else{
							send(tModel.getData(),ioTCloudDevice.getUdpIp(),ioTCloudDevice.getUdpPort());
						}
					}
				}
				if(pushModel2!=null){
					TModel tModel = pushModel2.getData();
					IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByDeviceId(telcomPushDataModel.getDeviceId());
					IotCloudLog iotCloudLog = new IotCloudLog();
					iotCloudLog.setData(tModel.getData());
					iotCloudLog.setFromSite("telcom");
					iotCloudLog.setCreateTime(new Date());
					iotCloudLog.setImei(ioTCloudDevice.getImei());
					iotCloudLog.setType(0);
					iotCloudLog.setMac(ioTCloudDevice.getMac());
					iotCloudLogService.save(iotCloudLog);
					if(ioTCloudDevice.getLocalIp()!=null&&ioTCloudDevice.getLocalIp().equals("SM_CZ")){
						String _s = "content="+tModel.getData()+"&key=gdzxxxkjgfyxgs9981n";
						HttpUtils.get("http://zhxf.gdzxkj.net:8003/api/devices_get_single_info?sign="+MD5Util.toMD5(_s).toLowerCase()+"&"+_s);
					}else if(ioTCloudDevice.getLocalIp()!=null&&ioTCloudDevice.getLocalIp().equals("QJ")){
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/push?data="+tModel.getData());
					}else if(ioTCloudDevice.getLocalIp()!=null&&ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY")){
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data="+tModel.getData());
					}else{
						send(tModel.getData(),ioTCloudDevice.getUdpIp(),ioTCloudDevice.getUdpPort());
					}
				}
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}",e);
		}
		return resp;
		
	}
	
	/**
	 * 设备注册
	 * @param imei
	 * @param mac
	 * @param ipLocal
	 * @return
	 */
	@RequestMapping(path = "/register")
	@ResponseBody
	public Resp<?> register(String imei,String mac,String ipLocal,String name){
		Resp<?> resp = new Resp<>(false);
		try {
	        HttpsUtil httpsUtil = new HttpsUtil();
	        httpsUtil.initSSLConfigForTwoWay();
	        String accessToken = login(httpsUtil);
			String appId = Constant.APPID;
			String urlReg = Constant.REGISTER_DEVICE;
	        String verifyCode = imei;   
			String nodeId = verifyCode;
	        Integer timeout = 0;
	        Map<String, Object> paramReg = new HashMap<>();
	        paramReg.put("verifyCode", verifyCode.toUpperCase());
	        paramReg.put("nodeId", nodeId.toUpperCase());
	        paramReg.put("timeout", timeout);
	        String jsonRequest = JsonUtil.jsonObj2Sting(paramReg);
	        Map<String, String> header = new HashMap<>();
	        header.put(Constant.HEADER_APP_KEY, appId);
	        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
	        StreamClosedHttpResponse responseReg = httpsUtil.doPostJsonGetStatusLine(urlReg, header, jsonRequest);
	        JSONObject jsonObject = JSONObject.parseObject(responseReg.getContent());
	        log.warn("msg:{}",jsonObject);
	        String deviceId = jsonObject.getString("deviceId");
	        httpsUtil = new HttpsUtil();
	        httpsUtil.initSSLConfigForTwoWay();
	        accessToken = login(httpsUtil);
	        String urlModifyDeviceInfo = Constant.MODIFY_DEVICE_INFO + "/" + deviceId;
	        String manufacturerId= "Zhanway";
	        String manufacturerName = "Zhanway";
	        String deviceType = "SmartDevice";
	        String model = "ZWMNB01";
	        String protocolType = "CoAP";
	        Map<String, Object> paramModifyDeviceInfo = new HashMap<>();
	        paramModifyDeviceInfo.put("manufacturerId", manufacturerId);
	        paramModifyDeviceInfo.put("manufacturerName", manufacturerName);
	        paramModifyDeviceInfo.put("deviceType", deviceType);
	        paramModifyDeviceInfo.put("model", model);
	        paramModifyDeviceInfo.put("name", name+"_"+imei);
	        paramModifyDeviceInfo.put("protocolType", protocolType);
	        String jsonRequest2 = JsonUtil.jsonObj2Sting(paramModifyDeviceInfo);
	        Map<String, String> header2 = new HashMap<>();
	        header.put(Constant.HEADER_APP_KEY, appId);
	        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
	        StreamClosedHttpResponse responseModifyDeviceInfo = httpsUtil.doPutJsonGetStatusLine(urlModifyDeviceInfo, header2, jsonRequest2);
	        log.warn("msg:{}",responseModifyDeviceInfo.getContent());
	        IoTCloudDevice ioTCloudDevice = new IoTCloudDevice();
	        ioTCloudDevice.setCreateTime(new Date());
	        ioTCloudDevice.setImei(imei);
	        ioTCloudDevice.setLocalIp(ipLocal);
	        ioTCloudDevice.setMac(mac);
	        ioTCloudDevice.setType(1);
	        ioTCloudDevice.setDeviceId(deviceId);
	        iotCloudDeviceService.save(ioTCloudDevice);
	        return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	
}
