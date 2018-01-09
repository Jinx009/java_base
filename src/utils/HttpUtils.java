package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map.Entry;  
import org.apache.http.message.BasicNameValuePair;  

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
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public String doPost(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
    
}
