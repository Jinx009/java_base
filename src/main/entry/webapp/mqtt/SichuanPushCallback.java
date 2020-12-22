package main.entry.webapp.mqtt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import database.model.GnssRtkDevice;
import service.GnssRtkDeviceService;


public class SichuanPushCallback  implements MqttCallback {

	private static final Logger log = LoggerFactory.getLogger(PushCallback.class);

	@Resource
	private GnssRtkDeviceService gnssRtkDeviceService;
	

	private static SichuanPushCallback pu;
	private MqttClient client = SichuanDataManager.client;
	private MqttConnectOptions options = SichuanDataManager.options;

	@PostConstruct
	public void init() {
		pu = this;
		pu.gnssRtkDeviceService = this.gnssRtkDeviceService;
	}

	public void connectionLost(Throwable cause) {
		log.info("连接断开……（可以做重连）");
		while (true) {
			try {
				log.info("连接失败重连");
				client.connect(options);
				StringBuilder sb = new StringBuilder();
				sb.append("$creq/");
				sb.append(SichuanMqttUtils.SERVER_CLINETID);
				sb.append("/cmd");
				client.subscribe(sb.toString());
				log.info("连接失败重连成功");
				break;
			} catch (MqttException e) {
				log.info("连接失败重连失败");
				log.info("connectionLost e:{}", e);
			}
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		log.warn("token:{}", token.getClient().getClientId());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// subscribe后得到的消息会执行到这里面
		String payload = new String(message.getPayload());
		try {
			Map<String, String> map = getMap(payload);
			String $cmd = map.get("$cmd");
			String msgid = map.get("msgid");
			if($cmd.equals("reqtime")) {//获取机器时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SichuanMqttUtils.sendCmd(1,"$cmd=reqtime&time="+sdf.format(new Date())+"&msgid="+msgid);
			}
			if($cmd.equals("settime")) {//校准时间
				SichuanMqttUtils.sendCmd(1,"$cmd=reqtime&result=succ&msgid="+msgid);
			}
			if($cmd.equals("settime")) {//校准时间
				SichuanMqttUtils.sendCmd(1,"$cmd=reqtime&result=succ&msgid="+msgid);
			}
		} catch (Exception e) {
			log.error("messageArrived e:{}", e);
		}
	}

	private static Map<String, String> getMap(String payload){
		Map<String, String> map = new HashMap<String, String>();
		String[] params = payload.split("&");
		for(String s:params) {
			map.put(s.split("=")[0], s.split("=")[1]);
		}
		return map;
	}
	
}
