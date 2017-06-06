package main.entry.webapp.data.pro;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.pro.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.pro.ProUserService;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/data/")
public class ProUserDataController extends BaseController{

	@Autowired
	private ProUserService proUserService;
	
	/**
	 * 后台用户登录
	 * @param request
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@RequestMapping(path = "/pro_user/home/login",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> login(HttpServletRequest request,
			@RequestParam(name = "userName",required = false)String userName,
			@RequestParam(name = "pwd",required = false)String pwd){
		Resp<?> resp = new Resp<>(false);
		ProUser proUser = proUserService.login(userName, pwd);
		if(proUser!=null){
			setSessionProUser(request, proUser);
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
		}
		return resp;
	}
	
	/**
	 * 判断后台用户是否存在
	 * @param request
	 * @param userName
	 * @return
	 */
	@RequestMapping(path = "/pro_user/home/check",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> check(HttpServletRequest request,
			@RequestParam(name = "userName",required = false)String userName){
		Resp<?> resp = new Resp<>(false);
		ProUser proUser = proUserService.checkExits(userName);
		if(proUser!=null){
			setSessionProUser(request, proUser);
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
		}
		return resp;
	}
	
	/**
	 * 新增后台用户
	 * @param request
	 * @param proUser
	 * @return
	 */
	@RequestMapping(path = "/pro_user/home/save",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> save(HttpServletRequest request,
			 			ProUser proUser){
		Resp<?> resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
		proUser.setCreateTime(new Date());
		proUser.setUpdateTime(new Date());
		proUserService.save(proUser);
		return resp;
	}
	
	/**
	 * 新增后台用户
	 * @param request
	 * @param proUser
	 * @return
	 */
	@RequestMapping(path = "/pro_user/home/update",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> update(HttpServletRequest request,
			 			ProUser proUser){
		Resp<?> resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
		ProUser proUser2 = proUserService.findById(proUser.getId());
		proUser2.setPwd(proUser.getPwd());
		proUserService.update(proUser2);
		return resp;
	}
}
