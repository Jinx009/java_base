package service.basicFunctions.subcribe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.subcribe.SubcribeOrderDao;
import database.basicFunctions.dao.subcribe.SubcribeParkPlaceDao;
import database.common.QueryParam;
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
		subcribeOrder.setStatus(1);
		subcribeOrder.setNoticeType(0);
		subcribeOrder.setStartHour(startHour);
		subcribeOrderDao.save(subcribeOrder);
	}
	
	public List<SubcribeOrder> needSend(){
		QueryParam queryParam = QueryParam.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		queryParam.addParam("dateStr", sdf.format(date));
		queryParam.addParam("noticeType", 1);
		return subcribeOrderDao.findByCriteria(queryParam);
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
		AlimsgUtils.sendSubcibe(subcribeOrder.getMobilePhone(), "SMS_142615216", "展为",subcribeOrder.getParkName(),subcribeOrder.getDateStr()+" "+subcribeOrder.getStartHour()+"时~"+subcribeOrder.getEndHour()+"时");
		subcribeOrder.setStatus(0);
		subcribeOrderDao.update(subcribeOrder);
	}
	
	public void notice(Integer id,Integer type) {
		SubcribeOrder subcribeOrder = subcribeOrderDao.find(id);
		subcribeOrder.setNoticeType(type);
		subcribeOrderDao.update(subcribeOrder);
	}

	public SubcribeOrder get(Integer id) {
		return subcribeOrderDao.find(id);
	}
	
}
