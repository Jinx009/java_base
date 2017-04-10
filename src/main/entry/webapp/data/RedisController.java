package main.entry.webapp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.WebTokenFactory;
import service.redis.RedisService;

@Controller
public class RedisController {
	
	@Autowired(required = false)
	private RedisService redisService;
	
	@RequestMapping(value = "redis/get")
	@ResponseBody
	public WebTokenFactory getWebTokenFactory(@RequestParam(value = "key",required = false)String key){
		WebTokenFactory webTokenFactory = (WebTokenFactory) redisService.getObj(key);
		return webTokenFactory;
	}
	

}
