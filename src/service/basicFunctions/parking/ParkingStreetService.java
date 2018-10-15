package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingStreetDao;
import database.models.parking.ParkingStreet;

@Service
public class ParkingStreetService {

	@Autowired
	private ParkingStreetDao parkingStreetDao;
	
	public ParkingStreet find(Integer id){
		return parkingStreetDao.find(id);
	}
	
	public ParkingStreet save(ParkingStreet parkingStreet){
		parkingStreet.setCreateTime(new Date());
		parkingStreet.setShowStatus(1);
		parkingStreet.setStatus(1);
		return parkingStreetDao.save(parkingStreet);
	}
	
	public List<ParkingStreet> findAll(){
		return parkingStreetDao.findUseAll();
	}
	
	public void update(ParkingStreet parkingStreet){
		parkingStreetDao.update(parkingStreet);
	}
	
	public void delete(Integer id){
		ParkingStreet parkingStreet = parkingStreetDao.find(id);
		parkingStreet.setStatus(0);
		parkingStreetDao.update(parkingStreet);
	}

	public List<ParkingStreet> findByAreaId(Integer areaId) {
		return parkingStreetDao.findByAreaId(areaId);
	}

	public void save(Integer areaId,String name, String desc) {
		ParkingStreet parkingStreet = new ParkingStreet();
		parkingStreet.setAreaId(areaId);
		parkingStreet.setCreateTime(new Date());
		parkingStreet.setDesc(desc);
		parkingStreet.setName(name);
		parkingStreet.setStatus(1);
		parkingStreet.setShowStatus(1);
		
		parkingStreetDao.save(parkingStreet);
		
	}
	
}
