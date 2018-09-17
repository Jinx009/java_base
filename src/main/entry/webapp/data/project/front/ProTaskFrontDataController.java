package main.entry.webapp.data.project.front;

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
import database.models.project.ProTask;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProTaskService;
import utils.Resp;

@Controller
@RequestMapping(value = "/front/d/pro_task")
public class ProTaskFrontDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(ProTaskFrontDataController.class);
	
	@Autowired
	private ProTaskService proTaskService;
	
	@RequestMapping(path = "/waitList")
	@ResponseBody
	public Resp<?> list(HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			ProDriver proDriver = getSessionFrontUser(request);
			List<ProTask> list = proTaskService.findWait(proDriver.getMobilePhone());
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/frontWaitList")
	@ResponseBody
	public Resp<?> frontList(HttpServletRequest request,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			ProDriver proDriver = getSessionFrontUser(request);
			PageDataList<ProTask> list = proTaskService.frontWaitList(proDriver.getMobilePhone(),p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/frontDoneList")
	@ResponseBody
	public Resp<?> frontDoneList(HttpServletRequest request,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			ProDriver proDriver = getSessionFrontUser(request);
			PageDataList<ProTask> list = proTaskService.frontDoneList(proDriver.getMobilePhone(),p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/doneList")
	@ResponseBody
	public Resp<?> doneList(HttpServletRequest request,Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			ProDriver proDriver = getSessionFrontUser(request);
			PageDataList<ProTask> list = proTaskService.homeList(p, 1, proDriver.getName(), 0);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/detail")
	@ResponseBody
	public Resp<?> detail(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proTaskService.find(id));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/changeStatus")
	@ResponseBody
	public Resp<?> changeStatus(Integer id,HttpServletRequest request){
		Resp<?> resp = new Resp<>(false);
		try {
			ProDriver proDriver = getSessionFrontUser(request);
			proTaskService.changeStatus(id,proDriver.getName(),proDriver.getMobilePhone());
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
}
