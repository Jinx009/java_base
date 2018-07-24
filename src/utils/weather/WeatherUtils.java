package utils.weather;

import com.alibaba.fastjson.JSONObject;

import utils.HttpUtils;

public class WeatherUtils {
	
//	http://flash.weather.com.cn/wmaps/xml/china.xml 

	public static JSONObject get(String cityName){
		String result = HttpUtils.get("http://api.avatardata.cn/Weather/Query?key=a4dd8098ec3145e1a1509d6df78e0ab0&cityname="+cityName);
		return JSONObject.parseObject(result);
	}

	public static void main(String[] args) {
		System.out.println("==="+get("上海市"));
	}
	
}
