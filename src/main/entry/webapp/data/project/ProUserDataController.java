package main.entry.webapp.data.project;

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
@RequestMapping(value = "/home/d")
public class ProUserDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProUserDataController.class);
	
	@Autowired
	private ProUserService proUserService;
	
	@RequestMapping(path = "/proUser_list")
	@ResponseBody
	public Resp<?> list(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proUserService.pageList(p));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/proUser_add")
	@ResponseBody
	public Resp<?> add(String name,String card,String mobilePhone,String carNum){
		Resp<?> resp = new Resp<>(false);
		try {
			ProUser proUser = proUserService.findByCard(card);
			if(proUser!=null){
				return new Resp<>("卡片已存在！");
			}
			proUserService.saveNew(name,card,mobilePhone,carNum);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/proUser_delete")
	@ResponseBody
	public Resp<?> delete(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			proUserService.delete(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
