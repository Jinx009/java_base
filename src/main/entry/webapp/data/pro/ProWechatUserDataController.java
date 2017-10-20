package main.entry.webapp.data.pro;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.pro.ProWechatUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.pro.ProWechatUserService;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/front/d")
public class ProWechatUserDataController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ProWechatUserDataController.class);

	@Autowired
	private ProWechatUserService proWechatUserService;
	
	@RequestMapping(path = "/wechatUserList")
	@ResponseBody
	public Resp<?> getRegisterUser(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ProWechatUser> list = proWechatUserService.findRegisterUser();
			logger.warn("data:{}",list);
			return new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,list);
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/selectUser")
	@ResponseBody
	public Resp<?> usefulUser(Integer userId){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ProWechatUser> list = proWechatUserService.findUserfulUser(userId);
			logger.warn("data:{}",list);
			return new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,list);
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "userDetail")
	@ResponseBody
	public Resp<?> find(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			ProWechatUser proWechatUser = proWechatUserService.find(id);
			logger.warn("data:{}",proWechatUser);
			return new Resp<>(proWechatUser);
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "insertUser")
	@ResponseBody
	public Resp<?> save(ProWechatUser proWechatUser,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			logger.warn("user:{} is comming in ..." +proWechatUser.getRealName());
			proWechatUser.setNum("");
			proWechatUser.setStatus(1);
			Integer reId = (Integer) getSession(request,"reId");
			if(reId!=null&&reId!=0){
				ProWechatUser proWechatUser2 = proWechatUserService.find(reId);
				proWechatUser.setQrcode(proWechatUser2.getQrcode());
				proWechatUser.setReName(proWechatUser2.getRealName());
				proWechatUser.setReId(reId);
				if(proWechatUser2.getBeReNum()==null){
					proWechatUser2.setBeReNum(1);
				}else{
					proWechatUser2.setBeReNum(proWechatUser2.getBeReNum()+1);
				}
				proWechatUserService.update(proWechatUser2);
			}
			proWechatUserService.update(proWechatUser);
			proWechatUser = proWechatUserService.find(proWechatUser.getId());
			setSession(request,"name", proWechatUser.getRealName());
			setSession(request,"pic", proWechatUser.getPic1());
			return new Resp<>(proWechatUser);
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
}
