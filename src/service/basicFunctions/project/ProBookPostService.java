package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProBookPostDao;
import database.models.project.ProBookPost;

@Service
public class ProBookPostService {

	@Autowired
	private ProBookPostDao proBookPostDao;
	
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
}
