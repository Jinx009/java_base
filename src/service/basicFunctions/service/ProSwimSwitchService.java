package service.basicFunctions.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProSwimSwitchDao;
import database.common.PageDataList;
import database.models.project.ProSwimSwitch;

@Service
public class ProSwimSwitchService {

	@Autowired
	private ProSwimSwitchDao proSwimSwitchDao;
	
	public PageDataList<ProSwimSwitch> findByPage(int p){
		return proSwimSwitchDao.pageList(p);
	}
	
	public ProSwimSwitch findByDateStr(String dateStr){
		return proSwimSwitchDao.findByDateStr(dateStr);
	}
	
	public void del(Integer id){
		proSwimSwitchDao.delete(id);
	}
	
	public void save(String dateStr){
		ProSwimSwitch proSwimSwitch = new ProSwimSwitch();
		proSwimSwitch.setCreateTime(new Date());
		proSwimSwitch.setDateStr(dateStr);
		proSwimSwitchDao.save(proSwimSwitch);
	}
	
}
