package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewaySmokeDataDao;
import database.models.project.ProGatewaySmokeData;

@Service
public class ProGatewaySmokeDataService {

	@Autowired
	private ProGatewaySmokeDataDao proGatewaySmokeDataDao;
	
	public ProGatewaySmokeData save(String data){
		ProGatewaySmokeData proGatewaySmokeData = new ProGatewaySmokeData();
		String[] s = data.split("-");
		proGatewaySmokeData.setData(data);
		proGatewaySmokeData.setMac(s[1]);
		proGatewaySmokeData.setStatus(Integer.valueOf(s[2]));
		return proGatewaySmokeDataDao.save(proGatewaySmokeData);
	}
	
}
