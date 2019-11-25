package service.basicFunctions.project;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProVedioDao;
import database.common.PageDataList;
import database.models.project.ProVedio;

@Service
public class ProVedioService {
	
	@Autowired
	private ProVedioDao proVedioDao;
	

	public  PageDataList<ProVedio>  findByLevel(Integer level,Integer p) {
		return proVedioDao.findByLevel(level,p);
	}

}
