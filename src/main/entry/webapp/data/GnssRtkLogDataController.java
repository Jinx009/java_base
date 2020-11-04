package main.entry.webapp.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
import database.model.GnssRtkConnLog;
import database.model.GnssRtkLog;
import database.model.GnssRtkNumLog;
import main.entry.webapp.BaseController;
import service.GnssRtkConnLogService;
import service.GnssRtkLogService;
import service.GnssRtkNumLogService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtklog")
public class GnssRtkLogDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GnssRtkLogDataController.class);
	
	@Autowired
	private GnssRtkLogService gnssRtkLogService;
	@Autowired
	private GnssRtkNumLogService gnssRtkNumLogService;
	@Autowired
	private GnssRtkConnLogService gnssRtkConnLogService;
	
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
	
	@RequestMapping(path = "/findNum")
	@ResponseBody
	public Resp<?> findNum(String mac,String start,String end){
		Resp<?> resp = new Resp<>(false);
		try {
			//2020-11-03 00:00:00 - 2020-11-03 00:00:00
			List<GnssRtkNumLog> list = gnssRtkNumLogService.findByMac(mac, start, end);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/findConn")
	@ResponseBody
	public Resp<?> findConn(String mac,int p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkConnLog> pages = gnssRtkConnLogService.findByMacAndP(p, mac);
			return new Resp<>(pages);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/find")
	@ResponseBody
	public Resp<?> find(String rovertag,String start,String end){
		Resp<?> resp = new Resp<>(false);
		try {
			List<GnssRtkLog> list = gnssRtkLogService.find(rovertag,start,end);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
