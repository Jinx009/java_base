package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingNoticeDao;
import database.models.parking.ParkingNotice;

@Service
public class ParkingNoticeService {

	@Autowired
	private ParkingNoticeDao parkingNoticeDao;
	
	public void save(String name,String content,String startTime,String endTime,Integer status){
		ParkingNotice parkingNotice = new ParkingNotice();
		parkingNotice.setContent(content);
		parkingNotice.setEndTime(endTime);
		parkingNotice.setName(name);
		parkingNotice.setStartTime(startTime);
		parkingNotice.setCreateTime(new Date());
		parkingNotice.setStatus(status);
		parkingNotice.setShowStatus(1);
		parkingNoticeDao.save(parkingNotice);
	}
	
	public void update(String name,String content,String startTime,String endTime,Integer id,Integer status){
		ParkingNotice parkingNotice = parkingNoticeDao.find(id);
		parkingNotice.setContent(content);
		parkingNotice.setEndTime(endTime);
		parkingNotice.setName(name);
		parkingNotice.setStartTime(startTime);
		parkingNotice.setCreateTime(new Date());
		parkingNotice.setStatus(status);
		parkingNotice.setShowStatus(1);
		parkingNoticeDao.update(parkingNotice);
	}

	public List<ParkingNotice> findAll(){
		return parkingNoticeDao.findAll();
	}

	public void updateStatus(Integer id, Integer status) {
		ParkingNotice parkingNotice = parkingNoticeDao.find(id);
		parkingNotice.setStatus(status);
		parkingNoticeDao.update(parkingNotice);
	}

	public ParkingNotice findById(Integer id) {
		return parkingNoticeDao.find(id);
	}
	
}
