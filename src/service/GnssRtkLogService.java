package service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkLogDao;
import database.model.GnssRtkLog;

@Service
public class GnssRtkLogService {

	@Autowired
	private GnssRtkLogDao gnssRtkLogDao;
	
	public void save(GnssRtkLog gnssRtkLog, int i){
		gnssRtkLog.setType(i);
		gnssRtkLog.setStatus(1);
		gnssRtkLogDao.save(gnssRtkLog);
	}
	
	public void saveStatus(GnssRtkLog gnssRtkLog, int i){
		gnssRtkLog.setStatus(0);
		gnssRtkLog.setType(i);
		gnssRtkLogDao.save(gnssRtkLog);
	}

	public PageDataList<GnssRtkLog> findByPage(Integer p, String mac, int type, int status) {
		return gnssRtkLogDao.pages(p,mac,type,status);
	}

	public List<GnssRtkLog> find(String rovertag, String start, String end) throws Exception {
		return gnssRtkLogDao.findByRoverTagAndDate(rovertag,start,end);
	}

	public List<GnssRtkLog> findByMacAndDate(String mac, Date date, int start, int nowHour, int i) throws ParseException {
		return gnssRtkLogDao.findByMacAndDate(mac,date,start,nowHour,i);
	}
	
	public GnssRtkLog findByMacAndDate(String mac, String date, int start, int nowHour, int i) throws ParseException {
		return gnssRtkLogDao.findByMacAndDate(mac,date,start,nowHour,i);
	}
	
}
