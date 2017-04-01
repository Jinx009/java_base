package main.entry.webapp.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.User;
import lombok.Setter;
import service.redis.RedisService;

@Controller
@Setter
public class UserController {

	@Autowired
	private RedisService redisService;
	
	private Map<String,Object> data;

	/*
	 * 根据用户id获取用户信息
	 */
	@RequestMapping(value = "/data/test")
	@ResponseBody
	public Map<String,Object> userList(HttpServletResponse response,HttpServletRequest request) throws IOException{
		redisService.setString("KEY123","KEY125663");
		data = new HashMap<String,Object>();
		User user = new User();
		user.setId(1);
		user.setNickName("345");
		data.put("data",user);
		data.put("redis",redisService.get("KEY123"));
		return data;
	}
	
	
	
}
