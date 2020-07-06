package main.entry.webapp.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MqttServer {

	 //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public static final String HOST = "tcp://139.196.13.251:1883";
    //定义一个主题
    public static final String TOPIC = "test";
    public static final String TOPIC2 = "pos_message_sned";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientid = "server11";
 
    private MqttClient client;
    private static MqttTopic topic11;
//    private String userName = "mqtt";  //非必须
//    private String passWord = "mqtt";  //非必须
 
 
    /**
     * 构造函数
     * @throws MqttException
     */
    public MqttServer() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }
 
    /**
     *  用来连接服务器
     */
    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
//        options.setUserName(userName);
//        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        
        try {
            client.setCallback(new PushCallback());
            client.connect(options);
            topic11 = client.getTopic(TOPIC);
            int[] Qos = { 1 };// 0：最多一次 、1：最少一次 、2：只有一次
			String[] topic1 = { TOPIC2 };
			client.subscribe(topic1, Qos);
        } catch (Exception e) {
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
    public static void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
 
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }
 
 
    public static void sendMessage(String clieId,String msg)throws Exception{
    	MqttServer server = new MqttServer();
    	MqttMessage message = new MqttMessage();
        message.setQos(1);  //保证消息能到达一次
        message.setRetained(true);
        String str ="{\"clieId\":\""+clieId+"\",\"mag\":\""+msg+"\"}";
        message.setPayload(str.getBytes());
        try{
            publish(topic11 , message);
            //断开连接
//            server.client.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
 
    }
    /**
     *  启动入口
     * @param args
     * @throws MqttException
     */
    public static void main(String[] args) throws Exception {
        sendMessage("hello","哈哈");
    }

	
}
