package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
				// if(socket!=null) {
				// new DataThread(socket).start();
				// }
				while (true) {
					if (socket != null) {
						String str = "";
						bufferedInputStream = new BufferedInputStream(socket.getInputStream());
						if (bufferedInputStream.available() > 0) {
							byte[] receive = new byte[256];
							int read = bufferedInputStream.read(receive);
							b = receive;
							log.warn("server rec data：{}", read);
							try {
								Socket s = new Socket("139.224.237.198", 8888);
								OutputStream outputStream = s.getOutputStream();
								outputStream.write(b);
								outputStream.flush();
								s.close();
							} catch (Exception e) {
								log.error("socket error:{}",e);
							}
//							new Thread() {
//								public void run() {
//								
//									
//								}
//							}.start();
						}
					}
				}
				// Socket socket = server.accept();
				// log.warn("client :{} ,{}", socket.getInetAddress().getLocalHost(), "conn
				// success");
				// InputStream inputStream = socket.getInputStream();
				// byte[] bytes = new byte[256];
				// int len;
				// StringBuilder sb = new StringBuilder();
				// b = bytes;
				// // 只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
				// while ((len = inputStream.read(bytes)) != -1) {
				// // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
				// sb.append(new String(bytes, 0, len, "UTF-8"));
				// }
				// log.warn("get message from client: {}", b.length);
				// inputStream.close();
				// socket.close();

				// log.warn("client :{} ,{}", socket.getInetAddress().getLocalHost(), "conn
				// success");
				// InputStream inputStream = socket.getInputStream();
				// byte[] bytes = new byte[1024];
				// int len;
				// StringBuilder sb = new StringBuilder();
				// b = bytes;
				//// //只有当客户端关闭它的输出流的时候，服务端才能取得结尾的-1
				//// while ((len = inputStream.read(bytes)) != -1) {
				//// // 注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
				//// sb.append(new String(bytes, 0, len, "UTF-8"));
				//// }
				// log.warn("get message from client: {}" , b.length);

				// OutputStream outputStream = socket.getOutputStream();
				// outputStream.write("Hello Client,I get the message.\n".getBytes("UTF-8"));
				// outputStream.flush();

			} catch (Exception e) {// 捕获异常
				log.error("socket error");
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

	class DataThread extends Thread {
		private Socket socket;

		public DataThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(2000);
					socket.sendUrgentData(0xff);
				} catch (Exception e) {
					log.error("socket closed");
					try {
						if (socket != null) {
							socket.shutdownInput();
							socket.shutdownOutput();
							socket.close();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				}
			}

		}
	}

}
