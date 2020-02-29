package main.entry.webapp.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;

public class TCPClient {
	// public static void main(String[] args) {
	//
	// try {
	//
	// // 1.初始化客户端socket连接
	//// Socket socket = new Socket("139.196.205.157", 1124);
	// Socket socket = new Socket("localhost", 1124);
	//
	// // 2.client发送消息
	// OutputStream outputStream = socket.getOutputStream();
	// PrintWriter printWriter = new PrintWriter(outputStream);
	// printWriter.write("[name:jim, pwd:123]");
	// printWriter.flush();
	// socket.shutdownOutput();
	//
	// // 3.client接收消息
	// InputStream inputStream = socket.getInputStream();
	// InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	// BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	// socket.shutdownInput();
	// String str;
	// StringBuffer b = new StringBuffer();
	// System.out.println(bufferedReader.readLine());
	// while ((str = bufferedReader.readLine()) != null) {
	// b.append(str);
	// System.out.println(str);
	// }
	// System.out.println("I am Client, now get message from Server:" + b);
	// // 4.关闭资源
	// bufferedReader.close();
	// inputStreamReader.close();
	// inputStream.close();
	//
	// printWriter.close();
	// outputStream.close();
	// socket.close();
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	// @SuppressWarnings("resource")
	// public static void main(String[] args) throws IOException,
	// InterruptedException {
	// // 创建Socket对象，连接服务器
	// // 通过客户端的套接字对象Socket方法，获取字节输出流，将数据写向服务器
	// int i = 0;
	//// Socket socket = new Socket("139.196.205.157", 1124);
	// Socket socket = new Socket("rtk.ntrip.qxwz.com", 8002);
	//// Socket socket = new Socket("localhost", 1124);
	//// while (true) {
	// i++;
	//// OutputStream outputStream = socket.getOutputStream();
	//// outputStream.write(("bS"+i).getBytes());
	//// outputStream.flush();
	// InputStream inputStream = socket.getInputStream();
	// InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	// BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	// String str;
	// if ((str = bufferedReader.readLine()) != null) {
	// str+=str;
	// }
	// System.out.println("rtcm message from Client: ---"+i + str);
	//// }
	//// socket.shutdownInput();
	//// socket.close();
	// }
	//
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		Socket socket = new Socket("rtk.ntrip.qxwz.com", 8002);
		String msg = "GET /RTCM32_GGB HTTP/1.1 \r\n";// 请求行
		msg += "Host: rtk.ntrip.qxwz.com \r\n";// 请求首部
		msg += "Ntrip-Version: Ntrip/2.0 \r\n";// 通用首部
		msg += "User-Agent: NTRIP GNSSInternetRadio 2.0.10 \r\n";// 请求首部
		 msg += "Accept: */*\r\nConnection: close\r\n";// 7802_RTD
		msg += "Accept:*/* \r\n";// 请求首部
		msg += "Authorization: Basic " + Base64.getEncoder().encodeToString("qxnfun00291:a79b386".getBytes())
				+ "\r\n\r\n";// base64加密用户名和密码 // 请求首部
		/******************************************************************/

		System.out.print("" + msg);
//		System.out.print("" + Base64.);
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(msg.getBytes());
		outputStream.flush();
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str;
		if ((str = bufferedReader.readLine()) != null) {
			str += str;
		}
		System.out.println("rtcm message from Client: ---" + str);
		// }
	}
}
