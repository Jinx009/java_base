package service.basicFunctions.project;


import database.basicFunctions.dao.project.ProPriceDao;
import database.common.PageDataList;
import database.models.project.ProPrice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProPriceService {

	@Autowired
	private ProPriceDao proPriceDao;
	
	public List<ProPrice> findAll(){
		return proPriceDao.findAll();
	}
	
	public void update(ProPrice proPrice){
		proPriceDao.update(proPrice);
	}

	public ProPrice findById(Integer id) {
		return proPriceDao.find(id);
	}

	public Object findByTime(String time) {
		return proPriceDao.findByTime(time);
	}

	public PageDataList<ProPrice> findByPage(int p) {
		return proPriceDao.findByPage(p);
	}
	
}
