package main.entry.webapp.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import database.common.PageDataList;
import database.model.GnssRtkAccLog;
import database.model.GnssRtkConnLog;
import database.model.GnssRtkHeartLog;
import database.model.GnssRtkLog;
import database.model.GnssRtkNumLog;
import main.entry.webapp.BaseController;
import service.GnssRtkAccLogService;
import service.GnssRtkConnLogService;
import service.GnssRtkHeartLogService;
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
	@Autowired
	private GnssRtkHeartLogService gnssRtkHeartLogService;
	@Autowired
	private GnssRtkAccLogService gnssRtkAccLogService;
	
	@RequestMapping(path = "/page")
	@ResponseBody
	public Resp<?> pageList(Integer p,String mac,int type,int status){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkLog> pages = gnssRtkLogService.findByPage(p,mac,type,status);
			return new Resp<>(pages);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/findHeart")
	@ResponseBody
	public Resp<?> findHeart(Integer p,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkHeartLog> pages = gnssRtkHeartLogService.findByPage(p,mac);
			return new Resp<>(pages);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/findAcc")
	@ResponseBody
	public Resp<?> findAcc(Integer p,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkAccLog> pages = gnssRtkAccLogService.findByPage(p,mac);
			return new Resp<>(pages);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "createNum")
	@ResponseBody
	public Resp<?> pageList(String date,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			for(int i = 0;i<24;i++) {
				GnssRtkNumLog numLog = gnssRtkNumLogService.find(date,mac, i);
				if(numLog==null) {
					numLog = new GnssRtkNumLog();
					numLog.setCreateTime(new Date());
					numLog.setDate(date);
					numLog.setStartHour(i);
					numLog.setEndHour((i+1));
					numLog.setNum(0);
					numLog.setMac(mac);
					numLog.setType("RTCM");
					gnssRtkNumLogService.save(numLog);
				}
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "createLog")
	@ResponseBody
	public Resp<?> createLog(String date,String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateS = new Date();
			int k = 24;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateS);
			int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
			if(sdf.format(dateS).equals(date)) {
				k = nowHour;
			}
			for(int i = 0;i<k;i++) {
				GnssRtkLog log = gnssRtkLogService.findByMacAndDate(mac, date, i, (i+1), 0);
				if(log==null) {
					log = new GnssRtkLog();
					log.setRovertag(mac);
					log.setDatetime(String.valueOf(sdf2.parse(date+" "+getHour(i)+":00:00").getTime()));
					log.setCreateTime(new Date());
					log.setType(0);
					gnssRtkLogService.saveStatus(log, 0);
				}
				log = gnssRtkLogService.findByMacAndDate(mac, date, i, (i+1), 1);
				if(log==null) {
					log = new GnssRtkLog();
					log.setRovertag(mac);
					log.setDatetime(String.valueOf(sdf2.parse(date+" "+getHour(i)+":00:00").getTime()));
					log.setCreateTime(new Date());
					log.setType(1);
					gnssRtkLogService.saveStatus(log, 0);
				}
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	private String getHour(int nowHour) {
		if(nowHour<10) {
			return "0"+nowHour;
		}
		return String.valueOf(nowHour);
	}

	
	@RequestMapping(path = "/findNum")
	@ResponseBody
	public Resp<?> findNum(String mac,String start,String end){
		Resp<?> resp = new Resp<>(false);
		try {
			//2020-11-03 00:00:00 - 2020-11-03 00:00:00
			List<GnssRtkNumLog> list = gnssRtkNumLogService.findByMac(mac, start, end);
			List<GnssRtkNumLog> returnList = new ArrayList<GnssRtkNumLog>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String dateStr = sdf.format(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int nowHour = calendar.get(Calendar.HOUR_OF_DAY);	
			date = sdf.parse(dateStr+" 00:00:00");
			for(GnssRtkNumLog log : list) {
				Date logDate = sdf.parse(log.getDate());
				if(date.after(logDate)) {
					returnList.add(log);
				}
				if(dateStr.equals(log.getDate())&&nowHour>=log.getStartHour()) {
					returnList.add(log);
				}
			}
			return new Resp<>(returnList);
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
	public Resp<?> find(String rovertag,String start,String end,int type,int status){
		Resp<?> resp = new Resp<>(false);
		try {
			List<GnssRtkLog> list = gnssRtkLogService.find(rovertag,start,end,type,status);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
