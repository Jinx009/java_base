package utils.guodong;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

@SuppressWarnings("deprecation")
public class OpenApi {
	
	public static final int DATA_MAX_LENGTH = 51;
	public static final String URL = "http://www.guodongiot.com:90/api/";
	private static final String USER_ID = "3456959";
	private static final String USER_SEC = "a7da4728e374343d37021e4be5593311";

	public static void main(String[] args) {
		OpenApi ob = new OpenApi();

		 System.out.println("====node" + ob.getNodeDataListInfo("0000000000004f96").toString());
//		 System.out.println("====2" + ob.getAppEuiDataListInfo("000000000000006c").toString());

//		byte[] data = new byte[DATA_MAX_LENGTH];
//		for (int i = 0, j = data.length; i < j; i++) {
//			data[i] = (byte) (Math.random() * 255);
//		}
//		System.out.println(ob.sendDataToNodes("0000000000004f8e", data));
	}

	/**
	 * 获取某个节点(地磁)下所有的数据
	 * @param deveui
	 * @return
	 */
	@SuppressWarnings({ "resource" })
	public JSONObject getNodeDataListInfo(String deveui) {
		System.out.println("start getNodeDataListInfo!");
		String apiUrl = URL + "OpenAPI_Node_GetNodeDataList";
		String devEUI = deveui;
		String currentTime = String.valueOf(System.currentTimeMillis());
		String token = null;

		JSONObject result = null;

		HttpPost post = new HttpPost(apiUrl);
		post.setHeader("accept", "application/json");
		post.setHeader("content-type", "application/json");
		post.setHeader("userId", USER_ID);
		post.setHeader("time", currentTime);

		JSONObject json = new JSONObject();
		JSONObject jsonInner = new JSONObject();
		jsonInner.element("devEUI", devEUI);
		jsonInner.element("limit", 100);
		jsonInner.element("offset", 0);
		// jsonInner.element("obtainStartDataTime", "2016-08-16 01:23:08");
		// jsonInner.element("obtainEndDataTime", "2016-08-17 14:23:08");
		// jsonInner.element("obtainStartDataTime", "latest");
		jsonInner.element("obtainStartDataTime", "all");

		json.element("params", jsonInner);

		String bodyString = json.toString();
		StringEntity entity = new StringEntity(bodyString, ContentType.create("plain/text", Consts.UTF_8));
		entity.setChunked(true);
		post.setEntity(entity);

		try {
			bodyString = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String secret = USER_ID + currentTime + USER_SEC;

		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes("US-ASCII"), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);

			byte[] text = bodyString.getBytes("US-ASCII");
			byte[] finalText = mac.doFinal(text);
			token = Base64.getEncoder().encodeToString(finalText);
			post.setHeader("token", token);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(post);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				result = JSONObject.fromObject(strResult);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result != null) {
			System.out.println(result.toString());
		} else {
			System.out.println("getNodeDataListInfo fail!");
		}
		return result;
	}

	/**
	 * 查看appId下所有信息
	 * appeui:"abc541acbdfa0853"
	 * @param appeui
	 * @return
	 */
	@SuppressWarnings("resource")
	public JSONObject getAppEuiDataListInfo(String appeui) {
		System.out.println("start getAppEuiDataListInfo!");
		String apiUrl = URL + "OpenAPI_Node_GetDataListForAppEUI";
		String appEUI = appeui;
		String currentTime = String.valueOf(System.currentTimeMillis());
		String token = null;

		JSONObject result = null;

		HttpPost post = new HttpPost(apiUrl);
		post.setHeader("accept", "application/json");
		post.setHeader("content-type", "application/json");
		post.setHeader("userId", USER_ID);
		post.setHeader("time", currentTime);

		JSONObject json = new JSONObject();
		JSONObject jsonInner = new JSONObject();
		jsonInner.element("appEUI", appEUI);
		jsonInner.element("limit", 100);
		jsonInner.element("offset", 0);
		jsonInner.element("obtainStartDataTime", "all");
		json.element("params", jsonInner);
		// jsonInner.element("obtainStartDataTime", "2016-08-16 01:23:08");
		// jsonInner.element("obtainEndDataTime", "2016-08-17 14:23:08");
		// jsonInner.element("obtainStartDataTime", "latest");

		String bodyString = json.toString();
		StringEntity entity = new StringEntity(bodyString, ContentType.create("plain/text", Consts.UTF_8));
		entity.setChunked(true);
		post.setEntity(entity);

		try {
			bodyString = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String secret = USER_ID + currentTime + USER_SEC;

		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes("US-ASCII"), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);

			byte[] text = bodyString.getBytes("US-ASCII");
			byte[] finalText = mac.doFinal(text);
			token = Base64.getEncoder().encodeToString(finalText);
			post.setHeader("token", token);

			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(post);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				result = JSONObject.fromObject(strResult);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
		} else {
			System.out.println("getAppEuiDataListInfo fail!");
		}
		return result;
	}

	/**
	 * 发送下行数据
	 * @param devEUI
	 * @param data
	 * @return
	 */
	@SuppressWarnings("resource")
	public JSONObject sendDataToNodes(String devEUI, byte[] data) {
		System.out.println("start sendDataToNodes!");
		String apiUrl = URL + "OpenAPI_SendDataForNode";
		String currentTime = String.valueOf(System.currentTimeMillis());
		String token = null;

		JSONObject result = null;

		if (data.length > DATA_MAX_LENGTH && data.length < 1) {
			return null;
		}

		String sendData = "\\x";
		for (int i = 0, j = data.length; i < j; i++) {
			sendData += String.format("%02x", data[i]);

		}
		System.out.println("sendData:" + sendData);

		HttpPost post = new HttpPost(apiUrl);
		post.setHeader("accept", "application/json");
		post.setHeader("content-type", "application/json");
		post.setHeader("userId", USER_ID);
		post.setHeader("time", currentTime);

		JSONObject json = new JSONObject();
		JSONObject jsonInner = new JSONObject();
		jsonInner.element("devEUI", devEUI);
		jsonInner.element("data", sendData);
		json.element("params", jsonInner);

		String bodyString = json.toString();
		StringEntity entity = new StringEntity(bodyString, ContentType.create("plain/text", Consts.UTF_8));
		entity.setChunked(true);
		post.setEntity(entity);

		try {
			bodyString = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String secret = USER_ID + currentTime + USER_SEC;

		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes("US-ASCII"), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);

			byte[] text = bodyString.getBytes("US-ASCII");
			byte[] finalText = mac.doFinal(text);
			token = Base64.getEncoder().encodeToString(finalText);
			post.setHeader("token", token);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(post);

			int statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				result = JSONObject.fromObject(strResult);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result != null) {
			System.out.println(result.toString());
		} else {
			System.out.println("sendDataToNodes fail!");
		}
		return result;
	}
}
