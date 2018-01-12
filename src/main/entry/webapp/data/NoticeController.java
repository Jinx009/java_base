package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import service.AccessTokenService;
import service.NoticeService;
import utils.Resp;

@Controller
@RequestMapping(value = "/rest")
public class NoticeController {

	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);

	@RequestMapping(path = "/notice",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Resp<?> notice(@RequestBody Object r){
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("notice msg:{}",JSON.toJSONString(r));
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("erroe:{}",e);
		}
		return resp;
		
	}
	
	@RequestMapping(path = "/noticeSwitch")
	@ResponseBody
	public Resp<?> noticeSwitch(){
		Resp<?> resp = new Resp<>(false);
		try {
			String res = NoticeService.notice();
			return new Resp<>(res);
		} catch (Exception e) {
			log.error("erroe:{}",e);
		}
		return resp;
		
	}
	
	@RequestMapping(path = "/token")
	@ResponseBody
	public Resp<?> token(){
		Resp<?> resp = new Resp<>(false);
		try {
			String res = AccessTokenService.getAccessToken();
			return new Resp<>(res);
		} catch (Exception e) {
			log.error("erroe:{}",e);
		}
		return resp;
		
	}
	
	
}
