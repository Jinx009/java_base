package service.basicFunctions.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProClassDao;
import database.common.PageDataList;
import database.models.project.ProClass;

@Service
public class ProClassService {

	@Autowired
	private ProClassDao proClassDao;
	
	public PageDataList<ProClass> homeList(String classDate,Integer p){
		return proClassDao.homeList(p, classDate);
	}
	
	public List<ProClass> frontList(String classDate){
		return proClassDao.frontList(classDate);
	}
	
	public void save(String classDate,String name,String time,String desc){
		ProClass proClass = new ProClass();
		proClass.setClassDate(classDate);
		proClass.setCreateTime(new Date());
		proClass.setDesc(desc);
		proClass.setName(name);
		proClass.setTime(time);
		proClassDao.save(proClass);
	}
	
	public void delete(Integer id){
		proClassDao.delete(id);
	}
	
}
