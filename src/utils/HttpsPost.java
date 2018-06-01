package utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsPost {

	public static String sendAndRcvHttpPostBase(String url, String sendData) {
		String result = "";
		BufferedReader in = null;
		DataOutputStream out = null;
		HttpsURLConnection httpsConn = null;
		HttpURLConnection httpConn = null;
		try {
			URL myURL = new URL(url);
			httpsConn = (HttpsURLConnection) myURL.openConnection();
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			} };
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			httpsConn.setSSLSocketFactory(sc.getSocketFactory());
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}
			};
			httpsConn.setHostnameVerifier(hv);

			httpsConn.setRequestProperty("Accept-Charset", "utf-8");
			httpsConn.setRequestProperty("User-Agent", "java HttpsURLConnection");
			httpsConn.setRequestMethod("POST");
			httpsConn.setUseCaches(false);
			httpsConn.setDoInput(true);
			httpsConn.setInstanceFollowRedirects(true);
			if (sendData != null) {
				httpsConn.setDoOutput(true);
				// 获取URLConnection对象对应的输出流
				out = new DataOutputStream(httpsConn.getOutputStream());
				// 发送请求参数
				out.write(sendData.getBytes("utf-8"));
				// flush输出流的缓冲
				out.flush();
				out.close();
			}
			// 取得该连接的输入流，以读取响应内容
			in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
				System.out.println("=====反回结果=====" + line);
			}
		} catch (IOException e) {
			System.out.println(e);
			result = null;
		} catch (Exception e) {
			System.out.println(e);
			result = null;
		} finally {
			System.out.println("");
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (httpConn != null) {
				httpConn.disconnect();
			}
			if (httpsConn != null) {
				httpsConn.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		System.out.println(result);
		return result;
	}

	public static final void main(String[] s) {
		sendAndRcvHttpPostBase("https://api.opg-iot.cn/thingpark/lrc/rest/downlink?DevEUI=00956900000006DD&FPort=1&Payload=480032025200&FCntDn=12","");
	}

}
