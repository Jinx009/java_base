package service.basicFunctions;


import org.springframework.stereotype.Service;

import utils.HttpUtils;

@Service
public class HttpService {


	public String get(String url){
		return HttpUtils.get(url);
	}
	
	public String postJson(String url,String jsonStr){
		return HttpUtils.postJson(url, jsonStr);
	}
	
	public String postParams(String url){
		return HttpUtils.postParams(url);
	}
	
}
