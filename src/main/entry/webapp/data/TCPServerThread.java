package main.entry.webapp.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

	public void run() {
		while (true) {
			try {
				Socket socket = server.accept();// 等待客户连接
				DataInputStream in = new DataInputStream(socket.getInputStream());// 读取客户端传过来信息的DataInputStream
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());// 向客户端发送信息的DataOutputStream
				String accpet = in.readUTF();// 读取来自客户端的信息
				log.warn("tcp data:      {}", accpet);// 输出来自客户端的信息
				out.writeUTF("success");// 把服务器端的输入发给客户端
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
