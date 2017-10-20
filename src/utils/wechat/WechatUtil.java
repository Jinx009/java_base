package utils.wechat;

import utils.BufferUtils;
import utils.HttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;

@SuppressWarnings("deprecation")
public class WechatUtil {

	private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);

	/**
	 * 获取accessToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getAccessToken(String appId, String appSecret) {
		String access = null;
		try {
			String url = BufferUtils.add("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=",
					appId, "&secret=", appSecret);
			String result = HttpUtils.get(url);
			JSONObject jsonObject = JSON.parseObject(result);
			access = jsonObject.getString("access_token");
			logger.warn("[access_token:{}]", result);
			return access;
		} catch (Exception e) {
			logger.warn("[errorn:{}]", e);
		}
		return access;
	}

	/**
	 * 获取jsApiTicket
	 * 
	 * @param appId
	 * @param appsecret
	 * @return
	 */
	public static String getJSApiTicket(String appId, String appsecret) {
		String currentJSApiTicket = null;
		try {
			String accessToken = getAccessToken(appId, appsecret);
			if (accessToken != null) {
				String url = BufferUtils.add("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=",
						accessToken, "&type=jsapi");
				String result = HttpUtils.get(url);
				JSONObject jsonObject = JSON.parseObject(result);
				currentJSApiTicket = jsonObject.getString("ticket");
				return currentJSApiTicket;
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return currentJSApiTicket;
	}

	/**
	 * 
	 * @param 抓取图片流信息
	 * @return "contentType" & "content"
	 * @throws IOException 
	 * @throws Exception
	 */
	public static Map<String, Object> httpGetBytes(String apiURL) throws IOException {
		Boolean outputLogger = true;
		CloseableHttpResponse response = null;
		Map<String, Object> retValue = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(apiURL);
			logger.warn("executing request:{}", httpGet.getRequestLine());
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			logger.warn("executing entity:{},{}", "-------------\n", response.getStatusLine());
			if (entity != null) {
				retValue = new HashMap<String, Object>();
				retValue.put("contentType", entity.getContentType().toString().split(":")[1].trim());
				System.out.println("Response content length: " + entity.getContentLength());
				InputStream isr = entity.getContent();
				byte[] buf = new byte[(int) entity.getContentLength()];
				int len = 0;
				int totalLen = 0;
				while (len != -1) {
					if (outputLogger)
						logger.warn("read len:{}", len);

					len = isr.read(buf, totalLen, buf.length + 1);
					totalLen += len;
				}
				retValue.put("content", buf);
			}
			EntityUtils.consume(entity);
			response.close();
			httpclient.close();
		} catch (Exception e) {
			logger.error("error:{}",e);
			response.close();
			httpclient.close();
		}
		return retValue;
	}

	/**
	 * 将微信上传的图片或音频下载到本地
	 * 
	 * @param path
	 * @param mediaId
	 * @return
	 */
	public static String downToLocal(String path, String mediaId) {
		String result = "";
		String accessToken = null;
		Map<String, Object> ret;
		try {
			accessToken = getAccessToken(WechatData.APP_ID, WechatData.APP_SECRET);
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		try {
			String urlString = BufferUtils.add("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=",
					accessToken, "&ACCESS_TOKEN&media_id=", mediaId);
			ret = httpGetBytes(urlString);
			String contentType = (String) ret.get("contentType");
			String[] tmp = contentType.split("/");
			contentType = tmp[tmp.length - 1].toLowerCase();
			byte[] content = (byte[]) ret.get("content");
			String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + UUID.randomUUID();
			String orgFilePath = path + filename + "." + contentType;
			File orgFile = new File(orgFilePath);
			logger.warn("文件创建了：{}", orgFile.getAbsolutePath());
			FileOutputStream outStream = new FileOutputStream(orgFile);
			outStream.write(content);
			outStream.close();
			result = filename + "." + contentType;
			return result;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return result;
	}

	/**
	 * 获取openid
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	@SuppressWarnings({ "resource" })
	public static String getOauthOpenId(String appId, String appSecret, String code) {
		String openId = null;
		String url = BufferUtils.add("https://api.weixin.qq.com/sns/oauth2/access_token?appid=", appId, "&secret=",
				appSecret, "&code=", code, "&grant_type=authorization_code");
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse;
			httpResponse = client.execute(httpGet);
			int httpCode = httpResponse.getStatusLine().getStatusCode();
			String strResult = EntityUtils.toString(httpResponse.getEntity(), WechatData.CHAR_SET);
			if (httpCode == 200) {
				JSONObject jsonObject = JSONObject.parseObject(strResult);
				logger.warn(" get openId:{}", jsonObject);
				if (StringUtil.isNotBlank(jsonObject.getString("openid"))) {
					openId = jsonObject.getString("openid");
				}
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return openId;
	}

}
