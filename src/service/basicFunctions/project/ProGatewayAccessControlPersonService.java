package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayAccessControlAreaDao;
import database.basicFunctions.dao.project.ProGatewayAccessControlPersonDao;
import database.models.project.ProGatewayAccessControlArea;
import database.models.project.ProGatewayAccessControlPerson;

@Service
public class ProGatewayAccessControlPersonService {

	@Autowired
	private ProGatewayAccessControlPersonDao proGatewayAccessControlPersonDao;
	@Autowired
	private ProGatewayAccessControlAreaDao proGatewayAccessControlAreaDao;
	
	public List<ProGatewayAccessControlPerson> findAll(){
		return proGatewayAccessControlPersonDao.findAll();
	}
	
	public void init(){
		List<ProGatewayAccessControlPerson> list = findAll();
		for(ProGatewayAccessControlPerson proGatewayAccessControlPerson : list){
			ProGatewayAccessControlArea proGatewayAccessControlArea = proGatewayAccessControlAreaDao.random();
			proGatewayAccessControlPerson.setAreaId(proGatewayAccessControlArea.getId());
			proGatewayAccessControlPerson.setAreaName(proGatewayAccessControlArea.getName());
			proGatewayAccessControlPerson.setUpdateTime(new Date());
			proGatewayAccessControlPersonDao.update(proGatewayAccessControlPerson);
		}
	}
	
}
