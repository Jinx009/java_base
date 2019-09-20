package main.entry.webapp.data.vedio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.vedio.VedioLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/vedioLog")
public class VedioLogController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(VedioLogController.class);
	
	@Autowired
	private VedioLogService vedioLogService;
	
	@RequestMapping(path = "/findByTaskId")
	@ResponseBody
	public Resp<?> findBy(Integer taskId){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(vedioLogService.findByTaskId(taskId));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/findById")
	@ResponseBody
	public Resp<?> findById(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(vedioLogService.findById(id));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
