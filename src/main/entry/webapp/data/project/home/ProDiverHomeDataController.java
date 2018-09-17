package main.entry.webapp.data.project.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.models.project.ProDriver;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProDriverService;
import service.basicFunctions.project.ProLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_driver")
public class ProDiverHomeDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProDiverHomeDataController.class);
	
	@Autowired
	private ProDriverService proDriverService;
	@Autowired
	private ProLogService proLogService;
	
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(Integer p,String mobilePhone,String name,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<ProDriver> list = proDriverService.homeList(p, name, mobilePhone);
			proLogService.saveLog(getSessionHomeUser(req).getRealName(), "查看司机列表");
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "selectList")
	@ResponseBody
	public Resp<?> selectList(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<ProDriver> list = proDriverService.selectList();
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(String mobilePhone,String name,String pwd,String plateNumber,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			ProDriver proDriver = proDriverService.findByMobilePhone(mobilePhone);
			if(proDriver!=null){
				resp.setMsg("该手机号已经存在！");
				return resp;
			}
			proDriverService.save(mobilePhone,name,pwd,plateNumber);
			proLogService.saveLog(getSessionHomeUser(req).getRealName(), "新建司机"+mobilePhone);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "del")
	@ResponseBody
	public Resp<?> del(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			proDriverService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "update")
	@ResponseBody
	public Resp<?> update(String mobilePhone,String name,String pwd,String plateNumber,Integer id,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			proDriverService.update(mobilePhone,name,pwd,plateNumber,id);
			proLogService.saveLog(getSessionHomeUser(req).getRealName(), "更新司机"+mobilePhone);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
}
