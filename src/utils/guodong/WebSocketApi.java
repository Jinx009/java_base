package utils.guodong;

import java.net.URI;
import java.net.URISyntaxException;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.helper.ApplicationContextProvider;
import service.basicFunctions.guodong.GuodongSensorService;


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

	@Override
	public void onMessage(String message) {
		logger.warn("received: {} ...............", message);
		GuodongSensorService guodongSensorService = (GuodongSensorService) ApplicationContextProvider.getBeanByName("guodongSensorService");
		guodongSensorService.update(message);
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
