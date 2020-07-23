package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServerMThead extends Thread {
	private static final Logger log = LoggerFactory.getLogger(TCPServerMThead.class);

	ServerSocket server = null;
	
	public static byte[] a = new byte[] {};

	public TCPServerMThead(ServerSocket server) {
		this.server = server;
	}

	@SuppressWarnings({ "unused", "static-access" })
	public void run() {
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		Socket socket = null;
		while (true) {
			try {
				socket = server.accept();
				log.warn("client :{} ,{}", socket.getInetAddress().getLocalHost(), "conn success");
				if(socket!=null) {
					new DataThread(socket).start();
				}
				while (true) {
					String str = "";
					bufferedInputStream = new BufferedInputStream(socket.getInputStream());
					if (bufferedInputStream.available() > 0) {
						byte[] receive = new byte[1024];
						int read = bufferedInputStream.read(receive);
						str = new String(receive, "UTF-8").trim();
						log.warn("server rec data：{}", str);
					} 
					if(!Arrays.equals(TCPServerThread.b,a)){//
						a = TCPServerThread.b;
						bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
						bufferedOutputStream.write(a);//SocketServer.b
						bufferedOutputStream.flush();
					}
				}
			} catch (Exception e) {// 捕获异常
				e.printStackTrace();
			}
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
		TCPServerMThead st = new TCPServerMThead(socket);
		st.start();

	}
	class DataThread extends Thread{
		private Socket socket;
		public DataThread (Socket socket) {
			this.socket = socket;
		}
		public void run() {
				while(true) {
					try {
					Thread.sleep(5000);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
//					socket.sendUrgentData(0xff);
					bufferedOutputStream.write(a);//SocketServer.b
					bufferedOutputStream.flush();
					} catch (Exception e) {
						e.printStackTrace();
						try {
							if(socket!=null) {
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