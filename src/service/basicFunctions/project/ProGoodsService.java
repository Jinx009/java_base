package service.basicFunctions.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import database.basicFunctions.dao.project.ProGoodsDao;
import database.basicFunctions.dao.project.ProPriceDao;
import database.models.project.ProGoods;
import database.models.project.ProGoodsModel;
import database.models.project.ProPrice;
import utils.WeekUtils;

@Service
public class ProGoodsService {

	@Autowired
	private ProGoodsDao proGoodsDao;
	@Autowired
	private ProPriceDao proPriceDao;

	public List<ProGoodsModel> findByDate(String date) throws ParseException {
		List<ProGoodsModel> models = new ArrayList<ProGoodsModel>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int week = WeekUtils.getWeek(sdf.parse(date));
		int weekStatus = 0;
		if(week==0||week==6){
			weekStatus = 1;
		}
		List<ProPrice> prices = proPriceDao.findOrderLevelWeek(weekStatus);
		Set<String> set = new LinkedHashSet<String>();
		for (ProPrice p : prices) {
			set.add(p.getTime());
		}
		for (String s : set) {
			ProGoodsModel model = new ProGoodsModel();
			model.setTime(s);
			List<ProGoods> goods = new ArrayList<>();
			ProGoods a = proGoodsDao.findByDateTimeAbc(date,s,"A");
			ProGoods b = proGoodsDao.findByDateTimeAbc(date,s,"B");
			ProGoods c = proGoodsDao.findByDateTimeAbc(date,s,"C");
			ProGoods d = proGoodsDao.findByDateTimeAbc(date,s,"D");
			if (a == null) {
				a = new ProGoods();
				a.setAbc("A");
				a.setCreateTime(new Date());
				a.setDate(date);
				a.setTime(s);
				ProPrice price = proPriceDao.findByTimeAbc("A",s);
				a.setName(price.getName());
				a.setPrice(price.getPrice());
				a.setType(0);
				a = proGoodsDao.save(a);
			}
			if (b == null) {
				b = new ProGoods();
				b.setAbc("B");
				b.setCreateTime(new Date());
				b.setDate(date);
				b.setTime(s);
				ProPrice price = proPriceDao.findByTimeAbc("B",s);
				b.setName(price.getName());
				b.setPrice(price.getPrice());
				b.setType(0);
				b = proGoodsDao.save(b);
			}
			if (c == null) {
				c = new ProGoods();
				c.setAbc("C");
				c.setCreateTime(new Date());
				c.setDate(date);
				c.setTime(s);
				ProPrice price = proPriceDao.findByTimeAbc("C",s);
				c.setName(price.getName());
				c.setPrice(price.getPrice());
				c.setType(0);
				c = proGoodsDao.save(c);
			}
			if (d == null) {
				d = new ProGoods();
				d.setAbc("D");
				d.setCreateTime(new Date());
				d.setDate(date);
				d.setTime(s);
				ProPrice price = proPriceDao.findByTimeAbc("D",s);
				d.setName(price.getName());
				d.setPrice(price.getPrice());
				d.setType(0);
				d = proGoodsDao.save(d);
			}
			goods.add(a);
			goods.add(b);
			goods.add(c);
			goods.add(d);
			model.setPlace(goods);
			models.add(model);
		}
		return models;
	}

	public void update(ProGoods proGoods) {
		proGoodsDao.update(proGoods);
	}

	public ProGoods findById(Integer id) {
		return proGoodsDao.find(id);
	}

	public Object findByDateH(String date) {
		return proGoodsDao.findByDate(date);
	}

	public ProGoods findByTimeDateAbc(String time, String type, String date) {
		return proGoodsDao.findByDateTimeAbc(date,time,type);
	}

	public List<ProGoods> findByDateUpdate(String time, String name) {
		return proGoodsDao.findByDateUpdate(time,name);
	}

	public void save(ProGoods proGoods) {
		proGoodsDao.save(proGoods);
	}


}
