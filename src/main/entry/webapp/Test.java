package main.entry.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;

public class Test {
	public static String postFile(String url, String filePath) {  
	    File file = new File(filePath);  
	    if (!file.exists())  
	        return null;  
	    String result = null;  
	    try {  
	        URL url1 = new URL(url);  
	        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();  
	        conn.setConnectTimeout(5000);  
	        conn.setReadTimeout(30000);  
	        conn.setDoOutput(true);  
	        conn.setDoInput(true);  
	        conn.setUseCaches(false);  
	        conn.setRequestMethod("POST");  
	        conn.setRequestProperty("Connection", "Keep-Alive");  
	        conn.setRequestProperty("Cache-Control", "no-cache");  
	        String boundary = "-----------------------------" + System.currentTimeMillis();  
	        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);  
	  
	        OutputStream output = conn.getOutputStream();  
	        output.write(("--" + boundary + "\r\n").getBytes());  
	        output.write(  
	                String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName())  
	                        .getBytes());  
	        output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());  
	        byte[] data = new byte[1024];  
	        int len = 0;  
	        FileInputStream input = new FileInputStream(file);  
	        while ((len = input.read(data)) > -1) {  
	            output.write(data, 0, len);  
	        }  
	        output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());  
	        output.flush();  
	        output.close();  
	        input.close();  
	        InputStream resp = conn.getInputStream();  
	        StringBuffer sb = new StringBuffer();  
	        while ((len = resp.read(data)) > -1)  
	            sb.append(new String(data, 0, len, "utf-8"));  
	        resp.close();  
	        result = sb.toString();  
	        System.out.println(result);  
	    } catch (ClientProtocolException e) {  
	        System.out.println("postFile，不支持http协议"+ e);  
	    } catch (IOException e) {  
	    	System.out.println("postFile数据传输失败"+ e);  
	    }  
	    System.out.println(result);  
	    return result;  
	}  
	  
	public static void main(String[] args) {  
	    String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=6_svqJO4NtnZZg3Ah52v7-jM2GBykm9WMNsN5I3677tVCwyFDMxKudBXyh3wvoOAAxdVBBvmx1XF4fgXwc1epuVQgPgJGKc4wP-tzPKtdH8paKUWEOPBBfqbNpSo65vwAPK9BzatY6SK3F-n7uNESeABAQHV&type=image";  
	    postFile(url, "/Users/jinx/Downloads/WechatIMG185.jpeg");  
	}  
}
