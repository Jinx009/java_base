package utils;



import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;


import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketApi extends WebSocketClient{

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
		System.out.println("opened connection .......................");
	}

	@Override
	public void onMessage(String message) {
		System.out.println("received: {} ..............."+ message);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("Connection closed by :{} "+remote + "remote peerus");
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void close() {
		super.close();
		System.out.println("close.......................");
	}

	@Override
	public void connect() {
		super.connect();
		System.out.println("connect .................");
	}

	public static void main(String[] args) throws URISyntaxException {

	}
	
}
