package main.entry.webapp.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.text.DecimalFormat;
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
import database.models.PuzhiJob;
import database.models.vo.PushModel;
import database.models.vo.TModel;
import database.models.vo.TelcomPushDataModel;
import utils.Constant;
import utils.HttpUtils;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.MD5Util;
import utils.StreamClosedHttpResponse;
import utils.StringUtil;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import service.basicFunctions.PuzhiJobService;
import utils.Resp;
import utils.SichuanSqlUtils;

@Controller
@RequestMapping(value = "/telcom")
public class TelcomCotroller extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TelcomCotroller.class);

	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired
	private IotCloudLogService iotCloudLogService;
	@Autowired
	private PuzhiJobService puzhiJobService;

	@RequestMapping(path = "/na/iocm/devNotify/v1.1.0/addDevice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> addDevice(@RequestBody String r) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("addDevice msg:{}", r);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	@RequestMapping(path = "/na/iocm/devNotify/v1.1.0/reportCmdExecResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> cmd(@RequestBody String r) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("reportCmdExecResult msg:{}", r);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	@RequestMapping(path = "/na/iocm/devNotify/v1.1.0/commandRspData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> commandRspData(@RequestBody String r) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("commandRspData msg:{}", r);
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
			log.warn("updateDeviceDatas msg:{}", r);
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

	@RequestMapping(path = "/test")
	@ResponseBody
	public Resp<?> test() {
		try {
			IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByMac("0001181121000009");
			sendChaozhou("000118112100000969000D00FFDB00FFE6000006000015000BE707D61FFDF500D800CF", ioTCloudDevice);
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
			log.warn("updateDeviceDatas msg:{}", r);
			TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r, TelcomPushDataModel.class);
			if (telcomPushDataModel != null) {
				List<PushModel> list = telcomPushDataModel.getServices();
				PushModel pushModel2 = telcomPushDataModel.getService();
				if (list != null && !list.isEmpty()) {
					for (PushModel pushModel : list) {
						TModel tModel = pushModel.getData();
						IoTCloudDevice ioTCloudDevice = iotCloudDeviceService
								.findByDeviceId(telcomPushDataModel.getDeviceId());
						ioTCloudDevice.setDataTime(new Date());
						iotCloudDeviceService.update(ioTCloudDevice);
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
								&& ioTCloudDevice.getLocalIp().equals("QJ_YICHANG")) {
							HttpUtils.get(
									"http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + tModel.getData());
							sendWuhanQj2_0(ioTCloudDevice, iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_WUHAN")) {
							try {
								String id = tModel.getData().substring(0, 8);
								long dec_num = Long.parseLong(id, 16);
								IotCloudLog log = iotCloudLogService.findById((int) dec_num);
								if (log != null && log.getCmdType() == 1) {
									log.setCmdType(1);
									iotCloudLogService.update(log);
								} else {
									sendWuhanQj3_0(ioTCloudDevice, iotCloudLog);
									HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
											+ tModel.getData());
								}
							} catch (Exception e) {
								log.error("e:{}", e);
							}
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_BJ")) {
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
							sendBeijingQj(ioTCloudDevice, iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_1.0_CZ")) {
							HttpUtils.get(
									"http://app.zhanway.com/home/cloud/qj/zhanway/push/1_0?data=" + tModel.getData());
							sendChaozhou(iotCloudLog.getData(), ioTCloudDevice);
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_2.0")) {
							HttpUtils.get(
									"http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + tModel.getData());
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0")) {
							try {
								String id = tModel.getData().substring(0, 8);
								long dec_num = Long.parseLong(id, 16);
								IotCloudLog log = iotCloudLogService.findById((int) dec_num);
								if (log != null && log.getCmdType() == 1) {
									log.setCmdType(1);
									iotCloudLogService.update(log);
								} else {
									HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
											+ tModel.getData());
								}
							} catch (Exception e) {
								log.error("e:{}", e);
							}
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_YIBIN")) {
							try {
								String id = tModel.getData().substring(0, 8);
								long dec_num = Long.parseLong(id, 16);
								IotCloudLog log = iotCloudLogService.findById((int) dec_num);
								if (log != null && log.getCmdType() == 1) {
									log.setCmdType(1);
									iotCloudLogService.update(log);
								} else {
									sendYIBIN(ioTCloudDevice, iotCloudLog.getData());
									HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
											+ tModel.getData());
								}
							} catch (Exception e) {
								log.error("e:{}", e);
							}
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_GUANGDONG")) {
							try {
								String id = tModel.getData().substring(0, 8);
								long dec_num = Long.parseLong(id, 16);
								IotCloudLog log = iotCloudLogService.findById((int) dec_num);
								if (log != null && log.getCmdType() == 1) {
									log.setCmdType(1);
									iotCloudLogService.update(log);
								} else {
									sendGUANGDONG(ioTCloudDevice, iotCloudLog.getData());
									HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
											+ tModel.getData());
								}
							} catch (Exception e) {
								log.error("e:{}", e);
							}
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("QJ_PUSHI")) {
							String id = iotCloudLog.getData().substring(0, 6);
							long dec_num = Long.parseLong(id, 16);
							PuzhiJob pz = puzhiJobService.findByMacAndId((int) dec_num, ioTCloudDevice.getMac());
							if (pz != null) {
								log.warn("puzhi task:{},{},{}", id, dec_num, pz);
								pz.setTaskStatus(1);
								Map<String, Object> _r = new HashMap<>();
								String r2 = pz.getFeatureCtx() + "&msgid=" + pz.getMsgid() + "&result=succ";
								_r.put("data", r2);
								HttpUtils.postPuzhiJob(ioTCloudDevice.getUdpIp().split("_")[0],
										JSONObject.toJSONString(_r));
								puzhiJobService.update(pz);
							} else {
								HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data="
										+ tModel.getData());
								sendPushi(iotCloudLog.getData(), ioTCloudDevice);
							}
						} else if (ioTCloudDevice.getLocalIp() != null
								&& ioTCloudDevice.getLocalIp().equals("ZCT330M-SWP-N")) {
							sendZcqj(ioTCloudDevice, iotCloudLog);
							HttpUtils.get(
									"http://app.zhanway.com/home/cloud/qj/zhanway/push/zcqj?data=" + tModel.getData());
						} else {
							send(tModel.getData(), ioTCloudDevice.getUdpIp(), ioTCloudDevice.getUdpPort());
						}
						if (StringUtil.isNotBlank(ioTCloudDevice.getLocation())) {
							sendSichuan(ioTCloudDevice, iotCloudLog.getData());
						}
					}
				}
				if (pushModel2 != null) {
					TModel tModel = pushModel2.getData();
					IoTCloudDevice ioTCloudDevice = iotCloudDeviceService
							.findByDeviceId(telcomPushDataModel.getDeviceId());
					ioTCloudDevice.setDataTime(new Date());
					iotCloudDeviceService.update(ioTCloudDevice);
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
							&& ioTCloudDevice.getLocalIp().equals("QJ_YICHANG")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + tModel.getData());
						sendWuhanQj2_0(ioTCloudDevice, iotCloudLog);
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_WUHAN")) {
						try {
							String id = tModel.getData().substring(0, 8);
							long dec_num = Long.parseLong(id, 16);
							IotCloudLog log = iotCloudLogService.findById((int) dec_num);
							if (log != null && log.getCmdType() == 1) {
								log.setCmdType(1);
								iotCloudLogService.update(log);
							} else {
								sendWuhanQj3_0(ioTCloudDevice, iotCloudLog);
								HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
										+ tModel.getData());
							}
						} catch (Exception e) {
							log.error("e:{}", e);
						}
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_BJ")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
						sendBeijingQj(ioTCloudDevice, iotCloudLog);
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_1.0_CZ")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/1_0?data=" + tModel.getData());
						sendChaozhou(iotCloudLog.getData(), ioTCloudDevice);
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_2.0")) {
						HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + tModel.getData());
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0")) {
						try {
							String id = tModel.getData().substring(0, 8);
							long dec_num = Long.parseLong(id, 16);
							IotCloudLog log = iotCloudLogService.findById((int) dec_num);
							if (log != null && log.getCmdType() == 1) {
								log.setCmdType(1);
								iotCloudLogService.update(log);
							} else {
								HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
										+ tModel.getData());
							}
						} catch (Exception e) {
							log.error("e:{}", e);
						}

					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_YIBIN")) {
						try {
							String id = tModel.getData().substring(0, 8);
							long dec_num = Long.parseLong(id, 16);
							IotCloudLog log = iotCloudLogService.findById((int) dec_num);
							if (log != null && log.getCmdType() == 1) {
								log.setCmdType(1);
								iotCloudLogService.update(log);
							} else {
								sendYIBIN(ioTCloudDevice, iotCloudLog.getData());
								HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
										+ tModel.getData());
							}
						} catch (Exception e) {
							log.error("e:{}", e);
						}

					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_GUANGDONG")) {
						try {
							String id = tModel.getData().substring(0, 8);
							long dec_num = Long.parseLong(id, 16);
							IotCloudLog log = iotCloudLogService.findById((int) dec_num);
							if (log != null && log.getCmdType() == 1) {
								log.setCmdType(1);
								iotCloudLogService.update(log);
							} else {
								sendGUANGDONG(ioTCloudDevice, iotCloudLog.getData());
								HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="
										+ tModel.getData());
							}
						} catch (Exception e) {
							log.error("e:{}", e);
						}

					} else if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("QJ_PUSHI")) {
						String id = iotCloudLog.getData().substring(0, 8);
						long dec_num = Long.parseLong(id, 16);
						PuzhiJob pz = puzhiJobService.findByMacAndId((int) dec_num, ioTCloudDevice.getMac());
						if (pz != null) {
							log.warn("puzhi task:{},{},{}", id, dec_num, pz);
							pz.setTaskStatus(1);
							Map<String, Object> _r = new HashMap<>();
							String r2 = pz.getFeatureCtx() + "&msgid=" + pz.getMsgid() + "&result=succ";
							_r.put("data", r2);
							HttpUtils.postPuzhiJob(ioTCloudDevice.getUdpIp().split("_")[0],
									JSONObject.toJSONString(_r));
							puzhiJobService.update(pz);
						} else {
							HttpUtils.get(
									"http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + tModel.getData());
							sendPushi(iotCloudLog.getData(), ioTCloudDevice);
						}
					} else if (ioTCloudDevice.getLocalIp() != null
							&& ioTCloudDevice.getLocalIp().equals("ZCT330M-SWP-N")) {
						sendZcqj(ioTCloudDevice, iotCloudLog);
						HttpUtils
								.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/zcqj?data=" + tModel.getData());
					} else {
						send(tModel.getData(), ioTCloudDevice.getUdpIp(), ioTCloudDevice.getUdpPort());
					}
					if (StringUtil.isNotBlank(ioTCloudDevice.getLocation())) {
						sendSichuan(ioTCloudDevice, iotCloudLog.getData());
					}
				}
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	/**
	 * 直川倾角给湖北武汉使用
	 * 
	 * @param device
	 * @param iotCloudLog
	 */
	private void sendZcqj(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		try {
			String data = iotCloudLog.getData();
			log.warn("data:---zc--{}", data);
			Map<String, Object> map = new HashMap<String, Object>();
			String sn = device.getSimCard().split("_")[1];
			map.put("JCDB19A080", sn);
			String cmd = data.substring(5, 6);
			if (cmd.equals("7")) {
				cmd = "心跳";
				map.put("JCDB19A110", 0);
				map.put("JCDB19A120", 0);
			} else if (cmd.equals("8")) {
				cmd = "报警";
				map.put("JCDB19A110", 1);
				map.put("JCDB19A120", 1);
			}
			String x1 = data.substring(38, 40);
			String x2 = data.substring(36, 38);
			String x3 = data.substring(34, 36);
			String x4 = data.substring(32, 34);
			String y1 = data.substring(50, 52);
			String y2 = data.substring(48, 50);
			String y3 = data.substring(46, 48);
			String y4 = data.substring(44, 46);
			String x = hexToFloat(x1 + x2 + x3 + x4);
			String y = hexToFloat(y1 + y2 + y3 + y4);
			String b1 = data.substring(66, 68);
			String b2 = data.substring(64, 66);
			Double bat = Double.valueOf(Long.parseLong(b1 + b2, 16)) / 100;
			map.put("JCDB19A130", bat);
			map.put("JCDB19A010", cmd);
			map.put("JCDB19A020", 0);// acc_x
			map.put("JCDB19A030", 0);// acc_y
			map.put("JCDB19A040", 0);// acc_z
			map.put("JCDB19A050", 0);
			map.put("JCDB19A060", 0);
			map.put("JCDB19A070", 0);
			map.put("JCDB19A090", x);// x
			map.put("JCDB19A100", y);// y

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

	/**
	 * 四川数据库
	 * 
	 * @param device
	 * @param data
	 */
	private void sendSichuan(IoTCloudDevice device, String data) {
		try {
			log.warn("data:--四川---{}", data);
			String cmd = data.substring(20, 22);
			if (cmd.equals("68")) {
				cmd = "心跳";
			} else if (cmd.equals("69")) {
				cmd = "报警";
			}
			if (cmd.equals("心跳")) {
				SichuanSqlUtils basicApp = new SichuanSqlUtils();
				basicApp.loadJdbcDriver();
				basicApp.connect();
				String x = getData100(data.substring(50, 51), data.substring(50, 54));
				basicApp.insertXintiao(device.getArea(), device.getLocation(), x, "01");
				basicApp.disConnect();
				log.warn("send qj-----sichuan------------------\n:{},{},{},{}\n---------------------------------",
						device.getArea(), device.getLocation(), x, "01");
			}
			if ("报警".equals(cmd)) {
				SichuanSqlUtils basicApp = new SichuanSqlUtils();
				basicApp.loadJdbcDriver();
				basicApp.connect();
				String x = getData100(data.substring(50, 51), data.substring(50, 54));
				basicApp.insertBaojing("C1", 1, device.getArea(), device.getLocation(), Double.valueOf(x), 2.0);
				basicApp.disConnect();
				log.warn("send qj-----sichuan bj------------------\n:{},{},{},{}\n---------------------------------",
						device.getArea(), device.getLocation(), x, "01");
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

	/**
	 * 新固件武汉对接2019-08-14
	 * 
	 * @param device
	 * @param iotCloudLog
	 */
	private void sendWuhanQj3_0(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		try {
			String data = iotCloudLog.getData();
			log.warn("data:-----{}", data);
			String cmd = data.substring(20, 22);
			if (cmd.equals("68")) {
				cmd = "心跳";
			} else if (cmd.equals("69")) {
				cmd = "报警";
			}
			if (cmd.equals("心跳")) {
				Map<String, Object> map = new HashMap<String, Object>();
				String sn = device.getSimCard().split("_")[1];
				map.put("JCDB19A080", sn);
				String acc_x = hexToFloat(data.substring(26, 34));
				String acc_y = hexToFloat(data.substring(34, 42));
				String acc_z = hexToFloat(data.substring(42, 50));
				String x = getData100(data.substring(50, 51), data.substring(50, 54));
				String y = getData100(data.substring(54, 55), data.substring(54, 58));
				String bat = getData(data.substring(62, 63), data.substring(62, 66));
				map.put("JCDB19A130", bat);
				map.put("JCDB19A010", cmd);
				map.put("JCDB19A020", acc_x);// acc_x
				map.put("JCDB19A030", acc_y);// acc_y
				map.put("JCDB19A040", acc_z);// acc_z
				map.put("JCDB19A050", 0);
				map.put("JCDB19A060", 0);
				map.put("JCDB19A070", 0);
				map.put("JCDB19A090", x);// x
				map.put("JCDB19A100", y);// y
				map.put("JCDB19A110", 0);
				map.put("JCDB19A120", 0);
				String json = JSONObject.toJSONString(map);
				log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
				String url = "http://" + device.getUdpIp() + ":" + device.getUdpPort() + "/DzhZXJC/Sjcj/AddJCDB19A";
				log.warn("send url-----------------------\n:{}\n---------------------------------", url);
				String res = HttpUtils.sendPost(url, "json=" + json + "&appID=DZH_ZXJC_SJCJ");
				log.warn("send res-----------------------\n:{}\n---------------------------------", res);
			}
			if ("报警".equals(cmd)) {
				Map<String, Object> map = new HashMap<String, Object>();
				String sn = device.getSimCard().split("_")[1];
				map.put("JCDB19A080", sn);
				int[] arr = getB(data.substring(28, 30));
				String acc_x = hexToFloat(data.substring(30, 38));
				String acc_y = hexToFloat(data.substring(38, 46));
				String acc_z = hexToFloat(data.substring(46, 54));
				String x = getData100(data.substring(54, 55), data.substring(54, 58));
				String y = getData100(data.substring(58, 59), data.substring(58, 62));
				String bat = getData(data.substring(114, 115), data.substring(114, 118));
				map.put("JCDB19A130", bat);
				map.put("JCDB19A010", cmd);
				map.put("JCDB19A020", acc_x);// acc_x
				map.put("JCDB19A030", acc_y);// acc_y
				map.put("JCDB19A040", acc_z);// acc_z
				map.put("JCDB19A050", arr[5]);
				map.put("JCDB19A060", arr[4]);
				map.put("JCDB19A070", arr[3]);
				map.put("JCDB19A090", x);// x
				map.put("JCDB19A100", y);// y
				map.put("JCDB19A110", arr[2]);
				map.put("JCDB19A120", arr[1]);
				String json = JSONObject.toJSONString(map);
				log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
				String url = "http://" + device.getUdpIp() + ":" + device.getUdpPort() + "/DzhZXJC/Sjcj/AddJCDB19A";
				log.warn("send url-----------------------\n:{}\n---------------------------------", url);
				String res = HttpUtils.sendPost(url, "json=" + json + "&appID=DZH_ZXJC_SJCJ");
				log.warn("send res-----------------------\n:{}\n---------------------------------", res);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

	public int[] getB(String data) {
		Integer num = Integer.parseInt(data, 16);
		String a = Integer.toBinaryString(num);
		int[] s = new int[] { 0, 0, 0, 0, 0, 0 };
		if (a.length() == 1) {
			s[5] = Integer.valueOf(a);
		}
		if (a.length() == 2) {
			s[4] = Integer.valueOf(a.substring(0, 1));
			s[5] = Integer.valueOf(a.substring(1, 2));
		}
		if (a.length() == 3) {
			s[3] = Integer.valueOf(a.substring(0, 1));
			s[4] = Integer.valueOf(a.substring(1, 2));
			s[5] = Integer.valueOf(a.substring(2, 3));
		}
		if (a.length() == 4) {
			s[2] = Integer.valueOf(a.substring(0, 1));
			s[3] = Integer.valueOf(a.substring(1, 2));
			s[4] = Integer.valueOf(a.substring(2, 3));
			s[5] = Integer.valueOf(a.substring(3, 4));
		}
		if (a.length() == 5) {
			s[1] = Integer.valueOf(a.substring(0, 1));
			s[2] = Integer.valueOf(a.substring(1, 2));
			s[3] = Integer.valueOf(a.substring(2, 3));
			s[4] = Integer.valueOf(a.substring(3, 4));
			s[5] = Integer.valueOf(a.substring(4, 5));
		}
		if (a.length() == 6) {
			s[0] = Integer.valueOf(a.substring(0, 1));
			s[1] = Integer.valueOf(a.substring(1, 2));
			s[2] = Integer.valueOf(a.substring(2, 3));
			s[3] = Integer.valueOf(a.substring(3, 4));
			s[4] = Integer.valueOf(a.substring(4, 5));
			s[5] = Integer.valueOf(a.substring(5, 6));
		}
		return s;
	}

	/**
	 * 浮点数按IEEE754标准转16进制字符串
	 * 
	 * @param f
	 * @return
	 */
	public String FloatToHexString(float f) {
		int i = Float.floatToIntBits(f);
		String str = Integer.toHexString(i).toUpperCase();
		return str;
	}

	/**
	 * 16进制字符串IEEE754标准转小数
	 * 
	 * @param s
	 * @return
	 */
	private static String hexToFloat(String s) {
		BigInteger big = new BigInteger(s, 16);
		Float z = Float.intBitsToFloat(big.intValue());
		Double r = Double.valueOf(z);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}

	/**
	 * 普世广东
	 * 
	 * @param ioTCloudDevice
	 * @param data
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void sendGUANGDONG(IoTCloudDevice ioTCloudDevice, String data) throws NumberFormatException, Exception {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> sendData = new HashMap<>();
		map.put("deviceId", ioTCloudDevice.getUdpIp().split("_")[0]);
		map.put("apikey", ioTCloudDevice.getUdpIp().split("_")[1]);
		String cmd = data.substring(20, 22);
		if (cmd.equals("68")) {
			cmd = "报警";
		} else {
			cmd = "心跳";
		}
		if (cmd.equals("报警") || cmd.equals("心跳")) {
			String acc_x = hexToFloat(data.substring(26, 34));
			String acc_y = hexToFloat(data.substring(34, 42));
			String acc_z = hexToFloat(data.substring(42, 50));
			String x = getData100(data.substring(50, 51), data.substring(50, 54));
			String y = getData100(data.substring(54, 55), data.substring(54, 58));
			String z = getData100(data.substring(58, 59), data.substring(58, 62));
			sendData.put("103_1", x + "," + y + "," + z + "," + acc_x + "," + acc_y + "," + acc_z);
			map.put("data", sendData);
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			// String url =
			// "http://ghiot.cigem.cn/api/devices/datapoints?type=3";
			String url = "http://121.8.170.150:8201/api/devices/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postJson(url, json);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		}
	}

	/**
	 * 普世宜宾
	 * 
	 * @param ioTCloudDevice
	 * @param data
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void sendYIBIN(IoTCloudDevice ioTCloudDevice, String data) throws NumberFormatException, Exception {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> sendData = new HashMap<>();
		map.put("deviceId", ioTCloudDevice.getUdpIp().split("_")[0]);
		map.put("apikey", ioTCloudDevice.getUdpIp().split("_")[1]);
		String cmd = data.substring(20, 22);
		if (cmd.equals("68")) {
			cmd = "报警";
		} else {
			cmd = "心跳";
		}
		if (cmd.equals("报警") || cmd.equals("心跳")) {
			String acc_x = hexToFloat(data.substring(26, 34));
			String acc_y = hexToFloat(data.substring(34, 42));
			String acc_z = hexToFloat(data.substring(42, 50));
			String x = getData100(data.substring(50, 51), data.substring(50, 54));
			String y = getData100(data.substring(54, 55), data.substring(54, 58));
			String z = getData100(data.substring(58, 59), data.substring(58, 62));
			sendData.put("103_1", x + "," + y + "," + z + "," + acc_x + "," + acc_y + "," + acc_z);
			map.put("data", sendData);
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			// String url =
			// "http://ghiot.cigem.cn/api/devices/datapoints?type=3";
			String url = "http://218.6.244.186:44445/api/devices/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postJson(url, json);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		}
	}

	/**
	 * 宜昌新设备
	 * 
	 * @param device
	 * @param iotCloudLog
	 */
	private void sendWuhanQj2_0(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		String data = iotCloudLog.getData();
		String sn = device.getSimCard().split("_")[1];
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
			map.put("JCDB19A020", getData10000(data.substring(18, 19), data.substring(18, 22)));
			map.put("JCDB19A030", getData10000(data.substring(24, 25), data.substring(24, 28)));
			map.put("JCDB19A040", getData10000(data.substring(30, 31), data.substring(30, 34)));
			map.put("JCDB19A050", Integer.valueOf(data.substring(22, 24)));
			map.put("JCDB19A060", Integer.valueOf(data.substring(28, 30)));
			map.put("JCDB19A070", Integer.valueOf(data.substring(34, 36)));
			map.put("JCDB19A090", getData100(data.substring(36, 37), data.substring(36, 40)));
			map.put("JCDB19A100", getData100(data.substring(42, 43), data.substring(42, 46)));
			map.put("JCDB19A110", Integer.valueOf(data.substring(40, 42)));
			map.put("JCDB19A120", Integer.valueOf(data.substring(46, 48)));
			if ("心跳".equals(type)) {
				map.put("JCDB19A020", 0);
				map.put("JCDB19A030", 0);
				map.put("JCDB19A040", 0);
				map.put("JCDB19A100", getData100_3(data.substring(42, 43), data.substring(42, 46)));
			}
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

	/**
	 * 普适地质灾害
	 * 
	 * @param data
	 * @param ioTCloudDevice
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void sendPushi(String data, IoTCloudDevice ioTCloudDevice) throws NumberFormatException, Exception {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> sendData = new HashMap<>();
		map.put("deviceId", ioTCloudDevice.getUdpIp().split("_")[0]);
		map.put("apikey", ioTCloudDevice.getUdpIp().split("_")[1]);
		String type = data.substring(16, 18);
		if (type.equals("68")) {
			type = "报警";
		} else {
			type = "心跳";
		}
		if (type.equals("报警") || type.equals("心跳")) {
			String acc_x = getData10000(data.substring(18, 19), data.substring(18, 22));
			String acc_y = getData10000(data.substring(24, 25), data.substring(24, 28));
			String acc_z = getData10000(data.substring(30, 31), data.substring(30, 34));
			String x = getData100(data.substring(36, 37), data.substring(36, 40));
			String y = getData100(data.substring(42, 43), data.substring(42, 46));
			String z = getData100(data.substring(70, 71), data.substring(70, 74));
			sendData.put("103_1", x + "," + y + "," + z + "," + acc_x + "," + acc_y + "," + acc_z);
			map.put("data", sendData);
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			String url = "http://ghiot.cigem.cn/api/devices/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postJson(url, json);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		}
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

	private void sendChaozhou(String data, IoTCloudDevice ioTCloudDevice) {
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
			String params = "type=" + type + "&acc_x=" + acc_x + "&acc_y=" + acc_y + "&acc_z=" + acc_z + "&acc_x_type="
					+ acc_x_type + "&acc_y_type=" + acc_y_type + "&acc_z_type=" + acc_z_type + "&sn=" + sn + "&x=" + x
					+ "&y=" + y + "&x_type=" + x_type + "&y_type=" + y_type + "&bat=" + bat + "&tem=" + tem + "&rssi="
					+ rssi;
			String SIGN = MD5Util.toMD5(params + sign);
			params = params + "&sign=" + SIGN;
			HttpUtils.sendPost(ioTCloudDevice.getUdpIp(), params);
		} catch (Exception e) {
			log.error("error:{}", e);
		}

	}

	public static void main(String[] args) {
		// new
		// TelcomCotroller().sendChaozhou("000118112100000969000D00FFDB00FFE6000006000015000BE707D61FFDF500D800CF",null);
		try {
			// new TelcomCotroller().getData10000("4500", "4");
			System.out.print(hexToFloat("40400000"));
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private String getData100_3(String index, String _d) throws Exception {
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
		Double r = Double.valueOf(e) / 300;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
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
		Double r = Double.valueOf(e) / 100;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		log.warn("result:{}", result);
		return result;
	}

	private String getData10000(String index, String _d) throws Exception {
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
		Double r = Double.valueOf(e) / 10000;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
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
		Double r = Double.valueOf(e) / 1000;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
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
	public Resp<?> register(String imei, String mac, String ipLocal, String name, String simCard) {
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
			log.warn("regInfo------:{}", jsonObject);
			String deviceId = jsonObject.getString("deviceId");
			httpsUtil = new HttpsUtil();
			httpsUtil.initSSLConfigForTwoWay();
			String accessToken2 = login(httpsUtil);
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
			paramModifyDeviceInfo.put("name", name);
			paramModifyDeviceInfo.put("protocolType", protocolType);
			String jsonRequest2 = JsonUtil.jsonObj2Sting(paramModifyDeviceInfo);
			Map<String, String> header2 = new HashMap<>();
			header2.put(Constant.HEADER_APP_KEY, appId);
			header2.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken2);
			StreamClosedHttpResponse responseModifyDeviceInfo = httpsUtil.doPutJsonGetStatusLine(urlModifyDeviceInfo,
					header2, jsonRequest2);
			jsonObject = JSONObject.parseObject(responseModifyDeviceInfo.getContent());
			log.warn("ModifyDeviceInfo-------:{}", jsonObject);
			IoTCloudDevice ioTCloudDevice = new IoTCloudDevice();
			ioTCloudDevice.setCreateTime(new Date());
			ioTCloudDevice.setImei(imei);
			ioTCloudDevice.setLocalIp(ipLocal);
			ioTCloudDevice.setMac(mac);
			ioTCloudDevice.setType(2);
			ioTCloudDevice.setSimCard(simCard);
			ioTCloudDevice.setDeviceId(deviceId);
			iotCloudDeviceService.save(ioTCloudDevice);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

}
