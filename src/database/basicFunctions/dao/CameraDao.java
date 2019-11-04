package database.basicFunctions.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.models.Camera;

@Repository
public class CameraDao extends BaseDao<Camera>{

	public void updateCamera(Integer id,Date date){
		Camera camera = find(id);
		camera.setVedioTime(date);
		update(camera);
	}

}
