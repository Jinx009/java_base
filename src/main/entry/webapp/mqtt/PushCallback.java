package main.entry.webapp.mqtt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import common.helper.StringUtil;
import database.model.GnssRtkAccLog;
import database.model.GnssRtkControl;
import database.model.GnssRtkDebugLog;
import database.model.GnssRtkDevice;
import database.model.GnssRtkErrLog;
import database.model.GnssRtkHeartLog;
import database.model.GnssRtkNumLog;
import database.model.GnssRtkTopic;
import service.GnssRtkAccLogService;
import service.GnssRtkControlService;
import service.GnssRtkDebugLogService;
import service.GnssRtkDeviceService;
import service.GnssRtkErrLogService;
import service.GnssRtkHeartLogService;
import service.GnssRtkNumLogService;
import service.GnssRtkTopicService;

@Component
public class PushCallback implements MqttCallback {

	private static final Logger log = LoggerFactory.getLogger(PushCallback.class);

	@Resource
	private GnssRtkDeviceService gnssRtkDeviceService;
	@Resource
	private GnssRtkTopicService gnssRtkTopicService;
	@Resource
	private GnssRtkControlService gnssRtkControlService;
	@Resource
	private GnssRtkHeartLogService gnssRtkHeartLogService;
	@Resource
	private GnssRtkNumLogService gnssRtkNumLogService;
	@Resource
	private GnssRtkErrLogService gnssRtkErrLogService;
	@Resource
	private GnssRtkDebugLogService gnssRtkDebugLogService;
	@Resource
	private GnssRtkAccLogService gnssRtkAccLogService;
	

	private static PushCallback pu;
	private MqttClient client = DataManager.client;
	private MqttConnectOptions options = DataManager.options;

	@PostConstruct
	public void init() {
		pu = this;
	}

