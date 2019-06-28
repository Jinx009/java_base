package main.entry.webapp.data.project.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProUserService;
import utils.Resp;

@Controller
@RequestMapping(value = "/f/pro_user")
public class FrontProUserDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(FrontProUserDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String userName,String pwd){
		Resp<?> resp = new Resp<>(false);
		try {
			ProUser proUser = proUserService.findByUserName(userName);
			if(proUser==null){
				proUser = proUserService.register(userName, pwd);
				Resp<ProUser> resp2 = new Resp<>(true);
				resp2.setMsg("注册成功！");
				resp2.setData(proUser);
				return resp2;
			}else{
				proUser = proUserService.login(userName, pwd);
				if(proUser!=null){
					Resp<ProUser> resp2 = new Resp<>(true);
					resp2.setMsg("登录成功！");
					resp2.setData(proUser);
					return resp2;
				}else{
					resp.setMsg("账号或密码错误！");
					return resp;
				}
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
