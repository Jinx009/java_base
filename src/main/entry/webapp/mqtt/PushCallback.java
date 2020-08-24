package main.entry.webapp.mqtt;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import database.model.GnssRtkDevice;
import database.model.GnssRtkLog;
import service.GnssRtkDeviceService;

@Component
public class PushCallback implements MqttCallback {

	private static final Logger log = LoggerFactory.getLogger(PushCallback.class);

	@Resource
	private GnssRtkDeviceService gnssRtkDeviceService;

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
				// client.subscribe(topic, qos);
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
		String pay = new String(message.getPayload());
		log.warn("msg:{},topic:{}", pay, topic);
		try {
			if (topic.equals("/server/register")) {
				GnssRtkDevice gnssDevice = pu.gnssRtkDeviceService.findByRoverTag(pay);
				if (gnssDevice == null) {
					gnssDevice = new GnssRtkDevice();
					gnssDevice.setMac(pay);
					gnssDevice.setCreateTime(new Date());
					pu.gnssRtkDeviceService.save(gnssDevice);
				}
				client.subscribe("/gnss/"+pay+"/#", 1);
			}
			if(topic.indexOf("gnss")>-1){
				String[] strs = topic.split("/");
				String mac = strs[1];
				String t = strs[2];
				if(t.equals("PVT")){
					String s = pay;
					String fixTypeStr = s.substring(54, 56);
					String fixStatusStr = s.substring(56, 58);
					String numStr = s.substring(60, 62);
					String lngStr = s.substring(62, 70);
					String latStr = s.substring(70, 78);
					String height = s.substring(78, 86);
					String hmsl = s.substring(86, 94);
					String horAccStr = s.substring(94,102);
					String verAccStr = s.substring(102, 110);
					String yearStr = s.substring(22,26);
					String monthStr = s.substring(26, 28);
					String dayStr = s.substring(28, 30);
					String hourStr = s.substring(30, 32);
					String minStr = s.substring(32,34);
					String secStr = s.substring(34, 36);
					String dataTime = getHex4(yearStr) + "-" + Integer.valueOf(monthStr, 16) + "-"
							+ Integer.valueOf(dayStr, 16) + " " + (Integer.valueOf(hourStr, 16) + 8) + ":"
							+ Integer.valueOf(minStr, 16) + ":" + Integer.valueOf(secStr, 16);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}
	
	private String getHex4(String str) {// 0DE0FE43-A7C52512
		String s1 = str.substring(2, 4);
		String s2 = str.substring(0, 2);
		String str2 = s1 + s2;
		return Integer.valueOf(str2, 16).toString();
	}

}
