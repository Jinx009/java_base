package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	@SuppressWarnings("resource")
	public static String putUnicomJson(String url,String jsonStr,String secret){
		logger.warn("HttpUtils.postJson[info:{},{}]",url,jsonStr);
		String result = "500";
        HttpPut post = new HttpPut(url);
        post.addHeader("Content-type","application/json;charset=utf-8");
        post.addHeader("Authorization","Basic "+secret);
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
	
	@SuppressWarnings("resource")
	public static String postBeijingQjJson(String url,String jsonStr,String apikey){
		logger.warn("HttpUtils.postJson[info:{},{}]",url,jsonStr);
		String result = "500";
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type","application/json;charset=utf-8");
        post.addHeader("apikey",apikey);
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
	
	
	@SuppressWarnings("resource")
	public static String putJson(String url,String jsonStr){
		logger.warn("HttpUtils.postJson[info:{},{}]",url,jsonStr);
		String result = "500";
        HttpPut post = new HttpPut(url);
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
    
    /**
     * http get
     * @param url
     * @return
     */
    @SuppressWarnings("resource")
	public static String getPuzhiJob(String deviceId){
    	//logger.warn("HttpUtils.get[info:{}]",deviceId);
        String result = "500";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://ghiot.cigem.cn/api/devices/"+deviceId+"/deliveryHistory");
        get.setHeader("appkey", "KFKFxvyVAmAC6O-cSefzy31hcvPyc77HijsSLw9wQPND-GVeuw4seLDQdXR");
		try {
			HttpResponse response = httpClient.execute(get);
			result = EntityUtils.toString(response.getEntity(),"UTF-8");
			//logger.warn("HttpUtils.get[res:{}]",result);
		} catch (ParseException e) {
			logger.error("HttpUtils.get[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("HttpUtils.get[IOException.error:{}]",e);
		}
		return result;
    }
	
	
    
    /**
     * 标准post
     * @param url
     * @param param
     * @return
     */
    public static String sendPost(String url, String param) {
        logger.warn("HttpUtils.sendPost url:{},param:{}",url,param);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            logger.warn("HttpUtils.sendPost result:{}",result);
        } catch (Exception e) {
            logger.error("[HttpUtils.sendPost data:{},error:{}]", param + url, e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

	@SuppressWarnings("resource")
	public static void postPuzhiJob(String deviceId,String jsonStr) {
		logger.warn("HttpUtils.postJson[deviceId:{}]",deviceId);
		String result = "500";
        HttpPost post = new HttpPost("http://ghiot.cigem.cn/api/devices/"+deviceId+"/deliveryResponse");
        post.addHeader("Content-type","application/json;charset=utf-8");
        post.addHeader("appkey", "KFKFxvyVAmAC6O-cSefzy31hcvPyc77HijsSLw9wQPND-GVeuw4seLDQdXR");
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
	}

	@SuppressWarnings("resource")
	public static void postGuizhouJson(String url, String json, String key) {
		logger.warn("HttpUtils.postJson[info:{},{}]",url,json);
		String result = "500";
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-type","application/json;charset=utf-8");
        post.addHeader("api-key",key);
        post.setHeader("Accept", "application/json");
        post.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
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
	}
}
