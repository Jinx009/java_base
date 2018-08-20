package main.entry.webapp.data.subcribe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.subcribe.SubcribeUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.subcribe.SubcribeUserService;
import utils.Resp;

@Controller
@RequestMapping(value = "/subcribe/d/user")
public class SubcribeUserDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(SubcribeUserDataController.class);
	
	@Autowired
	private SubcribeUserService subcribeUserService;
	
	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String mobilePhone,String pwd,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			SubcribeUser subcribeUser = subcribeUserService.login(mobilePhone, pwd);
			if(subcribeUser==null){
				resp.setMsg("账号密码错误！");
				return resp;
			}else{
				HttpSession session = request.getSession();
				session.setAttribute("subcribeUser", subcribeUser);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/register")
	@ResponseBody
	public Resp<?> register(String mobilePhone,String pwd,String plateNumber,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			SubcribeUser subcribeUser = subcribeUserService.findByMobilePhone(mobilePhone);
			if(subcribeUser!=null){
				resp.setMsg("账号已存在！");
				return resp;
			}else{
				subcribeUserService.save(mobilePhone, pwd, plateNumber, name);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/sessionUser")
	@ResponseBody
	public Resp<?> getSessionUser(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			HttpSession session = request.getSession();
			return new Resp<>(session.getAttribute("subcribeUser"));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
