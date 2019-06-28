package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProVedioDao;
import database.models.project.ProVedio;

@Service
public class ProVedioService {
	
	@Autowired
	private ProVedioDao proVedioDao;
	

	public List<ProVedio> findByLevel(Integer level) {
		return proVedioDao.findByLevel(level);
	}

}
