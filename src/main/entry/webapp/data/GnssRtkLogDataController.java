package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.model.GnssRtkLog;
import main.entry.webapp.BaseController;
import service.GnssRtkLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtklog")
public class GnssRtkLogDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GnssRtkLogDataController.class);
	
	@Autowired
	private GnssRtkLogService gnssRtkLogService;
	
	@RequestMapping(path = "/page")
	@ResponseBody
	public Resp<?> pageList(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkLog> pages = gnssRtkLogService.findByPage(p);
			return new Resp<>(pages);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
