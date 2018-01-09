package utils;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {

	private String url = "https://180.101.147.89:8743";  
    private String charset = "utf-8";  
    private HttpUtils httpClientUtil = null;  
      
    public HttpTest(){  
        httpClientUtil = new HttpUtils();  
    }  
      
    public void test(){  
        String httpOrgCreateTest = url + "/iocm/app/sec/v1.1.0/login";  
        Map<String,String> createMap = new HashMap<String,String>();  
        createMap.put("authuser","*****");  
        createMap.put("authpass","*****");  
        createMap.put("orgkey","****");  
        createMap.put("orgname","****");  
        createMap.put("appId","rbclARkLazNvQKlCEYzWEMh5MAYa");  
        createMap.put("secret","PfYwRjAgXTIxfIcUPl882tV82K8a");
        String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);  
        System.out.println("result:"+httpOrgCreateTestRtn);  
    }  
      
    public static void main(String[] args){  
    	HttpTest main = new HttpTest();  
        main.test();  
    }  
	
}
