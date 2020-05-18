package service.basicFunctions.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGoodsDao;
import database.basicFunctions.dao.project.ProPriceDao;
import database.models.project.ProGoods;
import database.models.project.ProPrice;

@Service
public class ProGoodsService {

	@Autowired
	private ProGoodsDao proGoodsDao;
	@Autowired
	private ProPriceDao proPriceDao;
	
	public List<ProGoods> findByDate(String date){
		List<ProGoods> list = proGoodsDao.findByDate(date);
		if(list!=null&&!list.isEmpty()){
			return list;
		}else{
			list = new ArrayList<ProGoods>();
			List<ProPrice> prices = proPriceDao.findOrderLevel();
			for(ProPrice price:prices){
				ProGoods proGoods = new ProGoods();
				proGoods.setAName(price.getAName());
				proGoods.setBName(price.getBName());
				proGoods.setCName(price.getCName());
				proGoods.setDName(price.getDName());
				proGoods.setDate(date);
				proGoods.setTime(price.getTime());
				proGoods.setCreateTime(new Date());
				proGoods.setAPrice(price.getAPrice());
				proGoods.setAType(0);
				proGoods.setBPrice(price.getBPrice());
				proGoods.setBType(0);
				proGoods.setCPrice(price.getCPrice());
				proGoods.setCType(0);
				proGoods.setDPrice(price.getDPrice());
				proGoods.setDType(0);
				proGoodsDao.save(proGoods);
				list.add(proGoods);
			}
			return list;
		}
	}

	public void update(ProGoods proGoods) {
		proGoodsDao.update(proGoods);
	}

	public ProGoods findByDateTimeName(String date, String time) {
		return proGoodsDao.findByDateTimeName(date, time);
	}
	
}
