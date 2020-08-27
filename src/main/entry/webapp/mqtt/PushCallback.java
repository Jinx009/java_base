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
		String payload = new String(message.getPayload());
		log.warn("payload:{},topic:{}", payload, topic);
		try {
			if (topic.equals("/server/register")) {
				GnssRtkDevice gnssDevice = pu.gnssRtkDeviceService.findByRoverTag(payload);
				if (gnssDevice == null) {
					gnssDevice = new GnssRtkDevice();
					gnssDevice.setMac(payload);
					gnssDevice.setCreateTime(new Date());
					pu.gnssRtkDeviceService.save(gnssDevice);
				}
				client.subscribe("/device/"+payload+"/#", 1);
			}
			if(topic.indexOf("device")>-1){
				String[] strs = topic.split("/");
				String mac = strs[1];
				String t = strs[2];
			
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
