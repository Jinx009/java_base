package service.basicFunctions.project;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGoodsDao;
import database.basicFunctions.dao.project.ProOrderAddDao;
import database.common.PageDataList;
import database.models.project.ProGoods;
import database.models.project.ProOrderAdd;
import utils.HttpUtils;

@Service
public class ProOrderAddService {

	@Autowired
	private ProOrderAddDao proOrderAddDao;
	@Autowired
	private ProGoodsDao proGoodsDao;

	public static String remark;

	public void add(String date, String time, String address, String mobilePhone, String userName) {
		ProOrderAdd o = new ProOrderAdd();
		o.setAddress(address);
		o.setCreateTime(new Date());
		o.setDate(date);
		o.setTime(time);
		o.setMobilePhone(mobilePhone);
		o.setUserName(userName);
		o = proOrderAddDao.save(o);
		String f = date.split(" - ")[0];
		String e = date.split(" - ")[1];
		remark = "";
		changeGoods(f, e, time, address);
		o.setRemark(remark);
		proOrderAddDao.update(o);
	}

	public void changeGoods(String f, String e, String time, String address) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			HttpUtils.get("https://football.amize.cn/f/pro_goods/list?date="+f);
			Date fd = sdf.parse(f);
			Date ed = sdf.parse(e);
			if (fd.before(ed)) {
				ProGoods proGoods = proGoodsDao.findByDateTimeName(f, time, address);
				if (proGoods.getType() == 1||proGoods.getType() == 2) {
					remark += f + " " + time + " " + address + " 已经占用<br>";
				} else {
					proGoods.setType(2);
					proGoodsDao.update(proGoods);
				}
				Calendar c = Calendar.getInstance();
				c.setTime(fd);
				c.add(Calendar.DAY_OF_MONTH, 1);
				fd = c.getTime();
				changeGoods(sdf.format(fd), e, time, address);
			} else {
				ProGoods proGoods = proGoodsDao.findByDateTimeName(f, time, address);
				if (proGoods.getType() == 1||proGoods.getType() == 2) {
					remark += f + " " + time + " " + address + " 已经占用<br>";
				} else {
					proGoods.setType(2);
					proGoodsDao.update(proGoods);
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public PageDataList<ProOrderAdd> findByPage(int p) {
		return proOrderAddDao.findByPage(p);
	}

}
