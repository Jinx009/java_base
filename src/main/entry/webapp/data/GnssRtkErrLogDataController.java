package main.entry.webapp.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import database.common.PageDataList;
import database.model.GnssRtkErrLog;
import main.entry.webapp.BaseController;
import service.GnssRtkErrLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtkerrlog")
public class GnssRtkErrLogDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GnssRtkErrLogDataController.class);
	
	@Autowired
	private GnssRtkErrLogService gnssRtkErrLogService;
	
	public Resp<?> findByPageAndMac(int p,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkErrLog> pageDataList = gnssRtkErrLogService.findByPageAndMac(p,mac);
			return new Resp<>(pageDataList);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
