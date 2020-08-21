package main.entry.webapp.mqtt;

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

public class CmdManager {

	private static final Logger log = LoggerFactory.getLogger(CmdManager.class);

	public static final String TOPIC = "/cmd/";

	/**
	 * 用来连接服务器
	 */
	public static void connect(String cmd, String mac) {
		MqttClient client = null;
		try {
			client = new MqttClient(MqttUtils.HOST, MqttUtils.SERVER_CLINETID, new MemoryPersistence());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(false);
			options.setUserName(MqttUtils.USERNAME);
			options.setPassword(MqttUtils.PASSWORD.toCharArray());
			options.setConnectionTimeout(10);
			options.setKeepAliveInterval(20);
			client.setCallback(new PushCallback());
			MqttTopic topic = client.getTopic(TOPIC + mac);
			options.setWill(topic, "close".getBytes(), 1, true);
			client.connect(options);
			log.warn("wait send:{},topic:/cmd/{}",cmd,mac);
			sendMessage(cmd.getBytes(), client, topic);
		} catch (Exception e) {
			try {
				client.disconnect();
			} catch (MqttException e1) {
				log.error("e:{}", e1);
			}
		}
	}

	public static void sendMessage(byte[] b, MqttClient client, MqttTopic topic) throws Exception {
		MqttMessage message = new MqttMessage();
		message.setQos(1); // 保证消息能到达一次
		message.setRetained(true);
		message.setPayload(b);
		try {
			publish(topic, message);
			client.disconnect();
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
//		log.warn("message is published completely!{} ", token.isComplete());
	}

	public static void main(String[] args) {
		connect("2891021122", "10086");
	}

}
