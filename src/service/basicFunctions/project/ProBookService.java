package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProBookDao;
import database.common.PageDataList;
import database.models.project.ProBook;

@Service
public class ProBookService {

	@Autowired
	private ProBookDao proBookDao;
	
	public PageDataList<ProBook> homeList(Integer p){
		return proBookDao.homeList(p);
	}
	
}
