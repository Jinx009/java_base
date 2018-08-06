package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProUserDao;
import database.common.PageDataList;
import database.models.project.ProUser;

@Service
public class ProUserService {

	@Autowired
	private ProUserDao proUserDao;
	
	public ProUser save(ProUser proUser){
		return proUserDao.save(proUser);
	}
	
	public ProUser findByCard(String card){
		return proUserDao.findByCard(card);
	}

	public Object findAll() {
		return proUserDao.findAllUse();
	}
	
	public void delete(Integer id){
		ProUser proUser = proUserDao.find(id);
		proUser.setStatus(0);
		proUserDao.update(proUser);
	}

	public void saveNew(String name, String card, String mobilePhone,String carNum) {
		ProUser proUser = new ProUser();
		proUser.setCard(card);
		proUser.setCarNum(carNum);
		proUser.setMobilePhone(mobilePhone);
		proUser.setName(name);
		proUser.setStatus(1);
		proUser.setCreateTime(new Date());
		proUserDao.save(proUser);
	}

	public PageDataList<ProUser> pageList(Integer p) {
		return proUserDao.pageList(p);
	}
	
	
}
