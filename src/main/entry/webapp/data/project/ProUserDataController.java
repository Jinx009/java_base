package main.entry.webapp.data.project;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.service.ProUserService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/user")
public class ProUserDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProUserDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "/pageList")
	@ResponseBody
	public Resp<?> list(Integer p,Integer type,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProUser> data = proUserService.homeList(p, type, name);
			return new Resp<>(data);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/homeSave")
	@ResponseBody
	public Resp<?> homeSave(String mobilePhone,Integer type,String name,String desc,String pwd){
		Resp<?> resp = new Resp<>(false);
		try {
			proUserService.save(mobilePhone,type,name,desc,"","","",pwd);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/resetPwd")
	@ResponseBody
	public Resp<?> resetPwd(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			proUserService.resetPwd(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/updateType")
	@ResponseBody
	public Resp<?> updateType(Integer id,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			proUserService.updateType(id,type);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/updateRemark")
	@ResponseBody
	public Resp<?> updateRemark(Integer id,String desc,String remarkA){
		Resp<?> resp = new Resp<>(false);
		try {
			proUserService.updateRemark(id,desc,remarkA);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/frontSave")
	@ResponseBody
	public Resp<?> frontSave(String mobilePhone,String pwd,String name){
		Resp<?> resp = new Resp<>(false);
		try {
			proUserService.save(mobilePhone,1,name,"","","","","");
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/me")
	@ResponseBody
	public Resp<?> me(Integer userId){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proUserService.findById(userId));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(Integer userId,String name,String pwd,String remarkB){
		Resp<?> resp = new Resp<>(true);
		try {
			proUserService.updateN(userId,name,pwd,remarkB);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
