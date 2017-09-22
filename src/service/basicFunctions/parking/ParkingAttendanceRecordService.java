package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingAttendanceRecordDao;
import database.models.parking.ParkingAttendanceRecord;
import utils.DateUtils;

@Service
public class ParkingAttendanceRecordService {

	@Autowired
	private ParkingAttendanceRecordDao parkingAttendanceRecordDao;
	
	public List<ParkingAttendanceRecord> findAll(){
		return parkingAttendanceRecordDao.findAll();
	}
	
	public ParkingAttendanceRecord save(ParkingAttendanceRecord parkingAttendanceRecord){
		Date date = new Date();
		parkingAttendanceRecord.setCreateTime(date);
		parkingAttendanceRecord.setToWorkStatus(1);
		parkingAttendanceRecord.setToWorkTime(date);
		parkingAttendanceRecord.setWorkTime(0.00);
		parkingAttendanceRecord.setWeekTime(DateUtils.getWeek(date));
		parkingAttendanceRecord.setShowStatus(1);
		parkingAttendanceRecord.setOffWorkStatus(0);
		return parkingAttendanceRecordDao.save(parkingAttendanceRecord);
	}
	
	public void updateOffWorkTime(Integer id){
		
	}
	
}
