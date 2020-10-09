package service.basicFunctions.project;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProOrderDao;
import database.common.PageDataList;
import database.models.project.ProOrder;

@Service
public class ProOrderService {

	@Autowired
	private ProOrderDao proOrderDao;
	
	public ProOrder saveOrder(ProOrder proOrder){
		return proOrderDao.save(proOrder);
	}
	
	public PageDataList<ProOrder> getByOpenid(String openid,Integer p){
		return proOrderDao.findPage(openid,p);
	}

	public ProOrder findById(Integer id) {
		return proOrderDao.find(id);
	}
	
	public void update(ProOrder proOrder) {
		proOrderDao.update(proOrder);
	}

	public List<ProOrder> findByDateTimeType(String date, String time, String type) {
		return proOrderDao.findByDateTimeType(date,time,type);
	}

	public PageDataList<ProOrder> findPage(Integer fromSite, String fromDate, String toDate,Integer p) {
		return proOrderDao.findPageH(fromSite, fromDate,toDate,p);
	}

	public List<ProOrder> findByStatus(int status) {
		return proOrderDao.findByStatus(status);
	}
	
	public void del(int id){
		proOrderDao.delete(id);
	}

	public Map<String, Double> getByM(String date) {
		String f = date.split(" - ")[0];
		String e = date.split(" - ")[1];
		List<ProOrder> list = proOrderDao.findByM(f,e);
		Map<String, Double> map = new HashMap<String, Double>();
		Double payed = 0.00;
		Double payfail = 0.00;
		if(list!=null){
			for(ProOrder o : list){
				if(o.getStatus()==1){
					payed+= o.getPrice();
				}else{
					payfail += o.getPrice();
				}
			}
		}
		map.put("payed",payed );
		map.put("payfail", payfail);
		return map;
	}

	public ProOrder getByMsg(String out_trade_no) {
		return proOrderDao.findByMsg(out_trade_no);
	}
	
}
