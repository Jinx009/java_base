package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.ParkingListDao;
import database.models.ParkingList;

@Service
public class ParkingListService {

	@Autowired
	private ParkingListDao parkingListDao;
	
	public void save(ParkingList parkingList){
		parkingListDao.save(parkingList);
	}
	
}
