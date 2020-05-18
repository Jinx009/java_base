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

	public List<ProGoods> findByDate(String date) {
		List<ProGoods> list = proGoodsDao.findByDate(date);
		List<ProPrice> prices = proPriceDao.findOrderLevel();
		if (list != null && !list.isEmpty()) {
			List<ProGoods> goodsList = new ArrayList<ProGoods>();
			for (ProPrice price : prices) {
				ProGoods good = null;
				for (ProGoods goods : list) {
						if(goods.getTime().equals(price.getTime())){
							goods.setAName(price.getAName());
							goods.setBName(price.getBName());
							goods.setCName(price.getCName());
							goods.setDName(price.getDName());
							goods.setAPrice(price.getAPrice());
							goods.setBPrice(price.getBPrice());
							goods.setCPrice(price.getCPrice());
							goods.setDPrice(price.getDPrice());
							proGoodsDao.update(goods);
							good = goods;
							break;
						}
				}
				if(good!=null){
					goodsList.add(good);
				}else{
					good = new ProGoods();
					good.setAName(price.getAName());
					good.setBName(price.getBName());
					good.setCName(price.getCName());
					good.setDName(price.getDName());
					good.setDate(date);
					good.setTime(price.getTime());
					good.setCreateTime(new Date());
					good.setAPrice(price.getAPrice());
					good.setAType(0);
					good.setBPrice(price.getBPrice());
					good.setBType(0);
					good.setCPrice(price.getCPrice());
					good.setCType(0);
					good.setDPrice(price.getDPrice());
					good.setDType(0);
					proGoodsDao.save(good);
					goodsList .add(good);
				}
			}
			return goodsList;
		} else {
			list = new ArrayList<ProGoods>();
			for (ProPrice price : prices) {
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
