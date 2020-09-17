package main.entry.webapp.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class NtripClient {

	public static void main(String[] args) {
		// String result = "500";
		// DefaultHttpClient httpClient = new DefaultHttpClient();
		// HttpGet get = new HttpGet("http://rtk.ntrip.qxwz.com:8002/RTCM23");
		// get.addHeader("Authorization", "Basic
		// "+Base64.getEncoder().encodeToString("qxnfun00606:6a3ae19".getBytes()));
		// try {
		// HttpResponse response = httpClient.execute(get);
		// result = EntityUtils.toString(response.getEntity(),"UTF-8");
		// System.out.println("HttpUtils.get[res:{}]"+result);
		// } catch (ParseException e) {
		// System.out.println("HttpUtils.get[ParseException.error:{}]"+e);
		// } catch (IOException e) {
		// System.out.println("HttpUtils.get[IOException.error:{}]"+e);
		// }
		boolean flag = true;// 循环标志
		Socket m_socket = null;
		InputStream in = null;
		OutputStream out = null;
		InputStream in1 = null;
		int _tryTimes = 1 == 0 ? -1 : 1;

		while (flag && _tryTimes != 0) {
			try {
				m_socket = new Socket();
				m_socket.setSoTimeout(20 * 1000);
				m_socket.connect(new InetSocketAddress("rtk.ntrip.qxwz.com", 8002), 20 * 1000);
				m_socket.setKeepAlive(true);
				m_socket.setOOBInline(true);

				out = m_socket.getOutputStream();
				in = m_socket.getInputStream();
				String msg = "GET /hz12 HTTP/1.1 \r\n";// 请求行
				msg += "Host: rtk.ntrip.qxwz.com \r\n";// 请求首部
				msg += "Ntrip-Version: Ntrip/2.0 \r\n";// 通用首部
				msg += "User-Agent: NTRIP GNSSInternetRadio 2.0.10 \r\n";// 请求首部
				// msg += "Accept: */*\r\nConnection: close\r\n";// 7802_RTD
				msg += "Accept:*/* \r\n";// 请求首部
				msg += "Authorization: Basic " + Base64.getEncoder().encodeToString("qxnfun00606:6a3ae19".getBytes())
						+ "\r\n\r\n";// base64加密用户名和密码 // 请求首部
				/******************************************************************/

				System.out.print("" + msg);
				out.write(msg.getBytes());
				out.flush();
				int n = 0;
				byte[] buf = new byte[1024];
				byte[] buf1 = new byte[1024];
				String message = "";
				String message1 = "";
				if ((n = in.read(buf)) > 0) {
					message += new String(buf, 0, n);
					System.out.println("receive:" + message);
					// System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
					// System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					// System.out.println("*******************************************");
					System.out.println("开始解析cors站数据！！！！");
					out.close();
					in.close();
					m_socket.close();
					flag = false;// 停止循环标志
				} else {
					if ("" == message && message.equals(""))
						System.out.println("cors站登录失败！！！！！");
				}

			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				if (m_socket != null) {
					try {
						in.close();
						out.close();
						m_socket.close();

					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					m_socket = null;
					in = null;
					out = null;
					in1 = null;
				}

				// try {
				// Thread.sleep(5 * 1000);
				// } catch (InterruptedException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
