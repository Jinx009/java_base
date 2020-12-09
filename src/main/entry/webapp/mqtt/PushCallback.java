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
import database.model.GnssRtkControl;
import database.model.GnssRtkDevice;
import database.model.GnssRtkNumLog;
import database.model.GnssRtkTopic;
import service.GnssMsgLogService;
import service.GnssRtkAccLogService;
import service.GnssRtkConnLogService;
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
	@Resource
	private GnssRtkConnLogService gnssRtkConnLogService;
	@Resource
	private GnssMsgLogService gnssMsgLogService;
	

	private static PushCallback pu;
	private MqttClient client = DataManager.client;
	private MqttConnectOptions options = DataManager.options;

	@PostConstruct
	public void init() {
		pu = this;
		pu.gnssRtkDeviceService = this.gnssRtkDeviceService;
		pu.gnssRtkTopicService = this.gnssRtkTopicService;
		pu.gnssRtkControlService = this.gnssRtkControlService;
		pu.gnssRtkHeartLogService = this.gnssRtkHeartLogService;
		pu.gnssRtkNumLogService = this.gnssRtkNumLogService;
		pu.gnssRtkErrLogService = this.gnssRtkErrLogService;
		pu.gnssRtkDebugLogService = this.gnssRtkDebugLogService;
		pu.gnssRtkAccLogService = this.gnssRtkAccLogService;
		pu.gnssRtkConnLogService = this.gnssRtkConnLogService;
		pu.gnssMsgLogService = this.gnssMsgLogService;
	}

	public void connectionLost(Throwable cause) {
		log.info("连接断开……（可以做重连）");
		while (true) {
			try {
				log.info("连接失败重连");
				client.connect(options);
				client.subscribe(DataManager.TOPIC, DataManager.Qos);
				List<GnssRtkDevice> list = pu.gnssRtkDeviceService.findAll();
				if(list!=null&&!list.isEmpty()) {
					for(GnssRtkDevice d : list) {
						StringBuilder sb = new StringBuilder();
						sb.append("/device/");
						sb.append(d.getMac());
						sb.append("/");
						log.warn("subscribe mac:{}", d.getMac());
						client.subscribe(sb.toString()+"control", 0);
						client.subscribe(sb.toString()+"RTCM", 0);
						client.subscribe(sb.toString()+"UBX", 0);
						client.subscribe(sb.toString()+"NMEA", 0);
						client.subscribe(sb.toString()+"slope&acc", 0);
						client.subscribe(sb.toString()+"debug", 0);
						client.subscribe(sb.toString()+"heartbeat", 0);
						client.subscribe(sb.toString()+"errlog", 0);
					}
				}
				log.info("连接失败重连成功");
				break;
			} catch (MqttException e) {
				log.info("连接失败重连失败");
				log.info("connectionLost e:{}", e);
			}
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		log.warn("token:{}", token.getClient().getClientId());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// subscribe后得到的消息会执行到这里面
		String payload = new String(message.getPayload());
		byte[] bytes = message.getPayload();
		String str = "";
		for(byte b:bytes) {
			String s1 = Integer.toHexString(b);
			if(s1.length()==1) {
				str+=  "0"+s1;
			}else {
				str+=s1;
			}
		}
		payload = str.replace(" ", "").replace("ffffff", "");
		try {
			if (topic.equals("/server/register")) {//设备注册报文
				GnssRtkDevice gnssDevice = pu.gnssRtkDeviceService.findByMac(payload);
				if (gnssDevice == null) {
					pu.gnssRtkDeviceService.saveDevice(payload);
					StringBuilder sb = new StringBuilder();
					sb.append("/device/");
					sb.append(payload);
					sb.append("/");
					log.warn("sub mac:{}",payload);
					client.subscribe(sb.toString()+"control", 1);
					client.subscribe(sb.toString()+"RTCM", 0);
					client.subscribe(sb.toString()+"UBX", 0);
					client.subscribe(sb.toString()+"NMEA", 0);
					client.subscribe(sb.toString()+"slope&acc", 0);
					client.subscribe(sb.toString()+"debug", 0);
					client.subscribe(sb.toString()+"heartbeat", 0);
					client.subscribe(sb.toString()+"errlog", 0);
					pu.gnssRtkConnLogService.saveConnLog(payload,0);
				}else {
					pu.gnssRtkConnLogService.saveConnLog(payload,1);
					List<GnssRtkTopic> list = pu.gnssRtkTopicService.list(payload);
					if(list!=null&&!list.isEmpty()) {
						StringBuilder sb = new StringBuilder();
						GnssRtkControl grc = pu.gnssRtkControlService.saveCmd("33",payload,0);
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
					log.warn("control:{}", payload);
					pu.gnssRtkControlService.updateGrc(payload,mac);
				}
				if(t.equals("heartbeat")) {//心跳报文
					log.warn("heart:{}", payload);
					pu.gnssRtkHeartLogService.saveHeartbeat(payload,mac);
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
						log.error("RTCM e:{},topic:{},mac:{}",e,t,mac);
					}
				}
				if(t.equals("errlog")) {//错误日志报文
					try {
						pu.gnssRtkErrLogService.saveErrlog(payload,mac);
					} catch (Exception e) {
						log.error("errlog e:{},topic:{},mac:{}",e,t,mac);
					}
				}
				if(t.equals("slope&acc")) {//错误日志报文
					try {
						log.warn("slope&acc:{},mac:{}",payload,mac);
						pu.gnssRtkAccLogService.saveAccLog(payload,mac);
					} catch (Exception e) {
						log.error("slop&acc e:{},topic:{},mac:{}",e,t,mac);
					}
				}
				if(t.equals("debug")) {//错误日志报文
					try {
						pu.gnssRtkDebugLogService.saveDebuglog(payload,mac);
					} catch (Exception e) {
						log.error("debug e:{},topic:{},mac:{}",e,t,mac);
					}
				}
			}
		} catch (Exception e) {
			log.error("messageArrived e:{}", e);
		}
	}
	

}
