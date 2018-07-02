package service.basicFunctions.face;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.face.FaceGatewayCompareDao;
import database.common.PageDataList;
import database.models.face.FaceGatewayCompare;

@Service
public class FaceGatewayCompareService {

	@Autowired
	private FaceGatewayCompareDao faceGatewayCompareDao;
	
	public void save(FaceGatewayCompare faceGatewayCompare){
		faceGatewayCompareDao.save(faceGatewayCompare);
	}
	
	public PageDataList<FaceGatewayCompare> pageList(int p){
		return faceGatewayCompareDao.pageList(p);
	}
	
}
