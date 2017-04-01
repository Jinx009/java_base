package main.entry.webapp.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.User;
import lombok.Setter;

@Controller
@Setter
public class UserController {

	
	private Map<String,Object> data;

	/*
	 * 根据用户id获取用户信息
	 */
	@RequestMapping(value = "/data/test")
	@ResponseBody
	public Map<String,Object> userList(HttpServletResponse response,HttpServletRequest request) throws IOException{
		data = new HashMap<String,Object>();
		User user = new User();
		user.setId(1);
		user.setNickName("345");
		data.put("data",user);
//		HttpWebIOHelper._printWebJson(data, response);
		return data;
	}
	
	
	
}
