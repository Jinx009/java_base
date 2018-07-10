package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewaySmokeDataDao;
import database.common.PageDataList;
import database.models.project.ProGatewaySmokeData;

@Service
public class ProGatewaySmokeDataService {

	@Autowired
	private ProGatewaySmokeDataDao proGatewaySmokeDataDao;
	
	public ProGatewaySmokeData save(String data){
		ProGatewaySmokeData proGatewaySmokeData = new ProGatewaySmokeData();
		proGatewaySmokeData.setData(data);
		proGatewaySmokeData.setCreateTime(new Date());
		return proGatewaySmokeDataDao.save(proGatewaySmokeData);
	}

	public PageDataList<ProGatewaySmokeData> pageList(Integer p) {
		return proGatewaySmokeDataDao.PageList(p);
	}
	
}
