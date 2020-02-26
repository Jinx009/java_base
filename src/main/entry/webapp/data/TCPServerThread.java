package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	public static byte[] b = new byte[] {};

	@SuppressWarnings({ "static-access", "unused" })
	public void run() {
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		Socket socket = null;
		while (true) {
			try {
				socket = server.accept();
				log.warn("client :{} ,{}", socket.getInetAddress().getLocalHost(), "conn success");
				InputStream inputStream = socket.getInputStream();
			    byte[] bytes = new byte[1024];
			    int len;
			    StringBuilder sb = new StringBuilder();
			    b = bytes;
			    //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
			    while ((len = inputStream.read(bytes)) != -1) {
			      // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
			      sb.append(new String(bytes, 0, len, "UTF-8"));
			    }
			    log.warn("get message from client: {}" , sb);
			 
			    OutputStream outputStream = socket.getOutputStream();
			    outputStream.write("Hello Client,I get the message.".getBytes("UTF-8"));
			    outputStream.flush();
			    
			} catch (Exception e) {// 捕获异常
				e.printStackTrace();
			}
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
