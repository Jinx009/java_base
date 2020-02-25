package main.entry.webapp.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
		// 创建Socket对象，连接服务器
		// 通过客户端的套接字对象Socket方法，获取字节输出流，将数据写向服务器
		int i = 0;
		 Socket socket = new Socket("139.196.205.157", 1124);
//		Socket socket = new Socket("localhost", 1124);
		while (i < 13) {
			i++;
			OutputStream outputStream = socket.getOutputStream();
			if(i==13) {
				outputStream.write(("end").getBytes());
				outputStream.flush();
			}else {
				outputStream.write((i + "sss").getBytes());
				outputStream.flush();
			}
			BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
			if (bufferedInputStream.available() > 0) {
				byte[] receive = new byte[1024];
				int read = bufferedInputStream.read(receive);
				System.out.println(new String(receive));
			}
			Thread.sleep(1000);
		}
		socket.close();
	}
}