	public void connectionLost(Throwable cause) {
		log.info("连接断开……（可以做重连）");
		while (true) {
			try {
				log.info("连接失败重连");
				client.connect(options);
				log.info("连接失败重连成功");
				break;
			} catch (MqttException e) {
				log.info("连接失败重连失败");
				log.info("e:{}", e);
			}
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		log.warn("token:{}", token.getClient().getClientId());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// subscribe后得到的消息会执行到这里面
		String payload = new String(message.getPayload());
		log.warn("payload:{},topic:{}", payload, topic);
		try {
			if (topic.equals("/server/register")) {//设备注册报文
				GnssRtkDevice gnssDevice = pu.gnssRtkDeviceService.findByRoverTag(payload);
				if (gnssDevice == null) {
					gnssDevice = new GnssRtkDevice();
					gnssDevice.setMac(payload);
					gnssDevice.setCreateTime(new Date());
					pu.gnssRtkDeviceService.save(gnssDevice);
				}else {
					List<GnssRtkTopic> list = pu.gnssRtkTopicService.list(payload);
					if(list!=null&&!list.isEmpty()) {
						StringBuilder sb = new StringBuilder();
						GnssRtkControl grc = new GnssRtkControl();
						grc.setCmd("33");
						grc.setCreateTime(new Date());
						grc.setMac(payload);
						grc.setResultStr("");
						grc.setStatus(0);
						grc = pu.gnssRtkControlService.save(grc);
						String sn = StringUtil.getMore(Integer.toHexString(grc.getId()));
						grc.setCmdName("设置gnss订阅/退订主题设定");
						sb.append("4800");
						sb.append(sn);
						sb.append("33");
						String ac = StringUtil.stringToA(topic);
						sb.append(topic.length()+1);
						sb.append("01");
						sb.append(ac);
						String content = sb.toString();
						grc.setContent(content);
						pu.gnssRtkControlService.update(grc);
						MqttUtils.sendCmd(1,content,payload);
					}
				}
			}
			if(topic.indexOf("device")>-1){
				String[] strs = topic.split("/");
				String mac = strs[2];
				String t = strs[3];
				if(t.equals("control")) {//下行命令回复
					String id = payload.substring(4,12);
					long dec_num = Long.parseLong(id, 16);
					GnssRtkControl grc = pu.gnssRtkControlService.find((int)dec_num);
					if(grc!=null) {
						grc.setResultStr(payload);
						grc.setStatus(1);
						pu.gnssRtkControlService.update(grc);
					}
				}
				if(t.equals("heartbeat")) {//心跳报文
					GnssRtkHeartLog log = new GnssRtkHeartLog();
					log.setBaseData(payload);
					log.setMac(mac);
					log.setCreateTime(new Date());
					pu.gnssRtkHeartLogService.save(log);
				}
				if(t.equals("RTCM")||t.equals("UBX")||t.equals("NMEA")) {//转发报文
					try {
						Date d = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
						String date = sdf.format(d);
						int startHour = Integer.parseInt(sdf2.format(d));
						GnssRtkNumLog log = pu.gnssRtkNumLogService.find(date,mac, startHour);
						if(log!=null) {
							log.setNum(log.getNum()+1);
							pu.gnssRtkNumLogService.update(log);
						}else {
							log = new GnssRtkNumLog();
							log.setCreateTime(new Date());
							log.setDate(date);
							log.setStartHour(startHour);
							log.setEndHour(startHour+1);
							log.setNum(1);
							log.setMac(mac);
							log.setType(t);
							pu.gnssRtkNumLogService.save(log);
						}
					} catch (Exception e) {
						log.error("e:{},topic:{},mac:{}",e,t,mac);
					}
				}
				if(t.equals("errlog")) {//错误日志报文
					try {
						GnssRtkErrLog log = new GnssRtkErrLog();
						log.setBaseData(payload);
						log.setCreateTime(new Date());
						log.setMac(mac);
						log.setErrorContent(StringUtil.convertHexToString(payload));
						pu.gnssRtkErrLogService.save(log);
					} catch (Exception e) {
						log.error("e:{},topic:{},mac:{}",e,t,mac);
					}
				}
				if(t.equals("slope&acc")) {//错误日志报文
					try {
						GnssRtkAccLog log = new GnssRtkAccLog();
						double accX = StringUtil.hexToFloat(payload.substring(24,32));
						double accY = StringUtil.hexToFloat(payload.substring(32,40));
						double accZ = StringUtil.hexToFloat(payload.substring(40,48));
						double angleX = StringUtil.hexToFloat(payload.substring(48,56));
						double angleY = StringUtil.hexToFloat(payload.substring(56,64));
						double angleZ = StringUtil.hexToFloat(payload.substring(64,72));
						int flag = Integer.valueOf(payload.substring(18,20));
						int alarmtype = Integer.valueOf(payload.substring(20,22));
						String alarmvalue = StringUtil.getB(payload.substring(22, 24));
						double accXP2p = StringUtil.hexToFloat(payload.substring(72,80));
						double accYP2p = StringUtil.hexToFloat(payload.substring(80,88));
						double accZP2p = StringUtil.hexToFloat(payload.substring(88,96));
						double shockStrength = StringUtil.hexToFloat(payload.substring(96,104));
						log.setAccX(accX);
						log.setAccXP2p(accXP2p);
						log.setAccY(accY);
						log.setAccYP2p(accYP2p);
						log.setAccZ(accZ);
						log.setAccZP2p(accZP2p);
						log.setAlarmtype(alarmtype);
						log.setAlarmvalue(alarmvalue);
						log.setAngleX(angleX);
						log.setAngleY(angleY);
						log.setAngleZ(angleZ);
						log.setCreateTime(new Date());
						log.setFlag(flag);
						log.setShockStrength(shockStrength);
						pu.gnssRtkAccLogService.save(log);
					} catch (Exception e) {
						log.error("e:{},topic:{},mac:{}",e,t,mac);
					}
				}
				if(t.equals("debug")) {//错误日志报文
					try {
						GnssRtkDebugLog log = new GnssRtkDebugLog();
						log.setBaseData(payload);
						log.setCreateTime(new Date());
						log.setDebugContent(StringUtil.convertHexToString(payload));
						log.setMac(mac);
						pu.gnssRtkDebugLogService.save(log);
					} catch (Exception e) {
						log.error("e:{},topic:{},mac:{}",e,t,mac);
					}
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}
	

}
