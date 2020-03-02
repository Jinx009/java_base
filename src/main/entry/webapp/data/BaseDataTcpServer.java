package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监测院数据转发
 * 
 * @author Jinx
 *
 */
public class BaseDataTcpServer extends Thread {

	private static Logger log = LoggerFactory.getLogger(BaseDataTcpServer.class);

	ServerSocket server = null;

	public BaseDataTcpServer(ServerSocket server) {
		this.server = server;
	}

	public static byte[] dataArray = new byte[] {};

	@SuppressWarnings("static-access")
	public void run() {
		try {
			while (true) {
				// 从请求队列中取出一个连接
				Socket client = server.accept();
				// 处理这次连接
				// new HandlerThread(client);
				try {
					log.warn("base data tcp server client :{} ,{}", client.getInetAddress().getLocalHost(),
							"conn success");
					if (client != null) {
						BufferedInputStream bufferedInputStream = new BufferedInputStream(client.getInputStream());
						if (bufferedInputStream.available() > 0) {
							byte[] receive = new byte[256];
							int read = bufferedInputStream.read(receive);
							dataArray = receive;
							log.warn("server rec data：{}", read);
						}
						client.shutdownInput();
						client.close();
					}
				} catch (Exception e) {
					log.error("base data tcp server thread error:{}: ", e.getMessage());
				}
			}
		} catch (Exception e) {
			log.error("base data tcp server error:{}", e);
		}
	}

	// private class HandlerThread implements Runnable {
	// private Socket socket;
	//
	// public HandlerThread(Socket client) {
	// this.socket = client;
	// new Thread(this).start();
	// }
	//
	// @SuppressWarnings("static-access")
	// public void run() {
	// try {
	// log.warn("base data tcp server client :{} ,{}",
	// socket.getInetAddress().getLocalHost(), "conn success");
	// while (true) {
	// if (socket != null) {
	// BufferedInputStream bufferedInputStream = new
	// BufferedInputStream(socket.getInputStream());
	// if (bufferedInputStream.available() > 0) {
	// byte[] receive = new byte[256];
	// int read = bufferedInputStream.read(receive);
	// dataArray = receive;
	// log.warn("server rec data：{}", read);
	// }
	// }
	// }
	// } catch (Exception e) {
	// log.error("base data tcp server thread error:{}: ", e.getMessage());
	// }
	// }
	// }

}
