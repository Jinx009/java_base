package service.basicFunctions.subcribe;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.subcribe.SubcribeOrderDao;
import database.basicFunctions.dao.subcribe.SubcribeParkPlaceDao;
import database.models.subcribe.SubcribeOrder;
import database.models.subcribe.SubcribeParkPlace;
import utils.msg.AlimsgUtils;

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
		AlimsgUtils.sendSubcibe(mobilePhone, "SMS_142615214", "展为",subcribeParkPlace.getName(),dateStr+" "+subcribeOrder.getStartHour()+"时~"+subcribeOrder.getEndHour());
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

	public void del(Integer id) {
		SubcribeOrder subcribeOrder = subcribeOrderDao.find(id);
		AlimsgUtils.sendSubcibe(subcribeOrder.getMobilePhone(), "SMS_142615216", "展为",subcribeOrder.getParkName(),subcribeOrder.getDateStr()+" "+subcribeOrder.getStartHour()+"时~"+subcribeOrder.getEndHour());
		subcribeOrderDao.delete(id);
	}

	public SubcribeOrder get(Integer id) {
		return subcribeOrderDao.find(id);
	}
	
}
