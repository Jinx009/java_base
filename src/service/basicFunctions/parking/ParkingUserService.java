package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingUserDao;
import database.models.parking.ParkingUser;

@Service
public class ParkingUserService {

	@Autowired
	private ParkingUserDao parkingUserDao;
	
	public List<ParkingUser> findAll(){
		return parkingUserDao.findAll();
	}
	
	public ParkingUser save(ParkingUser parkingUser){
		parkingUser.setCreateTime(new Date());
		parkingUser.setShowStatus(1);
		parkingUser.setBlackList(0);
		parkingUser.setAccount(0.00);
		return parkingUserDao.save(parkingUser);
	}
	
	public void setBlackList(Integer id){
		ParkingUser parkingUser = parkingUserDao.find(id);
		parkingUser.setBlackList(1);
		parkingUserDao.update(parkingUser);
	}
	
}
