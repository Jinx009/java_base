package service.basicFunctions;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.VedioDao;
import database.common.PageDataList;
import database.models.Vedio;

@Service
public class VedioService {

	@Autowired
	private VedioDao vedioDao;
	
    public Vedio save(Integer streetId,String streetName,Integer cameraId,String cameraNumber,String parkNumber,String startTime,String endTime,String vedioName){
    	Vedio vedio = new Vedio();
    	vedio.setStartTime(startTime);
    	vedio.setStreetName(streetName);
    	vedio.setCameraId(cameraId);
    	vedio.setCameraNumber(cameraNumber);
    	vedio.setParkNumber(parkNumber);
    	vedio.setEndTime(endTime);
    	vedio.setVedioName(vedioName);
    	vedio.setVedioStatus(0);
    	vedio.setCreateTime(new Date());
    	return vedioDao.save(vedio);
    }
    
    public void update(Integer id){
    	Vedio vedio = vedioDao.find(id);
    	vedio.setVedioStatus(1);
    	vedioDao.update(vedio);
    }
    
    public void delete(Integer id){
    	vedioDao.delete(id);
    }
    
    public PageDataList<Vedio> findByPage(int p ,Integer streetId,String parkNumber){
    	return vedioDao.findByPage(p,streetId,parkNumber);
    }
	
}
