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
	
	public ParkingNotice save(ParkingNotice parkingNotice){
		parkingNotice.setCreateTime(new Date());
		parkingNotice.setStatus(0);
		parkingNotice.setShowStatus(1);
		return parkingNoticeDao.save(parkingNotice);
	}

	public List<ParkingNotice> findAll(){
		return parkingNoticeDao.findAll();
	}

	public void updateStatus(Integer id, Integer status) {
		ParkingNotice parkingNotice = parkingNoticeDao.find(id);
		parkingNotice.setStatus(status);
		parkingNoticeDao.update(parkingNotice);
	}
	
}
