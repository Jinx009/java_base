package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingAreaDao;
import database.models.parking.ParkingArea;

@Service
public class ParkingAreaService {

	@Autowired
	private ParkingAreaDao parkingAreaDao;
	
	public ParkingArea find(Integer id){
		return parkingAreaDao.find(id);
	}
	
	public List<ParkingArea> findAll(){
		return parkingAreaDao.findUseAll();
	}
	
	public ParkingArea save(ParkingArea parkingArea){
		parkingArea.setCreateTime(new Date());
		return parkingAreaDao.save(parkingArea);
	}
	
	public void update(ParkingArea parkingArea){
		parkingAreaDao.update(parkingArea);
	}
	
	public void delete(Integer id){
		ParkingArea parkingArea = parkingAreaDao.find(id);
		parkingArea.setStatus(0);
		parkingAreaDao.update(parkingArea);
	}

	public void save(String name, String desc) {
		ParkingArea parkingArea = new ParkingArea();
		parkingArea.setCreateTime(new Date());
		parkingArea.setDesc(desc);
		parkingArea.setName(name);
		parkingArea.setStatus(1);
		
		parkingAreaDao.save(parkingArea);
	}

	public void update(Integer id, String name, String desc) {
		ParkingArea parkingArea = parkingAreaDao.find(id);
		parkingArea.setDesc(desc);
		parkingArea.setName(name);
		
		parkingAreaDao.update(parkingArea);
		
	}
	
}
