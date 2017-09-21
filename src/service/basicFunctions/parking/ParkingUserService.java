package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingMonthCardDao;
import database.basicFunctions.dao.parking.ParkingUserDao;
import database.models.home.HomeUser;
import database.models.parking.ParkingMonthCard;
import database.models.parking.ParkingUser;

@Service
public class ParkingUserService {

	@Autowired
	private ParkingUserDao parkingUserDao;
	@Autowired
	private ParkingMonthCardDao parkingMonthCardDao;
	
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
	
	public void setBlackList(Integer id, Integer black){
		ParkingUser parkingUser = parkingUserDao.find(id);
		parkingUser.setBlackList(black);
		parkingUserDao.update(parkingUser);
	}

	public void setSpecial(Integer id, Integer status) {
		ParkingUser parkingUser = parkingUserDao.find(id);
		parkingUser.setType(status);
		parkingUserDao.update(parkingUser);
	}

	public void setMonth(Integer id, Integer monthId, HomeUser homeUser) {
		ParkingMonthCard parkingMonthCard = parkingMonthCardDao.find(monthId);
		ParkingUser parkingUser = parkingUserDao.find(id);
		
		parkingMonthCard.setOperator(homeUser.getId());
		parkingMonthCard.setOperatorName(homeUser.getRealName());
		parkingMonthCard.setStatus(1);
		parkingMonthCard.setUserId(id);
		parkingMonthCard.setPlateNo(parkingUser.getPlateNo());
		
		parkingUser.setMonthNo(parkingMonthCard.getCardNo());
		parkingUser.setTotalCost(parkingMonthCard.getPrice());
		parkingUser.setType(parkingMonthCard.getType());
		
		parkingUserDao.update(parkingUser);
	}

	public ParkingUser find(Integer _id) {
		return parkingUserDao.find(_id);
	}
	
}
