package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServerThread extends Thread {
	private static final Logger log = LoggerFactory.getLogger(TCPServerThread.class);

	ServerSocket server = null;

	public TCPServerThread(ServerSocket server) {
		this.server = server;
	}

	public static byte[] b = null;

	@SuppressWarnings({ "static-access", "unused" })
	public void run() {
		try {
			while (true) {
				Socket socket = server.accept();
				log.warn("client :{} ,{}", socket.getInetAddress().getLocalHost(), "conn success");
				while (true) {
					BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
					if (bufferedInputStream.available() > 0) {
						byte[] receive = new byte[1024];
						int read = bufferedInputStream.read(receive);
						b = receive;
						log.warn("server rec data：{}", new String(receive).length());
						BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
						String response = "success";
						bufferedOutputStream.write(response.getBytes());
						bufferedOutputStream.flush();
					}
				}
			}
		} catch (Exception e) {// 捕获异常
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		/*
		 * 服务器端接受客户端的数据
		 */
		ServerSocket socket = new ServerSocket(1123);
		TCPServerThread st = new TCPServerThread(socket);
		st.start();

	}

}
