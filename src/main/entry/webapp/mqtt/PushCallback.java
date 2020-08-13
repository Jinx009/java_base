package main.entry.webapp.mqtt;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class PushCallback implements MqttCallback {
 
    public void connectionLost(Throwable cause) {
 
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，可以做重连");
    }
 
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
 
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
    	String pay = new String(message.getPayload());
//        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " +(new Date().getTime())+"_"+pay);
        System.out.println("接收消息内容 : " +(new Date().getTime()-Long.valueOf(pay)));
    }


}
