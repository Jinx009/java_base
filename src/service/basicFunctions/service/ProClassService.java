package service.basicFunctions.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.helper.StringUtil;
import database.basicFunctions.dao.project.ProClassDao;
import database.basicFunctions.dao.project.ProClassOrderDao;
import database.common.PageDataList;
import database.models.project.ProClass;
import database.models.project.ProClassOrder;

@Service
public class ProClassService {

	@Autowired
	private ProClassDao proClassDao;
	@Autowired
	private ProClassOrderDao proClassOrderDao;
	
	public PageDataList<ProClass> homeList(String classDate,Integer p){
		return proClassDao.homeList(p, classDate);
	}
	
	public List<ProClass> frontList(String classDate){
		return proClassDao.frontList(classDate);
	}
	
	public void save(String classDate,String name,String time,String desc) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ProClass proClass = new ProClass();
		proClass.setClassDate(classDate);
		proClass.setCreateTime(new Date());
		proClass.setDesc(desc);
		proClass.setName(name);
		proClass.setTime(time);
		proClass.setTimes(simpleDateFormat.parse(classDate).getTime());
		proClassDao.save(proClass);
	}
	
	public void delete(Integer id){
		proClassDao.delete(id);
	}

	public List<ProClass> getStatus(String userId) {
		Date date = new Date();
		List<ProClass> list = proClassDao.findByDate(date.getTime());
		if(StringUtil.isBlank(userId)){
			for(ProClass proClass:list){
				proClass.setDesc("1");
			}
		}else{
			for(ProClass proClass:list){
				ProClassOrder proClassOrder =  proClassOrderDao.findByUserIdAndClassId(userId,proClass.getId());
				if(proClassOrder!=null){
					proClass.setDesc("0");
				}else{
					proClass.setDesc("1");
				}
			}
		}
		return list;
	}
	
}
