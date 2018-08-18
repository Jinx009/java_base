package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProTaskTitleDao;
import database.models.project.ProTaskTitle;

@Service
public class ProTaskTitleService {

	@Autowired
	private ProTaskTitleDao proTaskTitleDao;
	
	public List<ProTaskTitle> list(){
		return proTaskTitleDao.findAll();
	}
	
}
