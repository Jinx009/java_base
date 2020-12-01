package main.entry.webapp.mqtt;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import database.model.GnssRtkDevice;
import service.GnssRtkDeviceService;

@Component
public class DataManager {

	private static final Logger log = LoggerFactory.getLogger(DataManager.class);
	
	@Resource
	private GnssRtkDeviceService gnssRtkDeviceService;

	private static DataManager dm;
	
	//
	public  void init(){
		dm = this;
		dm.gnssRtkDeviceService = this.gnssRtkDeviceService;
	}
	
	public static final String TOPIC = "/server/register";
	public static MqttClient client;
	public static MqttConnectOptions options;
	public static int Qos = 1;// 0：最多一次 、1：最少一次 、2：只有一次

	/**
	 * 用来连接服务器
	 */
	@PostConstruct
	public void start() {
		try {
			init();
			client = new MqttClient(MqttUtils.HOST, MqttUtils.SERVER_CLINETID, new MemoryPersistence());
			options = new MqttConnectOptions();
			options.setCleanSession(false);
			options.setUserName(MqttUtils.USERNAME);
			options.setPassword(MqttUtils.PASSWORD.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			client.setCallback(new PushCallback());
			client.connect(options);
			client.subscribe(TOPIC, Qos);
			List<GnssRtkDevice> list = dm.gnssRtkDeviceService.findAll();
			if(list!=null&&!list.isEmpty()) {
				for(GnssRtkDevice d : list) {
					StringBuilder sb = new StringBuilder();
					sb.append("/device/");
					sb.append(d.getMac());
					sb.append("/");
					log.warn("sub mac:{}", d.getMac());
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
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}
	
	public static void sendMessage(int qos,byte[] b,String topicStr) throws Exception {
		MqttTopic topic = client.getTopic(topicStr);
		MqttMessage message = new MqttMessage();
		message.setQos(qos); 
		message.setRetained(true);
		message.setPayload(b);
		try {
			publish(topic, message);
		} catch (Exception e) {
			client.disconnect();
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param topic
	 * @param message
	 * @throws MqttPersistenceException
	 * @throws MqttException
	 */
	public static void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
		MqttDeliveryToken token = topic.publish(message);
		token.waitForCompletion();
	}

}
