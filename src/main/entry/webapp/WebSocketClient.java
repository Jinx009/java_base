package main.entry.webapp;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;

import org.java_websocket.drafts.Draft_17;
import org.springframework.stereotype.Component;

@Component
public class WebSocketClient {

	@PostConstruct
	public void conn() {
		try {
			String url = "ws://10.0.1.46/vision/edge/console/algorithm/noticeAlgoResult/35";
			System.out.println("web socket start ...");
			WebSocketApi c;
			try {
				URI uri = new URI(url);
				c = new WebSocketApi(uri, new Draft_17());
				c.connect();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
