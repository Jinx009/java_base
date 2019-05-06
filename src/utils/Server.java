package utils;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
 
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;
//import org.eclipse.californium.elements.tcp.TcpServerConnector;
 
public class Server {
	
	private static AtomicInteger count = new AtomicInteger();
 
	public static void main(String[] args) {
		
		final CoapServer server = new CoapServer();
		
		//默认就是ucp实现传输层
//		server.addEndpoint(new CoapEndpoint(new InetSocketAddress("127.0.0.1", 5683)));
//		
//		//加入tcp实现传输层
//		server.addEndpoint(new CoapEndpoint(
//				new TcpServerConnector(new InetSocketAddress("127.0.0.1", 5683), 4, 20000),
//				NetworkConfig.getStandard()));
		
		//可以加入dtls支持，也就是coaps
//		server.addEndpoint(new CoapEndpoint(
//				new DTLSConnector(), //这里只是抛砖引玉，需要构建DtlsConnectorConfig
//				NetworkConfig.getStandard()));
		
		server.add(new CoapResource("hello"){
			@Override
			public void handleGET(CoapExchange exchange) {
				handlePOST(exchange);
			};
			@Override
			public void handlePOST(CoapExchange exchange) { //1
				System.out.println(exchange.getRequestOptions().getUriQueryString());
				System.out.println(exchange.getRequestText().length());
				exchange.respond("asdfasdf");
				super.handlePOST(exchange);
			}
		}.add(new CoapResource("lenovo"){
			@Override
			public void handlePOST(CoapExchange exchange) {  //2
				int c = count.incrementAndGet();
				if(c % 10000 == 0){
					System.out.println(c);
				}
				exchange.respond(String.valueOf(c));
				super.handlePOST(exchange);
			}
		}));
		Executors.newSingleThreadExecutor().execute(() -> {
			server.start();
		});
	}
}
