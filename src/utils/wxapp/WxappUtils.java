package utils.wxapp;

import com.alibaba.fastjson.JSONObject;

import utils.HttpUtils;

public class WxappUtils {

	public static String getAccessToken(){
		String res = HttpUtils.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx75b1786be4c1c6c8&secret=025e8584c9962fda9067f3a0de782b86");
		JSONObject obj = JSONObject.parseObject(res);
		return obj.getString("access_token");
	}
	
	public static void main(String[] args) {
		HttpUtils.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx75b1786be4c1c6c8&secret=025e8584c9962fda9067f3a0de782b86");
	}
	
}
