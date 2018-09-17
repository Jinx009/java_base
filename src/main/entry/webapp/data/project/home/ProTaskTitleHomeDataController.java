package main.entry.webapp.data.project.home;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProLogService;
import service.basicFunctions.project.ProTaskService;
import service.basicFunctions.project.ProTaskTitleService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/d/pro_task_title")
public class ProTaskTitleHomeDataController extends BaseController{

	private static final Logger log =  LoggerFactory.getLogger(ProTaskTitleHomeDataController.class);
	
	@Autowired
	private ProTaskTitleService proTaskTitleService;
	@Autowired
	private ProTaskService proTaskService;
	@Autowired
	private ProLogService proLogService;
	
	@RequestMapping(path = "/selectList")
	@ResponseBody
	public Resp<?> selectList(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proTaskTitleService.list());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/homeList")
	@ResponseBody
	public Resp<?> homeList(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proTaskTitleService.homeList(p));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(Integer titleId,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			proTaskTitleService.update(titleId);
			proTaskService.changeShowStatus(titleId);
			proLogService.saveLog(getSessionHomeUser(req).getRealName(), "删除导入"+titleId);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
