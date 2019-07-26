package service.basicFunctions.vedio;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.vedio.VedioAreaDao;
import database.models.vedio.VedioArea;

@Service
public class VedioAreaService {

	@Autowired
	private VedioAreaDao vedioAreaDao;
	
	public List<VedioArea> findAll(){
		return vedioAreaDao.findAll();
	}
	
	public  VedioArea save(String name,String cameraIndex,String picPath,Double x1,Double x2,Double x3,Double x4,Double y1,Double y2,Double y3,Double y4){
		VedioArea vedioArea = new VedioArea();
		vedioArea.setName(name);
		vedioArea.setCameraIndex(cameraIndex);
		vedioArea.setX1(x1);
		vedioArea.setX2(x2);
		vedioArea.setX3(x3);
		vedioArea.setX4(x4);
		vedioArea.setY1(y1);
		vedioArea.setY2(y2);
		vedioArea.setY3(y3);
		vedioArea.setY4(y4);
		vedioArea.setCreateTime(new Date());
		return vedioAreaDao.save(vedioArea);
	}

	public void update(VedioArea vedioArea) {
		vedioAreaDao.update(vedioArea);
	}

	public void update(Integer id, Double x1, Double x2, Double x3, Double x4, Double y1, Double y2, Double y3,
			Double y4) {
		// TODO Auto-generated method stub
		
	}
	
}
