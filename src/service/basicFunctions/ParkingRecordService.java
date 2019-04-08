package service.basicFunctions;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.ParkingRecordDao;
import database.common.PageDataList;
import database.models.ParkingRecord;

@Service
public class ParkingRecordService {

	@Autowired
	private ParkingRecordDao parkingRecordDao;
	
	public void save(Integer streetId,Integer status,String streetName,Integer cameraId,String cameraNumber,Date happenTime,String picUrl,String vedioUrl,String parkNumber){
		ParkingRecord parkingRecord = new ParkingRecord();
		parkingRecord.setStreetId(streetId);
		parkingRecord.setStatus(status);
		parkingRecord.setStreetName(streetName);
		parkingRecord.setCameraId(cameraId);
		parkingRecord.setCameraNumber(cameraNumber);
		parkingRecord.setHappenTime(happenTime);
		parkingRecord.setPicUrl(picUrl);
		parkingRecord.setVedioUrl(vedioUrl);
		parkingRecord.setSendStatus(0);
		parkingRecord.setParkNumber(parkNumber);
		parkingRecordDao.save(parkingRecord);
	}
	
	public void updateStatus(Integer id,Integer status){
		ParkingRecord parkingRecord = parkingRecordDao.find(id);
		parkingRecord.setSendStatus(status);
		parkingRecordDao.update(parkingRecord);
	}
	
	public void delete(Integer id){
		parkingRecordDao.delete(id);
	}
	
	public PageDataList<ParkingRecord> findByPage(int p,String parkNumber){
		return parkingRecordDao.findByPage(p,parkNumber);
	}
	
}
