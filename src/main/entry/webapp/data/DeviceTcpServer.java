package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class DeviceTcpServer extends Thread{

	private static final Logger log = LoggerFactory.getLogger(DeviceTcpServer.class);

	ServerSocket server = null;
	
	public DeviceTcpServer(ServerSocket server) {
		this.server = server;
	}

	public void run() {
		while (true) {
			try {
				while (true) {
					// 从请求队列中取出一个连接
					Socket client = server.accept();
					// 处理这次连接
					byte[] data = new byte[] {};
					new HandlerThread(client,data);
				}
			} catch (Exception e) {// 捕获异常
				log.error("device tcp server error:{}", e);
			}
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;
		private byte[] data;

		public HandlerThread(Socket client,byte[] data) {
			this.socket = client;
			this.data = data;
			new Thread(this).start();
		}

		@SuppressWarnings("static-access")
		public void run() {
			try {
				log.warn("device tcp server :{} ,{}", socket.getInetAddress().getLocalHost(), "conn success");
				if(socket!=null) {
					new DataThread(socket).start();
				}
				
				while (true) {
					BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
					if (bufferedInputStream.available() > 0) {
						byte[] receive = new byte[1024];
						int read = bufferedInputStream.read(receive);
						log.warn("device tcp server thread rec：{}", read);
					} 
					if(!Arrays.equals(BaseDataTcpServer.dataArray,data)){//
						data = BaseDataTcpServer.dataArray;
						BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
						bufferedOutputStream.write(data);
						bufferedOutputStream.flush();
					}
				}
			} catch (Exception e) {
				log.error("base data tcp server thread error:{}: ", e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						log.error("base data tcp server thread error", e.getMessage());
					}
				}
			}
		}
	}

	public class DataThread extends Thread{
		private Socket socket;
		public DataThread (Socket socket) {
			this.socket = socket;
		}
		public void run() {
				while(true) {
					try {
					Thread.sleep(10000);
					BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
					bufferedOutputStream.write("0xFF".getBytes());
					bufferedOutputStream.flush();
					} catch (Exception e) {
						log.error("e:{}",e);
						e.printStackTrace();
						try {
							if(socket!=null) {
								socket.shutdownInput();
								socket.shutdownOutput();
								socket.close();
							}
						} catch (IOException e1) {
							log.error("e:{}",e1);
						}
						break;
					}
				}
			
		}
	}
	
}
