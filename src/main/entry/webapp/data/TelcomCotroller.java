package main.entry.webapp.data;

import java.net.DatagramSocket;
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
public class TelcomCotroller extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TelcomCotroller.class);

	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired
	private IotCloudLogService iotCloudLogService;

	@RequestMapping(path = "/na/iocm/devNotify/v1.1.0/reportCmdExecResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> cmd(@RequestBody String r) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("notice msg:{}", r);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	/**
	 * 设置上报地址
	 * 
	 * @return
	 */
	@RequestMapping(path = "/setCallbakUrl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> setCallUrl() {
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

			HttpResponse httpResponse_deviceDataChanged = httpsUtil.doPostJson(urlSubscribe, header_deviceDataChanged,
					jsonRequest_deviceDataChanged);

			String bodySubscribe_deviceDataChanged = httpsUtil.getHttpResponseBody(httpResponse_deviceDataChanged);
			log.warn("msg:{}", bodySubscribe_deviceDataChanged);

			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	/**
	 * 电信上报数据
	 * 
	 * @param r
	 * @return
	 */
	@RequestMapping(path = "/na/iocm/devNotify/v1.1.0/updateDeviceDatas")
	@ResponseBody
	public Resp<?> notice(@RequestBody String r) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("notice test msg:{}", r);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	@RequestMapping(path = "/udpServer")
	@ResponseBody
	public Resp<?> udpServer() {
		try {
			DatagramSocket socket = new DatagramSocket(7777);
			UDPServerThread st = new UDPServerThread(socket);
			st.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(path = "/notice/na/iocm/devNotify/v1.1.0/updateDeviceDatas")
	@ResponseBody
	public Resp<?> noticeN(@RequestBody String r) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("notice msg:{}", r);
			TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r, TelcomPushDataModel.class);
			if (telcomPushDataModel != null) {
				List<PushModel> list = telcomPushDataModel.getServices();
				PushModel pushModel2 = telcomPushDataModel.getService();
				if (list != null && !list.isEmpty()) {
					for (PushModel pushModel : list) {
						TModel tModel = pushModel.getData();
						IoTCloudDevice ioTCloudDevice = iotCloudDeviceService
								.findByDeviceId(telcomPushDataModel.getDeviceId());
						IotCloudLog iotCloudLog = new IotCloudLog();
						iotCloudLog.setData(tModel.getData());
						iotCloudLog.setFromSite("telcom");
						iotCloudLog.setCreateTime(new Date());
						iotCloudLog.setImei(ioTCloudDevice.getImei());
						iotCloudLog.setType(0);
						iotCloudLog.setMac(ioTCloudDevice.getMac());
						iotCloudLogService.save(iotCloudLog);
						if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("SM_CZ")) {
							String _s = "content=" + tModel.getData() + "&key=gdzxxxkjgfyxgs9981n";
							HttpUtils.get("http://zhxf.gdzxkj.net:8003/api/devices_get_single_info?sign="
									+ MD5Util.toMD5(_s).toLowerCase() + "&" + _s);
						} else if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("QJ")) {
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/push?data=" + tModel.getData());
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY")) {
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
							sendWuhanQj(ioTCloudDevice, iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_BJ")) {
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
							sendBeijingQj(ioTCloudDevice, iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_1.0_CZ")) {
							HttpUtils.get(
									"http://app.zhanway.com/home/cloud/qj/zhanway/push/1_0?data=" + tModel.getData());
							sendChaozhou(iotCloudLog.getData());
						} else {
							send(tModel.getData(), ioTCloudDevice.getUdpIp(), ioTCloudDevice.getUdpPort());
						}
					}
				}
				if (pushModel2 != null) {
					TModel tModel = pushModel2.getData();
					IoTCloudDevice ioTCloudDevice = iotCloudDeviceService
							.findByDeviceId(telcomPushDataModel.getDeviceId());
					IotCloudLog iotCloudLog = new IotCloudLog();
					iotCloudLog.setData(tModel.getData());
					iotCloudLog.setFromSite("telcom");
					iotCloudLog.setCreateTime(new Date());
					iotCloudLog.setImei(ioTCloudDevice.getImei());
					iotCloudLog.setType(0);
					iotCloudLog.setMac(ioTCloudDevice.getMac());
					iotCloudLogService.save(iotCloudLog);
					if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("SM_CZ")) {
						String _s = "content=" + tModel.getData() + "&key=gdzxxxkjgfyxgs9981n";
						HttpUtils.get("http://zhxf.gdzxkj.net:8003/api/devices_get_single_info?sign="
								+ MD5Util.toMD5(_s).toLowerCase() + "&" + _s);
					} else if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("QJ")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/push?data=" + tModel.getData());
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
						sendWuhanQj(ioTCloudDevice, iotCloudLog);
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_BJ")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
						sendBeijingQj(ioTCloudDevice, iotCloudLog);
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_1.0_CZ")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/1_0?data=" + tModel.getData());
						sendChaozhou(iotCloudLog.getData());
					} else {
						send(tModel.getData(), ioTCloudDevice.getUdpIp(), ioTCloudDevice.getUdpPort());
					}
				}
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	private void sendBeijingQj(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> warning = new HashMap<String, Object>();
		Map<String, Object> monitoring = new HashMap<String, Object>();
		String data = iotCloudLog.getData();
		try {
			map.put("voltage", getData(data.substring(48, 49), data.substring(48, 52)));
			warning.put("acc_x_type", Integer.valueOf(data.substring(22, 24)));
			warning.put("acc_y_type", Integer.valueOf(data.substring(28, 30)));
			warning.put("acc_z_type", Integer.valueOf(data.substring(34, 36)));
			warning.put("x_type", Integer.valueOf(data.substring(40, 42)));
			warning.put("y_type", Integer.valueOf(data.substring(46, 48)));
			monitoring.put("x", Double.valueOf(getData(data.substring(36, 37), data.substring(36, 40))));
			monitoring.put("y", Double.valueOf(getData(data.substring(42, 43), data.substring(42, 46))));
			monitoring.put("acc_x", Double.valueOf(getData(data.substring(18, 19), data.substring(18, 22))));
			monitoring.put("acc_y", Double.valueOf(getData(data.substring(24, 25), data.substring(24, 28))));
			monitoring.put("acc_z", Double.valueOf(getData(data.substring(30, 31), data.substring(30, 34))));
			map.put("warning", warning);
			map.put("monitoring", monitoring);
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			String url = "http://" + device.getUdpIp() + device.getSimCard().split("_")[0] + "/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postBeijingQjJson(url, json, device.getSimCard().split("_")[1]);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

	private void sendChaozhou(String data) {
		String type = data.substring(16, 18);
		if (type.equals("68")) {
			type = "报警";
		} else {
			type = "心跳";
		}
		try {
			String acc_x = getDataBase(data.substring(18, 19), data.substring(18, 22)) + "/10000";
			String acc_y = getDataBase(data.substring(24, 25), data.substring(24, 28)) + "/10000";
			String acc_z = getDataBase(data.substring(30, 31), data.substring(30, 34)) + "/10000";
			Integer acc_x_type = Integer.valueOf(data.substring(22, 24));
			Integer acc_y_type = Integer.valueOf(data.substring(28, 30));
			Integer acc_z_type = Integer.valueOf(data.substring(34, 36));
			String sn = data.substring(0, 16);
			String x = getData100(data.substring(36, 37), data.substring(36, 40));
			String y = getData100(data.substring(42, 43), data.substring(42, 46));
			Integer x_type = Integer.valueOf(data.substring(40, 42));
			Integer y_type = Integer.valueOf(data.substring(46, 48));
			String bat = getData(data.substring(48, 49), data.substring(48, 52));
			String sign = "gdzxxxkjgfyxgs9981n";
			String tem = "";
			if ("心跳".equals(type)) {
				tem = getData100(data.substring(52, 53), data.substring(52, 56));
			}
			String rssi = data.substring(56, 58);
			String params = "type="+type+"&acc_x="+acc_x+"&acc_y="+acc_y+"&acc_z="+acc_z+"&acc_x_type="+acc_x_type+"&acc_y_type="+acc_y_type+"&acc_z_type="+acc_z_type+
					"&sn="+sn+"&x="+x+"&y="+y+"&x_type="+x_type+"&y_type="+y_type+"&bat="+bat+"&tem="+tem+"&rssi="+rssi;
			String SIGN = MD5Util.toMD5(params+sign);
			params = params+"&sign="+SIGN;
			HttpUtils.sendPost("http://zhxftest.gdzxkj.net/tiltSensor", params);
		} catch (Exception e) {
			log.error("error:{}",e);
		}

	}
	
	public static void main(String[] args) {
		new TelcomCotroller().sendChaozhou("000118112100000969000D00FFDB00FFE6000006000015000BE707D61FFDF500D800CF");
	}

	private String getDataBase(String index, String _d) throws Exception {
		log.warn("index:{},data:{}", index, _d);
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		String result = String.valueOf(Double.valueOf(e));
		log.warn("result:{}", result);
		return result;
	}

	private String getData100(String index, String _d) throws Exception {
		log.warn("index:{},data:{}", index, _d);
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		String result = String.valueOf(Double.valueOf(e) / 100);
		log.warn("result:{}", result);
		return result;
	}

	private void sendWuhanQj(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		String data = iotCloudLog.getData();
		String sn = data.substring(0, 16);
		map.put("JCDB19A080", sn);
		String type = data.substring(16, 18);
		if (type.equals("68")) {
			type = "报警";
		} else {
			type = "心跳";
		}
		try {
			map.put("JCDB19A130", getData(data.substring(48, 49), data.substring(48, 52)));
			map.put("JCDB19A010", type);
			map.put("JCDB19A020", getData(data.substring(18, 19), data.substring(18, 22)));
			map.put("JCDB19A030", getData(data.substring(24, 25), data.substring(24, 28)));
			map.put("JCDB19A040", getData(data.substring(30, 31), data.substring(30, 34)));
			map.put("JCDB19A050", Integer.valueOf(data.substring(22, 24)));
			map.put("JCDB19A060", Integer.valueOf(data.substring(28, 30)));
			map.put("JCDB19A070", Integer.valueOf(data.substring(34, 36)));
			map.put("JCDB19A090", Double.valueOf(getData(data.substring(36, 37), data.substring(36, 40))));
			map.put("JCDB19A100", Double.valueOf(getData(data.substring(42, 43), data.substring(42, 46))));
			map.put("JCDB19A110", Integer.valueOf(data.substring(40, 42)));
			map.put("JCDB19A120", Integer.valueOf(data.substring(46, 48)));
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			String url = "http://" + device.getUdpIp() + ":" + device.getUdpPort() + "/DzhZXJC/Sjcj/AddJCDB19A";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.sendPost(url, "json=" + json + "&appID=DZH_ZXJC_SJCJ");
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		} catch (Exception e) {
			log.error("error:{}", e);
		}

	}

	private String getData(String index, String _d) throws Exception {
		log.warn("index:{},data:{}", index, _d);
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		String result = String.valueOf(Double.valueOf(e) / 1000);
		log.warn("result:{}", result);
		return result;
	}

	/**
	 * 设备注册
	 * 
	 * @param imei
	 * @param mac
	 * @param ipLocal
	 * @return
	 */
	@RequestMapping(path = "/register")
	@ResponseBody
	public Resp<?> register(String imei, String mac, String ipLocal, String name) {
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
			log.warn("msg:{}", jsonObject);
			String deviceId = jsonObject.getString("deviceId");
			httpsUtil = new HttpsUtil();
			httpsUtil.initSSLConfigForTwoWay();
			accessToken = login(httpsUtil);
			String urlModifyDeviceInfo = Constant.MODIFY_DEVICE_INFO + "/" + deviceId;
			String manufacturerId = "Zhanway";
			String manufacturerName = "Zhanway";
			String deviceType = "SmartDevice";
			String model = "ZWMNB01";
			String protocolType = "CoAP";
			Map<String, Object> paramModifyDeviceInfo = new HashMap<>();
			paramModifyDeviceInfo.put("manufacturerId", manufacturerId);
			paramModifyDeviceInfo.put("manufacturerName", manufacturerName);
			paramModifyDeviceInfo.put("deviceType", deviceType);
			paramModifyDeviceInfo.put("model", model);
			paramModifyDeviceInfo.put("name", name + "_" + imei);
			paramModifyDeviceInfo.put("protocolType", protocolType);
			String jsonRequest2 = JsonUtil.jsonObj2Sting(paramModifyDeviceInfo);
			Map<String, String> header2 = new HashMap<>();
			header.put(Constant.HEADER_APP_KEY, appId);
			header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
			StreamClosedHttpResponse responseModifyDeviceInfo = httpsUtil.doPutJsonGetStatusLine(urlModifyDeviceInfo,
					header2, jsonRequest2);
			log.warn("msg:{}", responseModifyDeviceInfo.getContent());
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
			log.error("error:{}", e);
		}
		return resp;
	}

}
