package service.basicFunctions.subcribe;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.subcribe.SubcribeOrderDao;
import database.basicFunctions.dao.subcribe.SubcribeParkPlaceDao;
import database.models.subcribe.SubcribeOrder;
import database.models.subcribe.SubcribeParkPlace;

@Service
public class SubcribeOrderService {

	@Autowired
	private SubcribeOrderDao subcribeOrderDao;
	@Autowired
	private SubcribeParkPlaceDao subcribeParkPlaceDao;
	
	public void save(String mobilePhone,Integer startHour,Integer endHour,Integer parkId,String plateNumber,String dateStr){
		SubcribeOrder subcribeOrder = new SubcribeOrder();
		subcribeOrder.setCreateTime(new Date());
		subcribeOrder.setDateStr(dateStr);
		subcribeOrder.setEndHour(endHour);
		subcribeOrder.setMobilePhone(mobilePhone);
		subcribeOrder.setParkId(parkId);
		SubcribeParkPlace subcribeParkPlace = subcribeParkPlaceDao.find(parkId);
		subcribeOrder.setParkName(subcribeParkPlace.getName());
		subcribeOrder.setPlateNumber(plateNumber);
		subcribeOrder.setStartHour(startHour);
		subcribeOrderDao.save(subcribeOrder);
	}
	
	public int left(Integer parkId,String dateStr,Integer startHour,Integer endHour){
		SubcribeParkPlace subcribeParkPlace = subcribeParkPlaceDao.find(parkId);
		int num = subcribeOrderDao.getUse(parkId, startHour, endHour, dateStr);
		return subcribeParkPlace.getNumber() - num;
	}
	
	public List<SubcribeOrder> findByMobilePhone(String mobilePhone){
		return subcribeOrderDao.findByMobilePhone(mobilePhone);
	}
	
	public int getUseByPlateNumber(Integer parkId,String dateStr,Integer startHour,Integer endHour,String plateNumber){
		return subcribeOrderDao.getUseByPlateNumber(parkId, startHour, endHour, dateStr, plateNumber);
	}
	
}
