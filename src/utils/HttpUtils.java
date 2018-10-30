package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author jinx
 * http辅助
 */
@SuppressWarnings("deprecation")
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);     
	
	/**
	 * http发送json
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String postJson(String url,String jsonStr){
		logger.warn("HttpUtils.postJson[info:{},{}]",url,jsonStr);
		String result = "500";
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type","application/json;charset=utf-8");
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
        try {
        	DefaultHttpClient httpClient = new DefaultHttpClient();
        	HttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.postJson[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.postJson[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.postJson[IOException.error:{}]",e);
		}
        return result;
	}
	
	/**
	 * http发送json
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String postMofangJson(String sessionId,String url,String jsonStr){
		logger.warn("HttpUtils.postJson[info:{},{}]",url,jsonStr);
		String result = "500";
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type","application/json;charset=utf-8");
        post.addHeader("sessionId",sessionId);
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));
        try {
        	DefaultHttpClient httpClient = new DefaultHttpClient();
        	HttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.postJson[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.postJson[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.postJson[IOException.error:{}]",e);
		}
        return result;
	}
	
	/**
	 * post发送http请求
	 * @param url
	 * @return
	 */
    @SuppressWarnings("resource")
	public static String postParams(String url){
    	logger.warn("HttpUtils.postParams[info:{}]",url);
        String result = "500";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
		try {
			HttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.postParams[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.postParams[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.postParams[IOException.error:{}]",e);
		}
		return result;
    }
    
    /**
     * http get
     * @param url
     * @return
     */
    @SuppressWarnings("resource")
	public static String getMofang(String sessionId,String url){
    	logger.warn("HttpUtils.get[info:{}]",url);
        String result = "500";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.addHeader("sessionId",sessionId);
		try {
			HttpResponse response = httpClient.execute(get);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.get[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.get[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.get[IOException.error:{}]",e);
		}
		return result;
    }
    
	/**
	 * post发送http请求
	 * @param url
	 * @return
	 */
    @SuppressWarnings("resource")
	public static String postMofangParams(String sessionId,String url){
    	logger.warn("HttpUtils.postParams[info:{}]",url);
        String result = "500";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader("sessionId",sessionId);
		try {
			HttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.postParams[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.postParams[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.postParams[IOException.error:{}]",e);
		}
		return result;
    }
    
    /**
     * http get
     * @param url
     * @return
     */
    @SuppressWarnings("resource")
	public static String get(String url){
    	logger.warn("HttpUtils.get[info:{}]",url);
        String result = "500";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(get);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.get[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.get[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.get[IOException.error:{}]",e);
		}
		return result;
    }
    
    protected final String retrieveResponseFromServer(final URL validationUrl,  
            final String ticket) {  
        HttpURLConnection connection = null;  
        try {  
            connection = (HttpURLConnection) validationUrl.openConnection();  
            final BufferedReader in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream()));  
  
            String line;  
            final StringBuffer stringBuffer = new StringBuffer(255);  
  
            synchronized (stringBuffer) {  
                while ((line = in.readLine()) != null) {  
                    stringBuffer.append(line);  
                    stringBuffer.append("\n");  
                }  
                return stringBuffer.toString();  
            }  
  
        } catch (final IOException e) {  
        	logger.error("error:{}",e);  
            return null;  
        } catch (final Exception e1){  
        	logger.error("error:{}",e1);  
            return null;  
        }finally {  
            if (connection != null) {  
                connection.disconnect();  
            }  
        }  
    }  
    
    
    public static void main(String[] args) {
		postParams("https://api.opg-iot.cn/thingpark/lrc/rest/downlink?DevEUI=00:95:69:00:00:00:06:DD&FPort=1&Payload=480032025200&FCntDn=1234");
	}

	@SuppressWarnings("resource")
	public static String getMofangV2(String path, Map<String, String> map) {
		logger.warn("HttpUtils.get[info:{}]",JSONObject.toJSONString(map));
        String result = "500";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(HttpData.MOFANG_URL+path);
        get.addHeader("X-POS-REQUEST-ID",map.get("X-POS-REQUEST-ID"));
        get.addHeader("X-POS-REQUEST-TIME",map.get("X-POS-REQUEST-TIME"));
        get.addHeader("X-POS-ACCESS-KEY",map.get("X-POS-ACCESS-KEY"));
        get.addHeader("X-POS-REQUEST-SIGN",map.get("X-POS-REQUEST-SIGN"));
		try {
			HttpResponse response = httpClient.execute(get);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.get[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.get[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.get[IOException.error:{}]",e);
		}
		return result;
	}

	@SuppressWarnings("resource")
	public static String postJsonMofangV2(String url, Map<String, String> map,String data) {
		logger.warn("HttpUtils.postJson[info:{},{}]",JSONObject.toJSONString(map),data);
		String result = "500";
        HttpPost post = new HttpPost(HttpData.MOFANG_URL+url);
        post.addHeader("Content-type","application/json;charset=utf-8");
        post.addHeader("X-POS-REQUEST-ID",map.get("X-POS-REQUEST-ID"));
        post.addHeader("X-POS-REQUEST-TIME",map.get("X-POS-REQUEST-TIME"));
        post.addHeader("X-POS-ACCESS-KEY",map.get("X-POS-ACCESS-KEY"));
        post.addHeader("X-POS-REQUEST-SIGN",map.get("X-POS-REQUEST-SIGN"));
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
        try {
        	DefaultHttpClient httpClient = new DefaultHttpClient();
        	HttpResponse response = httpClient.execute(post);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.warn("HttpUtils.postJson[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.postJson[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.postJson[IOException.error:{}]",e);
		}
        return result;
	}
	
}
