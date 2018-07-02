package service.basicFunctions.face;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.face.FaceGatewayUserDao;
import database.common.PageDataList;
import database.models.face.FaceGatewayUser;

@Service
public class FaceGatewayUserService {

	@Autowired
	private FaceGatewayUserDao faceGatewayUserDao;
	
	
	public void save(FaceGatewayUser faceGatewayUser){
		faceGatewayUserDao.save(faceGatewayUser);
	}
	
	public PageDataList<FaceGatewayUser> pageList(int p){
		return faceGatewayUserDao.pageList(p);
	}
	
	public FaceGatewayUser findByMobilePhone(String mobilePhone){
		return faceGatewayUserDao.getByMobilePhone(mobilePhone);
	}
	
}
