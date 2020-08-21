package main.entry.webapp.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataManager {

	private static final Logger log = LoggerFactory.getLogger(DataManager.class);

	public static final String TOPIC = "/data/publish";
	
	 /**
     *  用来连接服务器
     */
    public static void connect() {
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
            client.connect(options);
            int[] Qos = { 1 };// 0：最多一次 、1：最少一次 、2：只有一次
			String[] topic = { TOPIC };
			client.subscribe(topic, Qos);
        } catch (Exception e) {
        	log.error("e:{}",e);
        	try {
				client.disconnect();
			} catch (Exception e2) {
				log.error("e2:{}",e2);
			}
        }
    }
    
    public static void main(String[] args) {
		new Thread(){
			public void run(){
				connect();
			}
		}.start();
	}
	
}
