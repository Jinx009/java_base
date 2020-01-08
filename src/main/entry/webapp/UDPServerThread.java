package main.entry.webapp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.HttpUtil;

/*
 * 服务器线程处理类
 */
public class UDPServerThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(UDPServerThread.class);

	DatagramSocket socket = null;

	public UDPServerThread(DatagramSocket socket) {
		this.socket = socket;
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];// 指定用于接受数据报的大小
			log.warn("client server start:");
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);// 接受到数据之前该方法处于阻塞状态
				String info = bytesToHexString(data, packet.getLength());
				log.warn("client info:{},ip:{}", info,packet.getAddress());
				if(info.equals("0000000000000000")) {
					log.warn("client error:--------------------");
				}else {
//					HttpUtil.postJson("http://127.0.0.1:8102/na/iocm/devNotify/v1.1.0/updateDeviceDatas", 
//					"{\"notifyType\":\"deviceDatasChanged\",\"requestId\":null,\"deviceId\":\""+info.substring(0,16)+"\",\"gatewayId\":\""+info.substring(0,16)+"\",\"services\":[{\"serviceId\":\"data\",\"serviceType\":\"data\",\"data\":{\"data\":\""+info+"\"},\"eventTime\":\"20191230T014307Z\"}]}");
			HttpUtil.postJson("http://127.0.0.1:1122/iot/na/iocm/devNotify/v1.1.0/updateDeviceDatas", 
					"{\"notifyType\":\"deviceDatasChanged\",\"requestId\":null,\"deviceId\":\""+info.substring(0,16)+"\",\"gatewayId\":\"LT\",\"services\":[{\"serviceId\":\"data\",\"serviceType\":\"data\",\"data\":{\"data\":\""+info+"\"},\"eventTime\":\"20191230T014307Z\"}]}");
			 
				}
				InetAddress address = packet.getAddress();
				 int port = packet.getPort();
				 byte[] data2 = "UDPACK\r\n".getBytes();
				 DatagramPacket packet2 = new DatagramPacket(data2,
				 data2.length, address, port);
				 socket.send(packet2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	
	
	
	public static void main(String[] args) throws IOException {
		/*
		 * 服务器端接受客户端的数据
		 */
		DatagramSocket socket = new DatagramSocket(7777);
		UDPServerThread st = new UDPServerThread(socket);
		st.start();

	}
	
	
	private static String bytesToHexString(byte[] src, int length) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || length <= 0) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv.toUpperCase());
		}
		return stringBuilder.toString();
	}
}