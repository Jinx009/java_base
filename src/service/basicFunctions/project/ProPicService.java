package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProPicDao;
import database.models.project.ProPic;

@Service
public class ProPicService {

	@Autowired
	private ProPicDao proPicDao;
	
	public List<ProPic> frontList(){
		return proPicDao.frontList();
	}
	
}
