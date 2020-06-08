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
import utils.WeekUtils;

@Service
public class ProOrderAddService {

	@Autowired
	private ProOrderAddDao proOrderAddDao;
	@Autowired
	private ProGoodsDao proGoodsDao;

	public static String remark;

	public void add(String date, String time, String address, String mobilePhone, String userName, Integer week) {
		ProOrderAdd o = new ProOrderAdd();
		o.setAddress(address);
		o.setCreateTime(new Date());
		o.setDate(date);
		o.setTime(time);
		o.setMobilePhone(mobilePhone);
		o.setUserName(userName);
		o.setWeek(week);
		o = proOrderAddDao.save(o);
		String f = date.split(" - ")[0];
		String e = date.split(" - ")[1];
		remark = "";
		changeGoods(f, e, time, address, week);
		o.setRemark(remark);
		proOrderAddDao.update(o);
	}

	public void changeGoods(String f, String e, String time, String address, int week) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date fd = sdf.parse(f);
			int weekD = WeekUtils.getWeek(fd);
			Date ed = sdf.parse(e);
			if (fd.before(ed)) {
				if (week == weekD) {
					HttpUtils.get("https://football.amize.cn/f/pro_goods/list?date=" + f);
					ProGoods proGoods = proGoodsDao.findByDateTimeName(f, time, address);
					if (proGoods.getType() == 1 || proGoods.getType() == 2) {
						remark += f + " " + time + " " + address + " 已经占用<br>";
					} else {
						proGoods.setType(2);
						proGoodsDao.update(proGoods);
					}
				}
				Calendar c = Calendar.getInstance();
				c.setTime(fd);
				c.add(Calendar.DAY_OF_MONTH, 1);
				fd = c.getTime();
				changeGoods(sdf.format(fd), e, time, address, week);
			} else {
				if (week == weekD) {
					ProGoods proGoods = proGoodsDao.findByDateTimeName(f, time, address);
					if (proGoods.getType() == 1 || proGoods.getType() == 2) {
						remark += f + " " + time + " " + address + " 已经占用<br>";
					} else {
						proGoods.setType(2);
						proGoodsDao.update(proGoods);
					}
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
