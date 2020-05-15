package main.entry.webapp.websocket;

import java.net.URI;
import java.net.URISyntaxException;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebSocketApi  extends WebSocketClient{

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
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		logger.warn("Connection closed by :{} ", (remote ? "remote peer" : "us"));
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