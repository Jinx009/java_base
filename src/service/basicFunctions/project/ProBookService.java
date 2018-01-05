package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

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

	public void saveNew(String name, String desc, String picPath, Integer points, String mobilePhone) {
		ProBook proBook = new ProBook();
		proBook.setCreateTime(new Date());
		proBook.setDesc(desc);
		proBook.setMobilePhone(mobilePhone);
		proBook.setName(name);
		proBook.setPicPath(picPath);
		proBook.setPoints(points);
		proBook.setStatus(0);
		proBookDao.save(proBook);
	}

	public List<ProBook> list() {
		return proBookDao.frontList();
	}
	
}
