package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import database.common.PageDataList;
import database.model.GnssRtkAccLog;
import main.entry.webapp.BaseController;
import service.GnssRtkAccLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtkacclog")
public class GnssRtkAccLogDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GnssRtkAccLogDataController.class);
	
	@Autowired
	private GnssRtkAccLogService gnssRtkAccLogService;
	
	
	public Resp<?> findPage(int p,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkAccLog> pageDataList = gnssRtkAccLogService.findByPageAndMac(p,mac);
			return new Resp<>(pageDataList);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
