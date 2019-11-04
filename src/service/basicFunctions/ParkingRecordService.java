package service.basicFunctions;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.ParkingRecordDao;
import database.models.ParkingRecord;

@Service
public class ParkingRecordService {

	@Autowired
	private ParkingRecordDao parkingRecordDao;
	

	public void save(ParkingRecord vedioLog) {
		parkingRecordDao.save(vedioLog);
	}
	
}
