package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayBedDao;
import database.common.PageDataList;
import database.models.project.ProGatewayBed;

@Service
public class ProGatewayBedService {

	@Autowired
	private ProGatewayBedDao proGatewayBedDao;
	
	public ProGatewayBed save(ProGatewayBed proGatewayBed){
		return proGatewayBedDao.save(proGatewayBed);
	}
	
	public PageDataList<ProGatewayBed> findByPage(Integer p){
		return proGatewayBedDao.pageList(p);
	}
	
	
}
