package main.entry.webapp.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.drafts.Draft_17;

public class WebSocketClient {

	public static void main(String[] args) {
		try {
			String url = "ws://192.168.1.2:8080/vision/edge/console/algorithm/noticeAlgoResult/6";
			WebSocketApi c;
			try {
				URI uri = new URI(url);
				c = new WebSocketApi(uri, new Draft_17());
				c.connect();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
	
	
}
