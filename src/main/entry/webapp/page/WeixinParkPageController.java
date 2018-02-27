package main.entry.webapp.page;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.WeixinParkUser;
import main.entry.webapp.BaseController;
import service.common.WeixinParkUserService;
import utils.UnicodeUtils;
import utils.wechat.WechatUtil;

@Controller
public class WeixinParkPageController extends BaseController{
	
	@Autowired
	private WeixinParkUserService weixinParkUserService;

	private static final Logger log = LoggerFactory.getLogger(WeixinParkPageController.class);
	
	
	/**
	 * 微信授权获取昵称
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wx/p/me")
	public String me(HttpServletRequest request,String code){
		WeixinParkUser user = getSessionWebUser(request);
		try {
			if(null==user){
				if (StringUtil.isNotBlank(code)) {
					String[] s = WechatUtil.getOpenid(code);
					if(null!=s){
						String accessToken = s[0];
						String openid = s[1];
						user = weixinParkUserService.findByOpenid(openid);
						JSONObject json = WechatUtil.getRealUserInfo(accessToken, openid);
						if(null==user){
							user = new WeixinParkUser();
							user.setCreateTime(new Date());
							user.setHeadimgUrl(json.getString("headimgurl"));
							user.setNickName(UnicodeUtils.string2Unicode(json.getString("nickname")));
							user.setOpenid(openid);
							user =  weixinParkUserService.save(user);
						}else{
							user.setNickName(UnicodeUtils.string2Unicode(json.getString("nickname")));
							user.setHeadimgUrl(json.getString("headimgurl"));
							weixinParkUserService.update(user);
						}
						setSessionUser(request, user);
					}
				} 
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return "/wx/me";
	}
	
	
	@RequestMapping(value = "/wx/park")
	public String park(HttpServletRequest request,String code){
		WeixinParkUser user = getSessionWebUser(request);
		try {
			if(null==user){
				if (StringUtil.isNotBlank(code)) {
					String[] s = WechatUtil.getOpenid(code);
					if(null!=s){
						String accessToken = s[0];
						String openid = s[1];
						user = weixinParkUserService.findByOpenid(openid);
						JSONObject json = WechatUtil.getRealUserInfo(accessToken, openid);
						if(null==user){
							user = new WeixinParkUser();
							user.setCreateTime(new Date());
							user.setHeadimgUrl(json.getString("headimgurl"));
							user.setNickName(UnicodeUtils.string2Unicode(json.getString("nickname")));
							user.setOpenid(openid);
							user =  weixinParkUserService.save(user);
						}else{
							user.setNickName(UnicodeUtils.string2Unicode(json.getString("nickname")));
							user.setHeadimgUrl(json.getString("headimgurl"));
							weixinParkUserService.update(user);
						}
						setSessionUser(request, user);
						request.setAttribute("plate_number", user.getPlateNumber());
					}
				} 
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return "/wx/park";
	}
}
