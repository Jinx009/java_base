package service.basicFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.AliParkingDao;
import database.common.PageDataList;
import database.models.AliParking;

@Service
public class AliParkingService {

	@Autowired
	private AliParkingDao aliParkingDao;
	
	public void save(AliParking aliParking) {
		aliParkingDao.save(aliParking);
	}

	public PageDataList<AliParking> list() {
		return aliParkingDao.list();
	}
	
}
