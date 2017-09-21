package service.basicFunctions.parking;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.home.HomeUserDao;
import database.basicFunctions.dao.parking.ParkingMonthCardDao;
import database.basicFunctions.dao.parking.ParkingUserDao;
import database.models.home.HomeUser;
import database.models.parking.ParkingMonthCard;
import database.models.parking.ParkingUser;
import utils.RandomUtils;

@Service
public class ParkingMonthCardService {

	@Autowired
	private ParkingMonthCardDao parkingMonthCardDao;
	@Autowired
	private HomeUserDao homeUserDao;
	@Autowired
	private ParkingUserDao parkingUserDao;
	
	public List<ParkingMonthCard> findAll(Integer status, Integer type) {
		return parkingMonthCardDao.findAll(status,type);
	}
	
	public List<ParkingMonthCard> findAll(){
		return parkingMonthCardDao.findAll();
	}
	
	public ParkingMonthCard save(ParkingMonthCard parkingMonthCard,Integer homeUserId){
		HomeUser homeUser = homeUserDao.find(homeUserId);
		
		parkingMonthCard.setCreateTime(new Date());
		parkingMonthCard.setShowStatus(1);
		parkingMonthCard.setStatus(0);
		parkingMonthCard.setCardNo(getCardNo(16));
		parkingMonthCard.setCreator(homeUserId);
		parkingMonthCard.setCreatorName(homeUser.getRealName());
		
		return parkingMonthCardDao.save(parkingMonthCard);
	}
	
	public void delete(Integer id){
		ParkingMonthCard parkingMonthCard = parkingMonthCardDao.find(id);
		parkingMonthCard.setShowStatus(0);
		parkingMonthCardDao.update(parkingMonthCard);
	}
	
	public void updateStatus(Integer id,Integer homeUserId,Integer userId){
		ParkingMonthCard parkingMonthCard = parkingMonthCardDao.find(id);
		HomeUser homeUser = homeUserDao.find(homeUserId);
		ParkingUser parkingUser = parkingUserDao.find(userId);
		
		Date startDate = new Date();
		Date endDate = getEndDate(startDate, parkingMonthCard.getType());
		parkingMonthCard.setEndTime(endDate);
		parkingMonthCard.setStartTime(startDate);
		parkingMonthCard.setStatus(1);
		parkingMonthCard.setOperator(homeUserId);
		parkingMonthCard.setOperatorName(homeUser.getRealName());
		parkingMonthCard.setUserId(userId);
		parkingMonthCard.setPlateNo(parkingUser.getPlateNo());
		
		parkingMonthCardDao.update(parkingMonthCard);
	}
	
	public boolean hasCardCode(String cardNo){
		return parkingMonthCardDao.findByCardCode(cardNo);
	}
	
	private Date getEndDate(Date startDate,Integer type){
		Date endDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		if(1==type){//月卡
			cal.add(Calendar.MONTH, +1);
			endDate = cal.getTime();
		}else if(2==type){//季卡
			cal.add(Calendar.MONTH, +3);
			endDate = cal.getTime();
		}else if(3==type){//半年卡
			cal.add(Calendar.MONTH, +6);
			endDate = cal.getTime();
		}else if(4==type){//年卡
			cal.add(Calendar.YEAR, +1);
			endDate = cal.getTime();
		}
		return endDate;
	}
	
	private String getCardNo(int length){
		String result = "";
		do{
			result = RandomUtils.getSpecialRandomCode(length);
		} 
		while (hasCardCode(result));
		return result;
	}

	public boolean checkUse(Integer monthId) {
		ParkingMonthCard parkingMonthCard = parkingMonthCardDao.find(monthId);
		if(parkingMonthCard!=null&&0==parkingMonthCard.getStatus())
			return true;
		return false;
	}

	
	
}
