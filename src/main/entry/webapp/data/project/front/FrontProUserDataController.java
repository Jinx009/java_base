package main.entry.webapp.data.project.front;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProUserService;
import utils.HttpUtils;
import utils.RandomUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/f/pro_user")
public class FrontProUserDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(FrontProUserDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "/getOpenid")
	@ResponseBody
	public Resp<?> getOpenid(String code){
		Resp<?> resp = new Resp<>(false);
		try {
			String res = HttpUtils.get("https://api.weixin.qq.com/sns/jscode2session?appid=wxbc5ec9a82883abb6&secret=0c1cb35d2f554e66cd8dbaa1b533fbee&js_code="+code+"&grant_type=authorization_code");
			JSONObject obj = JSONObject.parseObject(res);
			String openid = obj.getString("openid");
			return new Resp<>(openid);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取验证码
	 * @param mobilePhone
	 * @return
	 */
	@RequestMapping(path = "/getCode")
	@ResponseBody
	public Resp<?> getCode(String mobilePhone){
		Resp<?> resp = new Resp<>(false);
		try {
			String code = RandomUtils.code();
			return new Resp<>(code);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String openid,String mobilePhone){
		Resp<?> resp = new Resp<>(false);
		try {
			ProUser proUser = proUserService.getByOpenid(openid);
			if(proUser==null){
				proUser = new ProUser();
				proUser.setCreateTime(new Date());
				proUser.setMobilePhone(mobilePhone);
				proUser.setOpenid(openid);
				proUserService.save(proUser);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
