package service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis公用类
 * @author jinx
 *
 */
@Service
public class RedisService{

	@Autowired
	protected StringRedisTemplate stringRedisTemplate;
	@Autowired
	protected RedisTemplate<String,Object> redisTemplate;
	
	public String setObj(String key,Object obj){
		redisTemplate.opsForValue().set(key,obj);
		return key;
	}
	
	public String setString(String key,String str){
		stringRedisTemplate.opsForValue().set(key,str);
		return key;
	}
	
	public void clearObj(String key){
		redisTemplate.opsForValue().set(key,null);
	}
	
	public String get(String key){
		return stringRedisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 取出对象
	 * @param key
	 * @return
	 */
	public Object getObj(String key){
		return redisTemplate.opsForValue().get(key);
	}
}
