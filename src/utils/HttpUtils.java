package utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HttpUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);   
	
	 /**
     * http get
     * @param url
     * @return
     */
    @SuppressWarnings({ "resource"})
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
	 * post发送http请求
	 * @param url
	 * @return
	 */
    @SuppressWarnings("resource")
	public static String postSun(String url,String token){
    	logger.warn("HttpUtils.postParams[info:{}]",url);
        String result = "500";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader("token",token);
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
    
	
}
