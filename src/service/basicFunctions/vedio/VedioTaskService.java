package service.basicFunctions.vedio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.vedio.VedioAreaDao;
import database.basicFunctions.dao.vedio.VedioTaskDao;
import database.models.vedio.VedioArea;
import database.models.vedio.VedioTask;

@Service
public class VedioTaskService {

	@Autowired
	private VedioTaskDao vedioTaskDao;
	@Autowired
	private VedioAreaDao vedioAreaDao;
	
	public VedioTask save(String name,long time,Integer areaId,String vedioStart,String size){
		VedioArea vedioArea = vedioAreaDao.find(areaId);
		VedioTask vedioTask = new VedioTask();
		vedioTask.setAreaId(areaId);
		vedioTask.setAreaName(vedioArea.getName());
		vedioTask.setCreateTime(new Date());
		vedioTask.setResult("");
		vedioTask.setVedioStart(vedioStart);
		vedioTask.setStatus(0);
		vedioTask.setTime(time);
		vedioTask.setSize(size);
		return vedioTaskDao.save(vedioTask);
	}
	
	public List<VedioTask> findByStatus(Integer status){
		return vedioTaskDao.findByStatus(status);
	}

	public void update(VedioTask vedioTask) {
		vedioTaskDao.update(vedioTask);
	}
	
}
