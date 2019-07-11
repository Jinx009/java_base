package database.basicFunctions.dao.project;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import common.helper.StringUtil;
import database.common.BaseDao;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.common.PageDataList;
import database.models.project.ProOrder;
import utils.BaseConstant;

@Repository
public class ProOrderDao extends  BaseDao<ProOrder>{

	public PageDataList<ProOrder> homeList(Integer p,Integer type,String orderDate){
		QueryParam queryParam = QueryParam.getInstance();
		if(type!=null&&type!=0){
			queryParam.addParam("type",type);
		}
		if(StringUtil.isNotBlank(orderDate)){
			queryParam.addParam("orderDate",orderDate);
		}
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public ProOrder findByUserId(String userId, String date, String orderTime, Integer type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", type);
		queryParam.addParam("userId", userId);
		queryParam.addParam("orderDate", date);
		return findByCriteriaForUnique(queryParam);
	}
	
	public Integer findDivingStatus(String date, String orderTime) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", 1);
		queryParam.addParam("status", 0);
		queryParam.addParam("orderDate", date);
		List<ProOrder> list = findByCriteria(queryParam);
		int num = 0;
		for(ProOrder proOrder : list){
			num += proOrder.getNum();
		}
		if(num>15){
			return 0;
		}
		return (15-num);
	}
	
	public Integer findPoolStatus(String date, String orderTime) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", 2);
		queryParam.addParam("status", 0);
		queryParam.addParam("orderDate", date);
		List<ProOrder> list = findByCriteria(queryParam);
		int num = 0;
		for(ProOrder proOrder : list){
			num += proOrder.getNum();
		}
		if(num>15){
			return 0;
		}
		return (15-num);
	}
	
	public Integer findRoomStatus( String date, String orderTime) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", 3);
		queryParam.addParam("status", 0);
		queryParam.addParam("orderDate", date);
		List<ProOrder> list = findByCriteria(queryParam);
		int num = 0;
		if(list!=null){
			if(list.size()>=3){
				return 0;
			}else{
				num = list.size();
			}
		}
		return (3-num);
	}

	public List<ProOrder> myOrder(Integer userId) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userId", userId);
		queryParam.addOrder(OrderType.DESC, "id");
		return findByCriteria(queryParam);
	}

	public List<ProOrder> findOrder(Integer userId, String orderDate, String orderTime, int type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("userId", userId);
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("orderDate", orderDate);
		queryParam.addParam("type", type);
		return findByCriteria(queryParam);
	}

	public List<ProOrder> findOrder(String orderDate, String orderTime, int type) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("orderDate", orderDate);
		queryParam.addParam("type", type);
		return findByCriteria(queryParam);
	}

	public List<ProOrder> findOrderByTime(String dateStr, String time) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", time);
		queryParam.addParam("orderDate", dateStr);
		return findByCriteria(queryParam);
	}

	public int findOrderNum(String orderDate, String orderTime) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("orderTime", orderTime);
		queryParam.addParam("type", 2);
		queryParam.addParam("orderDate", orderDate);
		List<ProOrder> list = findByCriteria(queryParam);
		QueryParam queryParam2 = QueryParam.getInstance();
		queryParam2.addParam("orderTime", orderTime);
		queryParam2.addParam("type", 1);
		queryParam2.addParam("orderDate", orderDate);
		List<ProOrder> list2= findByCriteria(queryParam2);
		int num1 = 0;
		int num2 = 0;
		if(list!=null&&!list.isEmpty()){
			for(ProOrder order : list){
				num1+= order.getJlNum();
				num1+= order.getNum();
			}
		}
		if(list2!=null&&!list2.isEmpty()){
			for(ProOrder order : list2){
				num2+= order.getJlNum();
				num2+= order.getNum();
			}
		}
		
		return getDateStatus(orderDate,orderTime)- num1-num2;
	}
	
	public static int getDateStatus(String orderDate,String orderTime){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar ca = Calendar.getInstance();
	        Date d = df.parse(orderDate);
	        ca.setTime(d);//设置当前时间
	        if(ca.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && ca.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&"夜间".equals(orderTime)){
	        	return 10;
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
       return 25;
	}
	
	public static void main(String[] args) {
		System.out.println(getDateStatus("2019-07-13", "夜间"));
	}
	
}
