package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProBookPostDao;
import database.basicFunctions.dao.project.ProUserDao;
import database.common.PageDataList;
import database.models.project.ProBookPost;
import database.models.project.ProUser;

@Service
public class ProBookPostService {

	@Autowired
	private ProBookPostDao proBookPostDao;
	@Autowired
	private ProUserDao proUserDao;
	
	public void saveNew(String postNum,String remark,String mobilePhone){
		ProBookPost proBookPost = new ProBookPost();
		proBookPost.setCreateTime(new Date());
		proBookPost.setMobilePhone(mobilePhone);
		proBookPost.setRemark(remark);
		proBookPost.setPoints(0);
		proBookPost.setPointsRemark("");
		proBookPost.setPostNum(postNum);
		proBookPost.setStatus(0);
		proBookPostDao.save(proBookPost);
	}
	
	public List<ProBookPost> personList(String mobilePhone){
		return proBookPostDao.personList(mobilePhone);
	}
	
	public void update(Integer id,Integer points,String pointsRemark,Integer sellPoints){
		ProBookPost proBookPost = proBookPostDao.find(id);
		proBookPost.setStatus(1);
		proBookPost.setPoints(points);
		proBookPost.setPointsRemark(pointsRemark);
		proBookPost.setSellPoints(sellPoints);
		proBookPostDao.update(proBookPost);
		ProUser proUser = proUserDao.findByMobile(proBookPost.getMobilePhone());
		proUser.setCurrentPoints(proUser.getCurrentPoints()+points);
		proUserDao.update(proUser);
	}

	public PageDataList<ProBookPost> homeList(Integer p) {
		return proBookPostDao.homeList(p);
	}
	
}
