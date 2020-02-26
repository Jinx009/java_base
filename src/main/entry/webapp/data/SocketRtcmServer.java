package main.entry.webapp.data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketRtcmServer {
	private static final Logger log = LoggerFactory.getLogger(SocketRtcmServer.class);
	public static byte[] b = new byte[] {};
	
	@SuppressWarnings("resource")
	public static void start() {

		try {

			// 初始化服务端socket连接，并监听8888端口的socket请求
			ServerSocket server = new ServerSocket(1124);
			log.warn("****** I am Server, now begin to wait the client ******");

			int count = 0;

			// 处理socket请求
			Socket socket = null;
			while (true) {
				socket = server.accept();
				ServerRtcmThread serverThread = new ServerRtcmThread(socket);
				log.warn("client host address is: {}" , socket.getInetAddress().getHostAddress());
				serverThread.start();
				count++;
				log.warn("now client count is:{} " , count);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SocketRtcmServer.start();
	}
}
