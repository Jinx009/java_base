package main.entry.webapp.mqtt;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClinet {

	public static final String HOST = "tcp://139.196.13.251:1883";
	public static final String TOPIC1 = "test1";
	private static final String clientid = "12345678";
	private MqttClient client;
	private MqttConnectOptions options;
	private String userName = "mqtt"; // 非必须
	private String passWord = "mqtt"; // 非必须
	private void start() {
		try {
			// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient(HOST, clientid, new MemoryPersistence());
			// MQTT的连接设置
			options = new MqttConnectOptions();
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(false);
			// 设置连接的用户名
			options.setUserName(userName);
			// 设置连接的密码
			options.setPassword(passWord.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(10);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(20);
			// 设置断开后重新连接
			options.setAutomaticReconnect(true);
			// 设置回调
			client.setCallback(new PushCallback());
			MqttTopic topic = client.getTopic(TOPIC1);
			// setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
			// 遗嘱
			options.setWill(topic, "close".getBytes(), 1, true);
			client.connect(options);
			// 订阅消息
			int[] Qos = { 0 };// 0：最多一次 、1：最少一次 、2：只有一次
//			String[] topic1 = { TOPIC1 };
//			client.subscribe(topic1, Qos);
			for(int i = 0 ;i<30;i++){
				sendMessage(null, client);
			}
			client.disconnect();
//			new Thread() {
//				public void run() {
//					while (true) {
//							try {
//								sendMessage(null, client);
//								System.out.println(""+new Date().getTime());
//								Thread.sleep(1);
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//					}
//				}
//			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
		MqttDeliveryToken token = topic.publish(message);

		token.waitForCompletion();
//		System.out.println("message is published completely! " + token.isComplete());
	}

	public static void sendMessage(byte[] b, MqttClient client) throws Exception {
		MqttMessage message = new MqttMessage();
		message.setQos(0); // 保证消息能到达一次
		message.setRetained(true);
		String str = String.valueOf(new Date().getTime());
		message.setPayload(str.getBytes());
		try {
			publish(client.getTopic(TOPIC1), message);
			// 断开连接
			// 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MqttClinet client = new MqttClinet();
		client.start();

	}

}
