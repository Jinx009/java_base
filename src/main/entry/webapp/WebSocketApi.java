package main.entry.webapp;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import database.models.AliParking;
import main.entry.webapp.task.StatusCheckTask;
import utils.HttpUtil;

import org.java_websocket.client.WebSocketClient;

public class WebSocketApi extends WebSocketClient{

private static final Logger logger = LoggerFactory.getLogger(WebSocketApi.class);
	

	public WebSocketApi(URI serverUri, Draft draft) {
		super(serverUri, draft);
	}

	public WebSocketApi(URI serverURI) {
		super(serverURI);
	}

	public void start() {
		
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		logger.warn("opened connection .......................");
	}

//	{
//		"transParams": {
//			"deviceId": 3,
//			"type": "IntelligentAlarmEvent",
//			"pictureUrl": "http://192.168.1.2:11005/static/images/T6_2020_05_15_15_56_11_913090.jpg",
//			"pictureId": "6b2020c1a7054225934d0dbceffbbc6a",
//			"cutoutUrl": "http://192.168.1.2:11005/static/images/0_T6_2020_05_15_15_56_11_913090.jpg",
//			"taskId": 6,
//			"videoUrl": ""
//		},
//		"payload": {
//			"AlarmType": 10009,
//			"Algorithm": "personVehicleDetect",
//			"ProductKey": "",
//			"DeviceName": "",
//			"Data": "{\"rect\": [0.5682, 0.1806, 0.176, 0.8176], \"alarmType\": 10009, \"eventTime\": 1589529371912, \"captureTime\": 1589529371912, \"trackId\": 23, \"conf\": 0.9655, \"frameIndex\": 121574, \"ossUrl\": \"\", \"ltlId\": \"\", \"inPosition\": 1, \"regionId\": -1, \"type\": \"person\", \"index\": 0}",
//			"AlarmPicID": "6b2020c1a7054225934d0dbceffbbc6a"
//		}
//	}
	
	@Override
	public void onMessage(String message) {
		logger.warn("received: {} ...............", message);
		JSONObject jobj = JSONObject.parseObject(message);
		String transParams = jobj.getString("transParams");
		String picUrl = JSONObject.parseObject(transParams).getString("pictureUrl");
		String payload = jobj.getString("payload");
		String data = JSONObject.parseObject(payload).getString("Data");
		JSONObject jdata = JSONObject.parseObject(data);
		String type = jdata.getString("type");
		String plateNumber = jdata.getString("plateNumber");
		String status = jdata.getString("status");
		String eventTime = jdata.getString("eventTime");
		String trackId = jdata.getString("trackId");
		StatusCheckTask.saveAliPic(picUrl,"ali/"+eventTime+"_"+trackId+".jpeg");
		String pu = "http://58.246.184.99:801/ali"+eventTime+"_"+trackId+".jpeg";
		AliParking ali = new AliParking();
		ali.setCreateTime(new Date());
		ali.setPlateNumber(plateNumber);
		ali.setStatus(status);
		ali.setType(type);
		ali.setEventTime(eventTime);
		ali.setPicUrl(pu);
		HttpUtil.postJson("http://localhost:8089/d/aibox/save", JSONObject.toJSONString(ali));
		String d = message;
		try {
			File file = new File("/data/ftp_pic/ali/ali.txt"); // 本地目录
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
				file.createNewFile();
			}
			FileOutputStream fops = new FileOutputStream(file,true);
			fops.write(d.getBytes());
			fops.write("\r\n".getBytes());
			fops.flush();
			fops.close();
		} catch (Exception e) {
		}
		
		
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		logger.warn("Connection closed by :{} ", (remote ? "remote peer" : "us"));
		try {
			String url = "ws://10.0.1.46/vision/edge/console/algorithm/noticeAlgoResult/29";
			System.out.println("web socket start ...");
			WebSocketApi c;
			try {
				URI uri = new URI(url);
				c = new WebSocketApi(uri, new Draft_17());
				c.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void close() {
		super.close();
		logger.warn("close.......................");
	}

	@Override
	public void connect() {
		super.connect();
		logger.warn("connect .................");
	}

	public static void main(String[] args) throws URISyntaxException {

	}
	
}
