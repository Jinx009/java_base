package service.basicFunctions.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProPhotoDao;
import database.common.PageDataList;
import database.models.project.ProPhoto;

@Service
public class ProPhotoService {

	@Autowired
	private ProPhotoDao proPhotoDao;
	
	public void save(String dateStr,Integer type){
		ProPhoto proPhoto = new ProPhoto();
		proPhoto.setCreateTime(new Date());
		proPhoto.setDateStr(dateStr);
		if(1==type){
			proPhoto.setName("上午");
		}else if(2==type){
			proPhoto.setName("下午");
		}else{
			proPhoto.setName("夜间");
		}
		proPhotoDao.save(proPhoto);
	}
	
	public void del(Integer id){
		proPhotoDao.delete(id);
	}

	public PageDataList<ProPhoto> pageList(Integer p) {
		return proPhotoDao.pageList(p);
	}

	public ProPhoto findByDateStr(String dateStr, int type) {
		return proPhotoDao.findByDateStr(dateStr,type);
	}
	
}
