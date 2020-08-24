package main.entry.webapp.mqtt;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DataManager {

	private static final Logger log = LoggerFactory.getLogger(DataManager.class);

	public static final String TOPIC = "/server/register";

	
	private static DataManager dm;
	public static MqttClient client;
	public static MqttConnectOptions options;
	
	@PostConstruct
	public  void init(){
		dm = this;
	}

	/**
	 * 用来连接服务器
	 */
	@PostConstruct
	public void start() {
		try {
			client = new MqttClient(MqttUtils.HOST, MqttUtils.SERVER_CLINETID, new MemoryPersistence());
			options = new MqttConnectOptions();
			options.setCleanSession(false);
			options.setUserName(MqttUtils.USERNAME);
			options.setPassword(MqttUtils.PASSWORD.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
//         options.setWill(topic3, "This is ".getBytes(), 2, true);
			client.setCallback(new PushCallback());
			client.connect(options);
			int[] Qos = { 1 };// 0：最多一次 、1：最少一次 、2：只有一次
			String[] topic = { TOPIC };
			client.subscribe(topic, Qos);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

}
