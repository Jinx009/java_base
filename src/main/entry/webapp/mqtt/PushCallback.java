package main.entry.webapp.mqtt;

import java.util.Date;
import java.util.List;

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

import common.helper.StringUtil;
import database.model.GnssRtkControl;
import database.model.GnssRtkDevice;
import database.model.GnssRtkTopic;
import service.GnssRtkControlService;
import service.GnssRtkDeviceService;
import service.GnssRtkTopicService;

@Component
public class PushCallback implements MqttCallback {

	private static final Logger log = LoggerFactory.getLogger(PushCallback.class);

	@Resource
	private GnssRtkDeviceService gnssRtkDeviceService;
	@Resource
	private GnssRtkTopicService gnssRtkTopicService;
	@Resource
	private GnssRtkControlService gnssRtkControlService;

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
				}else {
					List<GnssRtkTopic> list = pu.gnssRtkTopicService.list(payload);
					if(list!=null&&!list.isEmpty()) {
						StringBuilder sb = new StringBuilder();
						GnssRtkControl grc = new GnssRtkControl();
						grc.setCmd("33");
						grc.setCreateTime(new Date());
						grc.setMac(payload);
						grc.setResultStr("");
						grc.setStatus(0);
						grc = pu.gnssRtkControlService.save(grc);
						String sn = StringUtil.getMore(Integer.toHexString(grc.getId()));
						grc.setCmdName("设置gnss订阅/退订主题设定");
						sb.append("4800");
						sb.append(sn);
						sb.append("33");
						String ac = StringUtil.stringToA(topic);
						sb.append(topic.length()+1);
						sb.append("01");
						sb.append(ac);
						String content = sb.toString();
						grc.setContent(content);
						pu.gnssRtkControlService.update(grc);
						MqttUtils.sendCmd(1,content,payload);
					}
				}
			}
			if(topic.indexOf("device")>-1){
				String[] strs = topic.split("/");
//				String mac = strs[2];
				String t = strs[3];
				if(t.equals("control")) {
					String id = payload.substring(4,12);
					long dec_num = Long.parseLong(id, 16);
					GnssRtkControl grc = pu.gnssRtkControlService.find((int)dec_num);
					if(grc!=null) {
						grc.setResultStr(payload);
						grc.setStatus(1);
						pu.gnssRtkControlService.update(grc);
					}
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}
	

}
