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
public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);     
	
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
			logger.warn("post json[res:{}]",result);
		} catch (ParseException e) {
			logger.error("[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("[IOException.error:{}]",e);
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
			logger.warn("post params[res:{}]",result);
		} catch (ParseException e) {
			logger.error("post params[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("post params[IOException.error:{}]",e);
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
			logger.warn("get[res:{}]",result);
		} catch (ParseException e) {
			logger.error("get[ParseException.error:{}]",e);
		} catch (IOException e) {
			logger.error("get[IOException.error:{}]",e);
		}
		return result;
    }


    @SuppressWarnings("resource")
	public static String postTextJson(String url, String json) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.addHeader("Content-type", "text/json; charset=utf-8");
            post.addHeader("Accept", "text/json");
            post.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
            HttpResponse response = httpClient.execute(post);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.warn("[HttpUtils.postJson data:{}]", jsonStr);
            return jsonStr;
        } catch (Exception e) {
            logger.error("[HttpUtils.postJson data:{},error:{}]", json + url, e);
        }
        return "";
    }
	
}
