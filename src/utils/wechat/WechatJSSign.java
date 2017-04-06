package utils.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.apache.http.client.ClientProtocolException;


public class WechatJSSign 
{
	/**
	 * 主方法
	 * @param url
	 * @param appID
	 * @param appSecrect
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static Map<String, String> createSign(String jsapi_ticket,String url, String appID,String appSecrect) throws ClientProtocolException, IOException{
		Map<String, String> ret = sign(jsapi_ticket, url);
		return ret;
	}
	
	/**
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url){
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		string1 = "jsapi_ticket="+jsapi_ticket+"&noncestr="+ nonce_str+"&timestamp="+timestamp+"&url="+url;

		try{
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		}
		catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}
	
	/**
	 * 去除特殊字符
	 * @param hash
	 * @return
	 */
	private static String byteToHex(final byte[] hash){
		Formatter formatter = new Formatter();
		for (byte b : hash){
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
	
	/**
	 * 随机字符串
	 * @return
	 */
	private static String create_nonce_str(){
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 获取时间戳
	 * @return
	 */
	private static String create_timestamp(){
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	
}
