package main.entry.webapp.data.wxapp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.wxapp.WxappUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.wxapp.WxappUserService;
import utils.HttpUtils;
import utils.Resp;
import utils.wxapp.WxappUtils;

@Controller
@RequestMapping(value = "/wxapp")
public class WxappDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(WxappDataController.class);
	
	@Autowired
	private WxappUserService wxappUserService;

	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String code){
		Resp<?> resp = new Resp<>(false);
		try {
			String res = HttpUtils.get("https://api.weixin.qq.com/sns/jscode2session?appid=wx75b1786be4c1c6c8&secret=025e8584c9962fda9067f3a0de782b86&js_code="+code+"&grant_type=authorization_code");
			JSONObject obj = JSONObject.parseObject(res);
			String openid = obj.getString("openid");
			WxappUser wxappUser = wxappUserService.findByOpenid(openid);
			if(wxappUser!=null){
				wxappUser.setUpdateTime(new Date());
				wxappUserService.update(wxappUser);
			}else{
				wxappUser = new WxappUser();
				wxappUser.setCreateTime(new Date());
				wxappUser.setUpdateTime(new Date());
				wxappUser.setOpenid(openid);
				wxappUser.setStatus(0);
				wxappUserService.save(wxappUser);
			}
			return new Resp<>(openid);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/level")
	@ResponseBody
	public Resp<?> level(String openid){
		Resp<?> resp = new Resp<>(0);
		try {
			WxappUser wxappUser = wxappUserService.findByOpenid(openid);
			if(wxappUser!=null){
				return new Resp<>(wxappUser.getStatus());
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	public Resp<?> send(){
		Resp<?> resp = new Resp<>(false);
		try {
//			String accessToken = WxappUtils.getAccessToken();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("touser", "oe5zd4nBn-ZYiGmPd4-MHUrVZz18");
			map.put("template_id", "5NwUahjnLzwNNutInGdNYUAI3oM7rq0z0_GKXG9mAwQ");
			map.put("miniprogram_state", "trial");
			map.put("lang", "oe5zd4nBn-ZYiGmPd4-MHUrVZz18");
			map.put("touser", "zh_CN");
			String jsonStr = "{"+
				      "\"thing1\": {"+
				          "\"value\": \"thing1\""+
				     " },"+
						"\"thing5\": {"+
				        "\"value\": \"thing5\""+
					   " },"+
						"\"character_string4\": {"+
					    "\"value\": \"character_string4\""+
					" },"+
					"\"thing6\": {"+
					"\"value\": \"thing6\""+
					"},"+
					"\"date8\": {"+
					"\"value\": \"date8\""+
					"},"+
				 " }";
			map.put("data", JSONObject.parseObject(jsonStr));
			
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	public static void main(String[] args) {
//		HttpUtils.get("https://api.weixin.qq.com/sns/jscode2session?appid=wx75b1786be4c1c6c8&secret=025e8584c9962fda9067f3a0de782b86&js_code=001UFp9H1STex20xhm6H17Yl9H1UFp9v&grant_type=authorization_code");
//		https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN
		String accessToken = WxappUtils.getAccessToken();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("touser", "oe5zd4nBn-ZYiGmPd4-MHUrVZz18");
		map.put("template_id", "5NwUahjnLzwNNutInGdNYUAI3oM7rq0z0_GKXG9mAwQ");
		map.put("miniprogram_state", "trial");
		map.put("lang", "zh_CN");
		String jsonStr = "{"+
			      "\"thing1\": {"+
			          "\"value\": \"thing1\""+
			     " },"+
					"\"thing5\": {"+
			        "\"value\": \"555\""+
				   " },"+
					"\"character_string4\": {"+
				    "\"value\": \"character_string4\""+
				" },"+
				"\"thing6\": {"+
				"\"value\": \"thing6\""+
				"},"+
				"\"date8\": {"+
				"\"value\": \"2020-04-26 00:00:00\""+
				"},"+
			 " }";
		map.put("data", JSONObject.parseObject(jsonStr));
		HttpUtils.postJson("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken, JSONObject.toJSONString(map));
	}
	
}
