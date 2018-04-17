package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayAccessControlLogDao;
import database.models.project.ProGatewayAccessControlLog;

@Service
public class ProGatewayAccessControlLogService {

	@Autowired
	private ProGatewayAccessControlLogDao proGatewayAccessControlLogDao;
	
	public void save(ProGatewayAccessControlLog proAccessControlLog){
		proGatewayAccessControlLogDao.save(proAccessControlLog);
	}
	
}
