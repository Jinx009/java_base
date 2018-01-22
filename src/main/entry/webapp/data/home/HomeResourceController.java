package main.entry.webapp.data.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeResource;
import lombok.Setter;
import service.basicFunctions.home.HomeResourceRoleService;
import service.basicFunctions.home.HomeResourceService;
import utils.Resp;
import utils.RespData;
import utils.model.HomeConfigConstant;


@Controller
@Setter
@RequestMapping(value = "/home/d")
public class HomeResourceController {

	private static final Logger logger = LoggerFactory.getLogger(HomeResourceController.class);
	
	@Autowired
	private HomeResourceService homeResourceService;
	@Autowired
	private HomeResourceRoleService homeResourceRoleService;
	
	@RequestMapping(path = "/saveResourceRole",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> saveResourceRole(Integer roleId,String arr){
		Resp<?> resp = new Resp<>(false);
		try {
			String[] s = arr.split("@");
			homeResourceRoleService.save(roleId,s);
			return new Resp<>(true);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/resourceRole",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> resourceRole(Integer roleId){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(homeResourceRoleService.findByRoleId(roleId));
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/menu",method = RequestMethod.POST)
	@ResponseBody
	public Resp<?> getMenu(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			HttpSession session = request.getSession();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,HomeConfigConstant.getResourceBySession(session));
//			logger.warn(" [HomeResourceController.getMenu][data:{}] ",resp);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	/**
	 * 后台管理页面菜单列表
	 * @return
	 */
	@RequestMapping(path = "/page")
	@ResponseBody
	public Resp<?> getResource(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<HomeResource> list = homeResourceService.getPageResource();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			logger.warn("[data:{}] ",resp);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	/**
	 * 后台管理数据菜单列表
	 * @return
	 */
	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> getDataResource(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<HomeResource> list = homeResourceService.getDataResource();
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,list);
			logger.warn("[data:{}] ",resp);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	
	
	/**
	 * 新增数据菜单
	 * @return
	 */
	@RequestMapping(path = "/resource_data_add")
	@ResponseBody
	public Resp<?> dataAdd(String name,String description,String uri){
		Resp<?> resp = new Resp<>(false);
		try {
			homeResourceService.saveData(name,description,uri);
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
	
	/**
	 * 更改数据状态
	 * @param name
	 * @param description
	 * @param uri
	 * @return
	 */
	@RequestMapping(path = "/resource_data_status")
	@ResponseBody
	public Resp<?> dataStatus(Integer status,Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			homeResourceService.updateStatus(status,id);
			resp = new Resp<>(RespData.OK_CODE,RespData.OK_MSG,null);
		} catch (Exception e) {
			logger.error("[error:{}] ",e);
		}
		return resp;
	}
}
