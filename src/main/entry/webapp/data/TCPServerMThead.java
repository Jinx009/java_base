package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServerMThead extends Thread {
	private static final Logger log = LoggerFactory.getLogger(TCPServerMThead.class);

	ServerSocket server = null;

	public TCPServerMThead(ServerSocket server) {
		this.server = server;
	}

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
						log.warn("server rec data：{}", new String(receive));
						BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
						bufferedOutputStream.write(TCPServerThread.b);
						bufferedOutputStream.flush();
					}
				}
			}
		} catch (Exception e) {// 捕获异常
			e.printStackTrace();
		}

	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}

	public static void main(String[] args) throws IOException {
		/*
		 * 服务器端接受客户端的数据
		 */
		ServerSocket socket = new ServerSocket(1124);
		TCPServerThread st = new TCPServerThread(socket);
		st.start();

	}
}
