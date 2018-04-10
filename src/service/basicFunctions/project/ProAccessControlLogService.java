package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProAccessControlLogDao;
import database.models.project.ProAccessControlLog;

@Service
public class ProAccessControlLogService {

	@Autowired
	private ProAccessControlLogDao proAccessControlLogDao;
	
	public void save(ProAccessControlLog proAccessControlLog){
		proAccessControlLogDao.save(proAccessControlLog);
	}
	
}
