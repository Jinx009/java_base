package main.entry.webapp.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.model.GnssRtkBatteryLog;
import main.entry.webapp.BaseController;
import service.GnssRtkBatteryLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtkbatterylog")
public class GnssRtkBatteryLogDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GnssRtkBatteryLogDataController.class);
	
	@Autowired
	private GnssRtkBatteryLogService gnssRtkBatteryLogService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> findByPage(int p,String imei){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkBatteryLog> list = gnssRtkBatteryLogService.findByPage(p, imei);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return  resp;
	}
	
	
	@RequestMapping(path = "/all")
	@ResponseBody
	public Resp<?> all(String imei,String start,String end) throws Exception{
		Resp<?> resp = new Resp<>(false);
		try {
			List<GnssRtkBatteryLog> list = gnssRtkBatteryLogService.all(imei,start,end);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return  resp;
	}
	
}
