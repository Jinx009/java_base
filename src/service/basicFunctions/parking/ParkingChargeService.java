package service.basicFunctions.parking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingAreaDao;
import database.basicFunctions.dao.parking.ParkingChargeDao;
import database.basicFunctions.dao.parking.ParkingStreetDao;
import database.models.parking.ParkingArea;
import database.models.parking.ParkingCharge;
import database.models.parking.ParkingStreet;

@Service
public class ParkingChargeService {

	@Autowired
	private ParkingChargeDao parkingChargeDao;
	@Autowired
	private ParkingAreaDao parkingAreaDao;
	@Autowired
	private ParkingStreetDao parkingStreetDao;
	
	public List<ParkingCharge> findAll(){
		return parkingChargeDao.findAll();
	}
	
	public ParkingCharge save(ParkingCharge parkingCharge, Integer areaId, Integer streetId, String _singleDate){
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Date date = sdf.parse(_singleDate);
			parkingCharge.setSingleDate(date);
			parkingCharge.setShowStatus(1);
			parkingCharge.setCreateTime(new Date());
			parkingCharge.setAreaId(areaId);
			parkingCharge.setStreetId(streetId);
			ParkingArea parkingArea = parkingAreaDao.find(areaId);
			parkingCharge.setAreaName(parkingArea.getName());
			if(0!=streetId){
				ParkingStreet parkingStreet = parkingStreetDao.find(streetId);
				parkingCharge.setStreetName(parkingStreet.getName());
			}else{
				parkingCharge.setStreetName("全部街道");
			}
			return parkingChargeDao.save(parkingCharge);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void del(Integer id) {
		ParkingCharge parkingCharge = parkingChargeDao.find(id);
		parkingCharge.setShowStatus(0);
		parkingChargeDao.update(parkingCharge);
	}
	
}
