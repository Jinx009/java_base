package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProPicDao;
import database.common.PageDataList;
import database.models.project.ProPic;

@Service
public class ProPicService {

	@Autowired
	private ProPicDao proPicDao;
	
	public List<ProPic> frontList(){
		return proPicDao.frontList();
	}

	public PageDataList<ProPic> homeList(Integer p) {
		return proPicDao.homeList(p);
	}
	
	public void saveNew(String name,String desc,String picPath,String autor,String mobilePhone){
		ProPic proPic = new ProPic();
		proPic.setAutor(autor);
		proPic.setCreateTime(new Date());
		proPic.setDesc(desc);
		proPic.setMobilePhone(mobilePhone);
		proPic.setName(name);
		proPic.setPicPath(picPath);
		proPic.setStar(0);
		proPicDao.save(proPic);
	}

	public List<ProPic> list() {
		return proPicDao.frontList();
	}
	
}
