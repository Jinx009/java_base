package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.MD5Util;
import common.helper.StringUtil;
import database.basicFunctions.dao.project.ProDriverDao;
import database.common.PageDataList;
import database.models.project.ProDriver;

@Service
public class ProDriverService {

	@Autowired
	private ProDriverDao proDriverDao;
	
	public void save(ProDriver proDriver){
		proDriverDao.save(proDriver);
	}
	
	public void update(ProDriver proDriver){
		proDriverDao.update(proDriver);
	}
	
	public PageDataList<ProDriver> homeList(Integer p,String name,String mobilePhone){
		return proDriverDao.homeList(p, name, mobilePhone);
	}

	public ProDriver login(String mobilePhone, String pwd) {
		pwd = MD5Util.md5(pwd);
		return proDriverDao.login(mobilePhone,pwd);
	}
	
	public ProDriver login_m(String mobilePhone, String pwd) {
		return proDriverDao.login(mobilePhone,pwd);
	}

	public ProDriver findByMobilePhone(String mobilePhone) {
		return proDriverDao.findByMobilePhone(mobilePhone);
	}

	public void save(String mobilePhone, String name, String pwd, String plateNumber) {
		ProDriver proDriver = new ProDriver();
		proDriver.setCreateTime(new Date());
		proDriver.setMobilePhone(mobilePhone);
		proDriver.setName(name);
		proDriver.setPlateNumber(plateNumber);
		proDriver.setPwd(MD5Util.md5(pwd));
		proDriverDao.save(proDriver);
	}

	public void update(String mobilePhone, String name, String pwd, String plateNumber, Integer id) {
		ProDriver proDriver = proDriverDao.find(id);
		proDriver.setCreateTime(new Date());
		proDriver.setMobilePhone(mobilePhone);
		proDriver.setName(name);
		proDriver.setPlateNumber(plateNumber);
		if(StringUtil.isNotBlank(pwd)){
			proDriver.setPwd(MD5Util.md5(pwd));
		}
		proDriverDao.update(proDriver);
	}

	public List<ProDriver> selectList() {
		return proDriverDao.selectList();
	}
}
