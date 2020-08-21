package main.entry.webapp.mqtt;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PushCallback implements MqttCallback {
 
	private static final Logger log = LoggerFactory.getLogger(PushCallback.class);
	
	
    public void connectionLost(Throwable cause) {
        log.warn("连接断开");
    }
 
    public void deliveryComplete(IMqttDeliveryToken token) {
    	log.warn("token:{}",token.getClient().getClientId());
//    	log.warn("deliveryComplete---------" + token.isComplete());
    }
 
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
    	String pay = new String(message.getPayload());
    	log.warn("msg:{},{}",pay,message.getId());
    }


}
