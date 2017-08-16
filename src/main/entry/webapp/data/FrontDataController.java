package main.entry.webapp.data;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.active.ActiveUser;
import service.basicFunctions.active.ActiveUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/front/d/")
public class FrontDataController {
	private static final Logger logger = LoggerFactory.getLogger(FrontDataController.class);
	
	@Autowired
	private ActiveUserService activeUserService;

	@RequestMapping(path = "/saveUser")
	@ResponseBody
	public Resp<?> saveUser(@RequestParam(value = "name",required = false)String name,
						    @RequestParam(value = "mobilePhone",required = false)String mobilePhone,
						    @RequestParam(value = "email",required = false)String email,
						    @RequestParam(value = "address",required = false)String address,
						    @RequestParam(value = "withNum",required = false)String withNum,
						    @RequestParam(value = "activeId",required = false)Integer activeId){
		Resp<?> resp = new Resp<>(false);
		try {
			ActiveUser activeUser = activeUserService.getByMobilePhone(mobilePhone);
			if(activeUser!=null){
				resp.setMsg("该手机号码已经报名！");
			}else{
				activeUser = new ActiveUser();
				activeUser.setActiveId(activeId);
				activeUser.setAddress(address);
				activeUser.setCreateTime(new Date());
				activeUser.setMobilePhone(mobilePhone);
				activeUser.setName(name);
				activeUser.setEmail(email);;
				activeUser.setWithNum(withNum);
				activeUserService.save(activeUser);
				resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,"");
				return resp;
			}
		} catch (Exception e) {
			logger.error("save error :{}",e);
		}
		
		return resp;
	}
	
}
