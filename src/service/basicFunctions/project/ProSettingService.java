package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProSettingDao;
import database.models.project.ProSetting;

@Service
public class ProSettingService {

	@Autowired
	private ProSettingDao proSettingDao;
	
	public ProSetting findByName(String name) {
		return proSettingDao.getByName(name);
	}

	public boolean change(String name, Integer status) {
		ProSetting proSetting = proSettingDao.getByName(name);
		if(proSetting!=null) {
			proSetting.setStatus(status);
			proSettingDao.update(proSetting);
			return true;
		}
		return false;
	}
	
}
