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
	
	public  VedioArea save(String name,String cameraIndex,String picPath,String x1,String x2,String x3,String x4,String y1,String y2,String y3,String y4){
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
		return vedioAreaDao.saveV(vedioArea);
	}

	public void update(VedioArea vedioArea) {
		vedioAreaDao.update(vedioArea);
	}

	public void update(Integer id, String x1, String x2, String x3, String x4, String y1, String y2, String y3,
			String y4) {
		VedioArea vedioArea = vedioAreaDao.find(id);
		vedioArea.setX1(x1);
		vedioArea.setX2(x2);
		vedioArea.setX3(x3);
		vedioArea.setX4(x4);
		vedioArea.setY1(y1);
		vedioArea.setY2(y2);
		vedioArea.setY3(y3);
		vedioArea.setY4(y4);
		vedioAreaDao.update(vedioArea);
	}

	public void del(Integer id) {
		vedioAreaDao.delete(id);
	}

	public VedioArea find(Integer id) {
		return vedioAreaDao.find(id);
	}
	
}
