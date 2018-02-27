package utils.wechat;


import utils.HttpUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;


public class WechatUtil {

	private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);
	
	/**
	 * 获取accessToken
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getAccessToken(String appId, String appSecret)throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
				appId+"&secret="+appSecret;
		String result = HttpUtils.get(url);
		String access = null;
		JSONObject jsonObject = JSON.parseObject(result);
		logger.warn("[WechatUtil.getAccessToken:{}]",result);
		access = jsonObject.getString("access_token");
		return access;
	}

	
	/**
	 * 获取jsApiTicket
	 * @param appId
	 * @param appsecret
	 * @return
	 */
	public static String getJSApiTicket(String appId, String appsecret)throws Exception {
		String currentJSApiTicket = null;
		String accessToken = getAccessToken(appId, appsecret);
		if(accessToken!=null){
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
			String result = HttpUtils.get(url);
			JSONObject jsonObject = JSON.parseObject(result);
			currentJSApiTicket = jsonObject.getString("ticket");
		}
		return currentJSApiTicket;
	}

	/**
	 * 微信服务器图片下载到本地
	 * @param path
	 * @param mediaId
	 * @return
	 * @throws Exception
	 */
	public static String downToLocal(String path,String mediaId) throws Exception{
		String accessToken = getAccessToken(WechatData.APP_ID,WechatData.APP_SECRET);
		
		String urlString = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="
					 + accessToken + "&media_id="
					 + mediaId;
		String result = "";
		Map<String, Object> ret = httpGetBytes(urlString);
		if( ret == null || ret.get("contentType") == null || ret.get("content") == null){
			return null;
		}
		
		String contentType = (String) ret.get("contentType");
		String[] tmp = contentType.split("/");
		contentType = tmp[tmp.length-1].toLowerCase();
		byte[] content = (byte[]) ret.get("content");
		String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() )+UUID.randomUUID();
		String orgFilePath = path +filename+ "." + contentType;
		File orgFile = new File(orgFilePath);
		logger.warn("文件创建了：{}}",orgFile.getAbsolutePath());
		FileOutputStream outStream = new FileOutputStream(orgFile);
		outStream.write(content);
		outStream.close();
		result = filename+"."+contentType;
		
		return result;
	}
	
	/**
	 * Get oauth openid
	 * 获取openId和access_token
	 * @param code
	 * @param APP_ID
	 * @param APP_SECRET
	 * @return
	 */
	public static String[] getOpenid(String code) {
		String url = StringUtil.add("https://api.weixin.qq.com/sns/oauth2/access_token?appid=",WechatData.APP_ID,"&secret=",WechatData.APP_SECRET,"&code=",code,"&grant_type=authorization_code");

		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			logger.warn("first openid:{}",result);
			JSONObject json = JSONObject.parseObject(result);
			if(StringUtil.isNotBlank(json.getString("openid"))){
				return new String[]{json.getString("openid"),json.getString("access_token")};
			}
		} catch (Exception e) {
			logger.error("error:{}",e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * Get UserInfo
	 * 
	 * @param code
	 * @param APP_ID
	 * @param APP_SECRET
	 * @return
	 */
	public static JSONObject getRealUserInfo(String access_token, String openid) {
		String url = StringUtil.add("https://api.weixin.qq.com/sns/userinfo?access_token=",access_token,"&openid=",openid,"&lang=zh_CN");

		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			logger.warn("user_info:{}",result);
			JSONObject json = JSONObject.parseObject(result);
			if(StringUtil.isNotBlank("openid"));
			return json;
		} catch (Exception e) {
			logger.error("error:{}",e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	
	
	/**
	 * 
	 * @param 抓取图片流信息
	 * @return "contentType" & "content"
	 * @throws Exception
	 */
	public static Map<String, Object> httpGetBytes(String apiURL) throws Exception {
		Boolean outputLogger = true;
		Map<String, Object> retValue = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try{
			HttpGet httpGet = new HttpGet(apiURL);
			logger.warn("executing request:{}",httpGet.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpGet);
	        try {
	        	HttpEntity entity = response.getEntity();
	            logger.warn("----------------------,{}",response.getStatusLine());
	            if (entity != null) {
	            	retValue = new HashMap<String, Object>();
	            	retValue.put("contentType", entity.getContentType().toString().split(":")[1].trim());
	                System.out.println("Response content length: " + entity.getContentLength());
	                InputStream isr = entity.getContent();
	                byte[] buf = new byte[(int) entity.getContentLength()];  
	                int len = 0;
	                int totalLen = 0;
	                while (len != -1) {
	                	if( outputLogger )
	                		logger.warn("----------------------read len:{}",len);
	                	len = isr.read(buf, totalLen, buf.length+1);
	                	totalLen += len;
	                }  
	                retValue.put("content", buf);
	            }
	            EntityUtils.consume(entity);
	        } finally {
	            response.close();
	        }
	    } finally {
	        httpclient.close();
	    }
    return retValue;
	}
	
}
