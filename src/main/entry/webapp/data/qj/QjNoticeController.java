package main.entry.webapp.data.qj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.qj.QjNoticeService;
import utils.Resp;

@RequestMapping(value = "/home/cloud/qjNotcie")
@Controller
public class QjNoticeController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(QjNoticeController.class);
	
	@Autowired
	private QjNoticeService qjNoticeService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(qjNoticeService.findAll());
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String mac) {
		Resp<?> resp = new Resp<>(false);
		try {
			qjNoticeService.save(mac);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/del")
	@ResponseBody
	public Resp<?> del(Integer id) {
		Resp<?> resp = new Resp<>(false);
		try {
			qjNoticeService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	
	
	
}
