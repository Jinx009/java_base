package main.entry.webapp.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.DatagramSocket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

	/**
	 * 电信平台新增设备会通知这个接口
	 * @param r
	 * @return
	 */
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

	/**
	 * 下行执行结果会通知这个接口
	 * @param r
	 * @return
	 */
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

	/**
	 * 下行返回？
	 * @param r
	 * @return
	 */
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
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}", e);
		}
		return resp;

	}

	/**
	 * 启动原始联通版本的udp倾角传感器服务器
	 * @return
	 */
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



	/**
	 * 真正电信平台数据上报接口
	 * @param r
	 * @return
	 */
	@RequestMapping(path = "/notice/na/iocm/devNotify/v1.1.0/updateDeviceDatas")
	@ResponseBody
	public Resp<?> noticeN(@RequestBody String r) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("updateDeviceDatas msg:{}", r);
			TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r, TelcomPushDataModel.class);
			if (telcomPushDataModel != null) {
				List<PushModel> list = telcomPushDataModel.getServices();
				if (list != null && !list.isEmpty()) {
					for (PushModel pushModel : list) {
						TModel tModel = pushModel.getData();
						IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByDeviceId(telcomPushDataModel.getDeviceId());
						ioTCloudDevice.setDataTime(new Date());
						ioTCloudDevice.setUpdateTime(new Date());
						iotCloudDeviceService.update(ioTCloudDevice);
						IotCloudLog iotCloudLog = new IotCloudLog();
						iotCloudLog.setData(tModel.getData());
						iotCloudLog.setFromSite("telcom");
						iotCloudLog.setCreateTime(new Date());
						iotCloudLog.setImei(ioTCloudDevice.getImei());
						iotCloudLog.setType(0);
						iotCloudLog.setMac(ioTCloudDevice.getMac());
						iotCloudLogService.save(iotCloudLog);
						if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("SM_CZ")) {//潮州烟感
							pushChaozhaoyangan(tModel);
						} else if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("QJ")) {
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/push?data=" + tModel.getData());
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY")) {//武汉最早测试的几个倾角传感器
							sendQjZhanway(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_YICHANG")) {//宜昌安装的倾角 对接武汉平台
							sendQjYiChang(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_WUHAN")) {//武汉的100来个新版本倾角
							sendWuhan(tModel,ioTCloudDevice,iotCloudLog);
						}else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.1_WUHAN")&&ioTCloudDevice.getIsCorrect()==1) {//武汉的100来个新版本倾角
							sendWuhan(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_BJ")) {//北京普世平台倾角
							sendBeijing(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_1.0_CZ")) {//潮州最老版本倾角
							sendChaozhouQj(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_2.0")) {//倾角第二版
							HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + tModel.getData());
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0")) {//新版倾角测试专用
							sendQjZhanway3_0(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_YIBIN")) {//宜宾 用的普世新平台 四川设备
							sendQjSichuan(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_PUSHI")) {//新版本倾角对接普世
							sendBeijing3_0(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_V_3.0_GUANGDONG")) {//新版本倾角安装在广东
							sendQjGuangdong(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("QJ_PUSHI")) {
							sendQjPushi(tModel,ioTCloudDevice,iotCloudLog);
						} else if (ioTCloudDevice.getLocalIp() != null&& ioTCloudDevice.getLocalIp().equals("ZCT330M-SWP-N")) {//直川倾角
							sendQjZhichuan(tModel,ioTCloudDevice,iotCloudLog);
						} else {
							send(tModel.getData(), ioTCloudDevice.getUdpIp(), ioTCloudDevice.getUdpPort());
						}
						if (StringUtil.isNotBlank(ioTCloudDevice.getLocation())&&ioTCloudDevice.getType()==2) {//对接四川数据库
							sendSichuanSelf(tModel,ioTCloudDevice,iotCloudLog);
						}
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
	 * 四川倾角对接数据库
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendSichuanSelf(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		new Thread() {
			public void run() {
				log.warn("开始线程");
				sendSichuan(ioTCloudDevice, iotCloudLog.getData());
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendA())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendA());
					sendSichuan(de, iotCloudLog.getData());
				}
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendB())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendB());
					sendSichuan(de, iotCloudLog.getData());
				}
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendC())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendC());
					sendSichuan(de, iotCloudLog.getData());
				}
			}
		}.start();
	}

	/**
	 * 直川倾角数据推送
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendQjZhichuan(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		sendZcqj(ioTCloudDevice, iotCloudLog);
		if(StringUtil.isNotBlank(ioTCloudDevice.getSendA())){
			IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendA());
			sendZcqj(de, iotCloudLog);
		}
		if(StringUtil.isNotBlank(ioTCloudDevice.getSendB())){
			IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendB());
			sendZcqj(de, iotCloudLog);
		}
		if(StringUtil.isNotBlank(ioTCloudDevice.getSendC())){
			IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendC());
			sendZcqj(de, iotCloudLog);
		}
		HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/zcqj?data=" + tModel.getData());
	}

	/**
	 * 对接普世老设备
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendQjPushi(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
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
			HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data="+ tModel.getData());
			try {
				sendPushi(iotCloudLog.getData(), ioTCloudDevice);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 广东两个设备
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendQjGuangdong(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		try {
			String id = tModel.getData().substring(0, 8);
			long dec_num = Long.parseLong(id, 16);
			IotCloudLog log = iotCloudLogService.findById((int) dec_num);
			if (log != null && log.getCmdType() == 1) {
				log.setCmdType(1);
				iotCloudLogService.update(log);
			} else {
				sendGUANGDONG(ioTCloudDevice, iotCloudLog.getData());
				HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="+ tModel.getData());
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 新版本倾角对接北京普世
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendBeijing3_0(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		try {
			String id = tModel.getData().substring(0, 8);
			long dec_num = Long.parseLong(id, 16);
			IotCloudLog log = iotCloudLogService.findById((int) dec_num);
			if (log != null && log.getCmdType() == 1) {
				log.setCmdType(1);
				iotCloudLogService.update(log);
			} else {
				sendPushiNew(ioTCloudDevice, iotCloudLog.getData());
				HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="+ tModel.getData());
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 四川那批设备
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendQjSichuan(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		try {
			String id = tModel.getData().substring(0, 8);
			long dec_num = Long.parseLong(id, 16);
			IotCloudLog log = iotCloudLogService.findById((int) dec_num);
			if (log != null && log.getCmdType() == 1) {
				log.setCmdType(1);
				iotCloudLogService.update(log);
			} else {
				sendYIBIN(ioTCloudDevice, iotCloudLog.getData());			
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendA())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendA());
					sendYIBIN(de, iotCloudLog.getData());
				}
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendB())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendB());
					sendYIBIN(de, iotCloudLog.getData());
				}
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendC())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendC());
					sendYIBIN(de, iotCloudLog.getData());
				}
				HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="+ tModel.getData());
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 展为新版本倾角测试转发
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendQjZhanway3_0(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		try {
			String id = tModel.getData().substring(0, 8);
			long dec_num = Long.parseLong(id, 16);
			IotCloudLog log = iotCloudLogService.findById((int) dec_num);
			if (log != null && log.getCmdType() == 1) {
				log.setCmdType(1);
				iotCloudLogService.update(log);
			} else {
				HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="+ tModel.getData());
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 潮州最早批次倾角
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendChaozhouQj(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/1_0?data=" + tModel.getData());
		sendChaozhou(iotCloudLog.getData(), ioTCloudDevice);
	}

	/**
	 * 北京普世地质灾害平台
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendBeijing(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
		sendBeijingQj(ioTCloudDevice, iotCloudLog);
	}

	/**
	 * 武汉大批次
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendWuhan(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		try {
			String id = tModel.getData().substring(0, 8);
			long dec_num = Long.parseLong(id, 16);
			IotCloudLog log = iotCloudLogService.findById((int) dec_num);
			if (log != null && log.getCmdType() == 1) {
				log.setCmdType(1);
				iotCloudLogService.update(log);
			} else {
				sendWuhanQj3_0(ioTCloudDevice, iotCloudLog);
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendA())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendA());
					sendWuhanQj3_0_1(de, iotCloudLog);
				}
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendB())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendB());
					sendWuhanQj3_0_1(de, iotCloudLog);
				}
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendC())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendC());
					sendWuhanQj3_0_1(de, iotCloudLog);
				}
				if(StringUtil.isNotBlank(ioTCloudDevice.getSendD())){
					IoTCloudDevice de = iotCloudDeviceService.findByMac(ioTCloudDevice.getSendD());
					sendWuhanQj3_0_1(de, iotCloudLog);
				}
				HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/3_0?data="+ tModel.getData());
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 宜昌倾角传感器
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendQjYiChang(TModel tModel, IoTCloudDevice ioTCloudDevice, IotCloudLog iotCloudLog) {
		HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + tModel.getData());
		sendWuhanQj2_0(ioTCloudDevice, iotCloudLog);
	}

	/**
	 * 潮州烟感
	 * @param tModel
	 */
	private void pushChaozhaoyangan(TModel tModel){
		String _s = "content=" + tModel.getData() + "&key=gdzxxxkjgfyxgs9981n";
		HttpUtils.get("http://zhxf.gdzxkj.net:8003/api/devices_get_single_info?sign="+ MD5Util.toMD5(_s).toLowerCase() + "&" + _s);
	}
	
	/**
	 * 武汉早批倾角传感器
	 * @param tModel
	 * @param ioTCloudDevice
	 * @param iotCloudLog
	 */
	private void sendQjZhanway(TModel tModel,IoTCloudDevice ioTCloudDevice,IotCloudLog iotCloudLog){
		HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + tModel.getData());
		sendWuhanQj(ioTCloudDevice, iotCloudLog);
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
			log.warn("data:---zhichuan--{}", data);
			String sn = device.getSimCard().split("_")[1];
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map< String, Object> map = new HashMap<String, Object>();
			Map< String, Object> d = new HashMap<String, Object>();
			d.put("gX", 0);
			d.put("gY", 0);
			d.put("gZ", 0);
			d.put("X", x);
			d.put("Y", y);
			d.put("Z", 0);
			map.put("sblxbm", "103");
			map.put("jczb",d);
			map.put("jcsj", sdf.format(new Date()));
			map.put("cgq", "1");
			HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+sn+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
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
				log.warn("send qj-----sichuan------------------\n:{},{},{},{}\n---------------------------------",
						device.getArea(), device.getLocation(), x, "01");
			}
			if ("报警".equals(cmd)) {
				SichuanSqlUtils basicApp = new SichuanSqlUtils();
				basicApp.loadJdbcDriver();
				basicApp.connect();
				String x = getData100(data.substring(50, 51), data.substring(50, 54));
				basicApp.insertBaojing("C1", 1, device.getArea(), device.getLocation(), Double.valueOf(x), 2.0);
				log.warn("send qj-----sichuan bj------------------\n:{},{},{},{}\n---------------------------------",
						device.getArea(), device.getLocation(), x, "01");
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}
	
	
	/**
	 * 新固件武汉对接2019-08-14 转发
	 * 
	 * @param device
	 * @param iotCloudLog
	 */
	private void sendWuhanQj3_0_1(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		try {
			String data = iotCloudLog.getData();
			log.warn("data:-----{}", data);
			String mac = data.substring(0,16);
			String cmd = data.substring(20, 22);
			if (cmd.equals("68")) {
				cmd = "心跳";
			} else if (cmd.equals("69")) {
				cmd = "报警";
			}
			if (cmd.equals("心跳")) {
				String sn = device.getSimCard().split("_")[1];
				String acc_x = hexToFloat_1(data.substring(26, 34));
				String acc_y = hexToFloat_1(data.substring(34, 42));
				String acc_z = hexToFloat_1(data.substring(42, 50));
				String x = getData100_1(data.substring(50, 51), data.substring(50, 54));
				String y = getData100_1(data.substring(54, 55), data.substring(54, 58));
				Double x_d = Double.valueOf(x);
				Double y_d = Double.valueOf(y);
				Double maxX = device.getMaxX();
				if(maxX==null){
					maxX = 0.00;
				}
				Double maxY = device.getMaxY();
				if(maxY==null){
					maxY = 0.00;
				}
				if(Math.abs(maxX)<Math.abs(x_d)){
					device.setMaxX(x_d);
				}
				if(Math.abs(maxY)<Math.abs(y_d)){
					device.setMaxY(y_d);
				}
				device.setSendBase(mac);
				device.setUpdateTime(new Date());
				Integer num = device.getDataNum();
				if(num==null){
					num = 0;
				}
				device.setDataNum(num+1);
				iotCloudDeviceService.update(device);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map< String, Object> map = new HashMap<String, Object>();
				Map< String, Object> d = new HashMap<String, Object>();
				if(device.getMac().indexOf("0508")>-1){
					if(device.getIsCorrect()==null||device.getIsCorrect()==0){
						if(Math.abs(x_d)>=2.5||Math.abs(y_d)>=2.5){
							log.warn("random:满足条件");
							int ran = new Random().nextInt(200)-100;
							double random = 0.00;
							random =Double.valueOf(ran)/100-1.40;
							if(ran>0){
								random = Double.valueOf(ran)/100+1.40;
							}
							double r = 0.00;
							BigDecimal f = new BigDecimal(0.00);
							DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
							if(Math.abs(x_d)>Math.abs(y_d)){
								r = x_d/random;
								x = String.valueOf(random);
								f = new BigDecimal(y_d/r);
								double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
								y = decimalFormat.format(g);
							}else{
								r = y_d/random;
								y = String.valueOf(random);
								f = new BigDecimal(x_d/r);
								double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
								x = decimalFormat.format(g);
							}
						}
					}
				}
				d.put("gX", acc_x);
				d.put("gY", acc_y);
				d.put("gZ", acc_z);
				d.put("X", x);
				d.put("Y", y);
				d.put("Z", 0);
				map.put("sblxbm", "103");
				map.put("jczb",d);
				map.put("jcsj", sdf.format(iotCloudLog.getCreateTime()));
				map.put("cgq", "1");
				HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+sn+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
			}
			if ("报警".equals(cmd)) {
				String sn = device.getSimCard().split("_")[1];
				String acc_x = hexToFloat_1(data.substring(30, 38));
				String acc_y = hexToFloat_1(data.substring(38, 46));
				String acc_z = hexToFloat_1(data.substring(46, 54));
				String x = getData100_1(data.substring(54, 55), data.substring(54, 58));
				String y = getData100_1(data.substring(58, 59), data.substring(58, 62));
				Double x_d = Double.valueOf(x);
				Double y_d = Double.valueOf(y);
				Double maxX = device.getMaxX();
				if(maxX==null){
					maxX = 0.00;
				}
				Double maxY = device.getMaxY();
				if(maxY==null){
					maxY = 0.00;
				}
				if(Math.abs(maxX)<Math.abs(x_d)){
					device.setMaxX(x_d);
				}
				if(Math.abs(maxY)<Math.abs(y_d)){
					device.setMaxY(y_d);
				}
				device.setSendBase(mac);
				device.setUpdateTime(new Date());
				Integer num = device.getDataNum();
				if(num==null){
					num = 0;
				}
				device.setDataNum(num+1);
				iotCloudDeviceService.update(device);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map< String, Object> map = new HashMap<String, Object>();
				Map< String, Object> d = new HashMap<String, Object>();
				d.put("gX", acc_x);
				d.put("gY", acc_y);
				d.put("gZ", acc_z);
				d.put("X", x);
				d.put("Y", y);
				d.put("Z", 0);
				map.put("sblxbm", "103");
				map.put("jczb",d);
				map.put("jcsj", sdf.format(iotCloudLog.getCreateTime()));
				map.put("cgq", "1");
				if(device.getIsCorrect()!=null&&device.getIsCorrect()==1&&device.getDataNum()<=38){
					HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+sn+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
				}
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
			log.warn("data:-wuhanqj3_0----{}", data);
			String cmd = data.substring(20, 22);
			if (cmd.equals("68")) {
				cmd = "心跳";
			} else if (cmd.equals("69")) {
				cmd = "报警";
			}
			if (cmd.equals("心跳")) {
				String sn = device.getSimCard().split("_")[1];
				String acc_x = hexToFloat(data.substring(26, 34));
				String acc_y = hexToFloat(data.substring(34, 42));
				String acc_z = hexToFloat(data.substring(42, 50));
				String x = getData100(data.substring(50, 51), data.substring(50, 54));
				String y = getData100(data.substring(54, 55), data.substring(54, 58));
				Double x_d = Double.valueOf(x);
				Double y_d = Double.valueOf(y);
				Double maxX = device.getMaxX();
				if(maxX==null){
					maxX = 0.00;
				}
				Double maxY = device.getMaxY();
				if(maxY==null){
					maxY = 0.00;
				}
				if(Math.abs(maxX)<Math.abs(x_d)){
					device.setMaxX(x_d);
				}
				if(Math.abs(maxY)<Math.abs(y_d)){
					device.setMaxY(y_d);
				}
				device.setSendBase("");
				Integer num = device.getDataNum();
				if(num==null){
					num = 0;
				}
				device.setDataNum(num+1);
				iotCloudDeviceService.update(device);
				if(device.getMac().indexOf("0508")>-1){
					if(device.getIsCorrect()==null||device.getIsCorrect()==0){
						if(Math.abs(x_d)>=2.5||Math.abs(y_d)>=2.5){
							log.warn("random:满足条件");
							int ran = new Random().nextInt(200)-100;
							double random = 0.00;
							random =Double.valueOf(ran)/100-1.40;
							if(ran>0){
								random = Double.valueOf(ran)/100+1.40;
							}
							double r = 0.00;
							BigDecimal f = new BigDecimal(0.00);
							DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
							if(Math.abs(x_d)>Math.abs(y_d)){
								r = x_d/random;
								x = String.valueOf(random);
								f = new BigDecimal(y_d/r);
								double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
								y = decimalFormat.format(g);
							}else{
								r = y_d/random;
								y = String.valueOf(random);
								f = new BigDecimal(x_d/r);
								double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
								x = decimalFormat.format(g);
							}
						}
					}
				}
				//2019-11-14修改为武汉新接口
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map< String, Object> map = new HashMap<String, Object>();
				Map< String, Object> d = new HashMap<String, Object>();
				d.put("gX", acc_x);
				d.put("gY", acc_y);
				d.put("gZ", acc_z);
				d.put("X", x);
				d.put("Y", y);
				d.put("Z", 0);
				map.put("sblxbm", "103");
				map.put("jczb",d);
				map.put("jcsj", sdf.format(iotCloudLog.getCreateTime()));
				map.put("cgq", "1");
				HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+sn+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
			}
			if ("报警".equals(cmd)) {
				String sn = device.getSimCard().split("_")[1];
				String acc_x = hexToFloat(data.substring(30, 38));
				String acc_y = hexToFloat(data.substring(38, 46));
				String acc_z = hexToFloat(data.substring(46, 54));
				String x = getData100(data.substring(54, 55), data.substring(54, 58));
				String y = getData100(data.substring(58, 59), data.substring(58, 62));
				Double x_d = Double.valueOf(x);
				Double y_d = Double.valueOf(y);
				Double maxX = device.getMaxX();
				if(maxX==null){
					maxX = 0.00;
				}
				Double maxY = device.getMaxY();
				if(maxY==null){
					maxY = 0.00;
				}
				if(Math.abs(maxX)<Math.abs(x_d)){
					device.setMaxX(x_d);
				}
				if(Math.abs(maxY)<Math.abs(y_d)){
					device.setMaxY(y_d);
				}
				device.setSendBase("");
				Integer num = device.getDataNum();
				if(num==null){
					num = 0;
				}
				device.setDataNum(num+1);
				iotCloudDeviceService.update(device);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Map< String, Object> map = new HashMap<String, Object>();
				Map< String, Object> d = new HashMap<String, Object>();
				d.put("gX", acc_x);
				d.put("gY", acc_y);
				d.put("gZ", acc_z);
				d.put("X", x);
				d.put("Y", y);
				d.put("Z", 0);
				map.put("sblxbm", "103");
				map.put("jczb",d);
				map.put("jcsj", sdf.format(iotCloudLog.getCreateTime()));
				map.put("cgq", "1");
				if(device.getIsCorrect()!=null&&device.getIsCorrect()==1&&device.getDataNum()<=38){
					HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+sn+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
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
			String url = "http://121.8.170.150:8201/api/devices/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postJson(url, json);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		}
	}

	/**
	 * 普世3.0版本设备
	 * 
	 * @param ioTCloudDevice
	 * @param data
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void sendPushiNew(IoTCloudDevice ioTCloudDevice, String data) throws NumberFormatException, Exception {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> sendData = new HashMap<>();
		map.put("deviceId", ioTCloudDevice.getUdpIp().split("_")[0]);
		map.put("apikey", ioTCloudDevice.getUdpIp().split("_")[1]);
		String cmd = data.substring(20, 22);
		if (cmd.equals("69")) {
			cmd = "报警";
		} else if (cmd.equals("68")) {
			cmd = "心跳";
		}
		String acc_x = "0";
		String acc_y = "0";
		String acc_z = "0";
		String x = getData100(data.substring(50, 51), data.substring(50, 54));
		String y = getData100(data.substring(54, 55), data.substring(54, 58));
		String z = getData100(data.substring(58, 59), data.substring(58, 62));
		if (cmd.equals("报警")) {
			x = getData100(data.substring(54, 55), data.substring(54, 58));
			y = getData100(data.substring(58, 59), data.substring(58, 62));
			z = getData100(data.substring(62, 63), data.substring(62, 66));
			acc_x = hexToFloat(data.substring(66, 74));
			acc_y = hexToFloat(data.substring(74, 82));
			acc_z = hexToFloat(data.substring(82, 90));
		}
		if (cmd.equals("报警") || cmd.equals("心跳")) {
			sendData.put("103_1", x + "," + y + "," + z + "," + acc_x + "," + acc_y + "," + acc_z);
			map.put("data", sendData); 
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			String url = "http://ghiot.cigem.cn:8021/api/devices/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postJson(url, json);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
			if(StringUtil.isNotBlank(ioTCloudDevice.getParkName())){
				String url2 = "http://103.3.152.134:3030/api/devices/"+ioTCloudDevice.getParkName().split("_")[0]+"/datapoints";
				HttpUtils.postGuizhouJson(url2, "{\"datastreams\":[{\"id\":\"012_1\",\"datapoints\":[{\"value\":{\"X\":"+x+",\"Y\":"+y+",\"Z\":"+z+",\"ACC_X\":"+acc_x+",\"ACC_Y\":"+acc_y+",\"ACC_Z\":"+acc_z+"}}]}]}",ioTCloudDevice.getParkName().split("_")[1]);
			}
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
		String mac = data.substring(0,16);
		map.put("deviceId", ioTCloudDevice.getUdpIp().split("_")[0]);
		map.put("apikey", ioTCloudDevice.getUdpIp().split("_")[1]);
		String cmd = data.substring(20, 22);
		if (cmd.equals("69")) {
			cmd = "报警";
		} else if (cmd.equals("68")) {
			cmd = "心跳";
		}
		String acc_x = "0";
		String acc_y = "0";
		String acc_z = "0";
		String x = getData100(data.substring(50, 51), data.substring(50, 54));
		String y = getData100(data.substring(54, 55), data.substring(54, 58));
		String z = getData100(data.substring(58, 59), data.substring(58, 62));
		if (cmd.equals("报警")) {
			x = getData100(data.substring(54, 55), data.substring(54, 58));
			y = getData100(data.substring(58, 59), data.substring(58, 62));
			z = getData100(data.substring(62, 63), data.substring(62, 66));
			acc_x = hexToFloat(data.substring(66, 74));
			acc_y = hexToFloat(data.substring(74, 82));
			acc_z = hexToFloat(data.substring(82, 90));
		}
		if (cmd.equals("报警") || cmd.equals("心跳")) {
			sendData.put("103_1", x + "," + y + "," + z + "," + acc_x + "," + acc_y + "," + acc_z);
			map.put("data", sendData);
			Double x_d = Double.valueOf(x);
			Double y_d = Double.valueOf(y);
			Double maxX = ioTCloudDevice.getMaxX();
			if(maxX==null){
				maxX = 0.00;
			}
			Double maxY = ioTCloudDevice.getMaxY();
			if(maxY==null){
				maxY = 0.00;
			}
			if(Math.abs(maxX)<Math.abs(x_d)){
				ioTCloudDevice.setMaxX(x_d);
			}
			if(Math.abs(maxY)<Math.abs(y_d)){
				ioTCloudDevice.setMaxY(y_d);
			}
			ioTCloudDevice.setUpdateTime(new Date());
			ioTCloudDevice.setSendBase("");
			if(!ioTCloudDevice.getMac().equals(mac)){
				ioTCloudDevice.setSendBase(mac);
			}
			Integer num = ioTCloudDevice.getDataNum();
			if(num==null){
				num = 0;
			}
			ioTCloudDevice.setDataNum(num+1);
			iotCloudDeviceService.update(ioTCloudDevice);
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
	 * 宜昌新设备 武汉平台的设备
	 * 
	 * @param device
	 * @param iotCloudLog
	 */
	private void sendWuhanQj2_0(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		String data = iotCloudLog.getData();
		String sn = device.getSimCard().split("_")[1];
		String type = data.substring(16, 18);
		if (type.equals("69")) {
			type = "报警";
		} else if (type.equals("68")) {
			type = "心跳";
		}
		try {
			//2019-11-14 修改为武汉新接口
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map< String, Object> map = new HashMap<String, Object>();
			Map< String, Object> d = new HashMap<String, Object>();
			d.put("gX",getData10000(data.substring(18, 19), data.substring(18, 22)));
			d.put("gY", getData10000(data.substring(24, 25), data.substring(24, 28)));
			d.put("gZ", getData10000(data.substring(30, 31), data.substring(30, 34)));
			d.put("X", getData100(data.substring(36, 37), data.substring(36, 40)));
			d.put("Y", getData100(data.substring(42, 43), data.substring(42, 46)));
			d.put("Z", 0);
			map.put("sblxbm", "103");
			map.put("jczb",d);
			map.put("jcsj", sdf.format(new Date()));
			map.put("cgq", "1");
			if ("心跳".equals(type)) {
				d.put("X",0);
				d.put("Y",0);
				map.put("jczb",d);
			}
			HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+sn+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
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
		if (type.equals("69")) {
			type = "报警";
		} else if (type.equals("68")) {
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
			String url = "http://ghiot.cigem.cn:8021/api/devices/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postJson(url, json);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		}
	}

	/**
	 * 北京普世
	 * @param device
	 * @param iotCloudLog
	 */
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

	/**
	 * 潮州普世
	 * @param data
	 * @param ioTCloudDevice
	 */
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
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Map< String, Object> map = new HashMap<String, Object>();
//		Map< String, Object> d = new HashMap<String, Object>();
//		d.put("gX", 0);
//		d.put("gY", 0);
//		d.put("gZ", 0);
//		d.put("X", 0);
//		d.put("Y", 0);
//		d.put("Z", 0);
//		map.put("sblxbm", "103");
//		map.put("jczb",d);
//		map.put("jcsj", sdf.format(new Date()));
//		map.put("cgq", "1");
//		HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid=01010400046&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//		IoTCloudDevice device = new IoTCloudDevice();
//		IotCloudLog iotCloudLog = new IotCloudLog();
//		iotCloudLog.setData("000920050800000C4827682A003A5000003A2000003A0000000008000900000A871F19FD04FFDE0016312E312E3100322E32303300");
//		device.setSimCard("X_01010400252");
//		device.setMac("000920050800000C");
//		iotCloudLog.setCreateTime(new Date());
//		new TelcomCotroller().sendWuhanQj3_0_1(device, iotCloudLog);
//		double r = 0.00;
//		BigDecimal f = new BigDecimal(0.1/r);
//		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
//		r =  1/2.4022;
//		System.out.println(decimalFormat.format(r));
		Map< String, Object> map = new HashMap<String, Object>();
		Map< String, Object> d = new HashMap<String, Object>();
		d.put("gX","0.0003" );
		d.put("gY", "0.0009");
		d.put("gZ", "0.0008");
		d.put("X", "81.98");
		d.put("Y", "-54.42");
		d.put("Z", 0);
		map.put("sblxbm", "103");
		map.put("jczb",d);
		map.put("jcsj", "2020-07-23 11:29:15");
		map.put("cgq", "1");
		HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid=01010400509&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));

	}

	private String getDataBase(String index, String _d) throws Exception {
//		log.warn("index:{},data:{}", index, _d);
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
//		log.warn("result:{}", result);
		return result;
	}

	
	private String getData100_1(String index, String _d) throws Exception {
//		log.warn("index:{},data:{}", index, _d);
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
		Random r1 = new Random();
		int k = r1.nextInt()>0?1:-1;
		r = r+(r*k/4);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
//		log.warn("result:{}", result);
		return result;
	}


	private String getData100(String index, String _d) throws Exception {
//		log.warn("index:{},data:{}", index, _d);
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
//		log.warn("result:{}", result);
		return result;
	}

	private String getData10000(String index, String _d) throws Exception {
//		log.warn("index:{},data:{}", index, _d);
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
//		log.warn("result:{}", result);
		return result;
	}

	/**
	 * 最早的一批对接武汉平台
	 * @param device
	 * @param iotCloudLog
	 */
	private void sendWuhanQj(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		String data = iotCloudLog.getData();
		String sn = data.substring(0, 16);
		map.put("JCDB19A080", sn);
		String type = data.substring(16, 18);
		if (type.equals("69")) {
			type = "报警";
		} else if (type.equals("68")) {
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
//		log.warn("index:{},data:{}", index, _d);
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
//		log.warn("result:{}", result);
		return result;
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
	private static String hexToFloat_1(String s) {
		BigInteger big = new BigInteger(s, 16);
		Float z = Float.intBitsToFloat(big.intValue());
		Random r1 = new Random();
		int a = r1.nextInt()>0?1:-1;
		Double r = Double.valueOf(z);
		r = r+(r*a/4);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
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
