package service.basicFunctions.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProSwitchDao;
import database.common.PageDataList;
import database.models.project.ProSwitch;

@Service
public class ProSwitchService {

	@Autowired
	private ProSwitchDao proSwitchDao;
	
	public void del(Integer id){
		proSwitchDao.delete(id);
	}
	
	public void save(String dateStr,Integer type, String reason){
		ProSwitch proSwitch = new ProSwitch();
		proSwitch.setCreateTime(new Date());
		proSwitch.setType(type);
		if(1==type){
			proSwitch.setName("上午");
		}else if(2==type){
			proSwitch.setName("下午");
		}else{
			proSwitch.setName("夜间");
		}
		proSwitch.setDateStr(dateStr);
		proSwitch.setReason(reason);
		proSwitchDao.save(proSwitch);
	}
	
	public PageDataList<ProSwitch> pageList(int p){
		return proSwitchDao.pageList(p);
	}
	
	public ProSwitch findByDateStr(String dateStr,Integer type){
		return proSwitchDao.findByDateStr(dateStr, type);
	}
	
}
