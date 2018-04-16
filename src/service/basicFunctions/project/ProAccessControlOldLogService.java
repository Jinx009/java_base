package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProAccessControlLogDao;
import database.basicFunctions.dao.project.ProAccessControlOldLogDao;
import database.models.project.ProAccessControlLog;
import database.models.project.ProAccessControlOldLog;

@Service
public class ProAccessControlOldLogService {

	@Autowired
	private ProAccessControlOldLogDao proAccessControlOldLogDao;
	@Autowired
	private ProAccessControlLogDao proAccessControlLogDao;
	
	public ProAccessControlLog random(){
		ProAccessControlOldLog proAccessControlOldLog = proAccessControlOldLogDao.random();
		ProAccessControlLog proAccessControlLog = new ProAccessControlLog();
		proAccessControlLog.setAccessResult(proAccessControlOldLog.getAccessResult());
		proAccessControlLog.setAccessWay(proAccessControlOldLog.getAccessWay());
		proAccessControlLog.setCardSerialNumber(proAccessControlOldLog.getCardSerialNumber());
		proAccessControlLog.setCertificateCardNo(proAccessControlOldLog.getCertificateCardNo());
		proAccessControlLog.setDeviceID(proAccessControlOldLog.getDeviceID());
		proAccessControlLog.setDeviceLocalDirectory(proAccessControlOldLog.getDeviceLocalDirectory());
		proAccessControlLog.setDeviceName(proAccessControlOldLog.getDeviceName());
		proAccessControlLog.setDirection(proAccessControlOldLog.getDirection());
		proAccessControlLog.setMobile(proAccessControlOldLog.getMobile());
		proAccessControlLog.setOpenDoorMsgID(proAccessControlOldLog.getOpenDoorMsgID());
		proAccessControlLog.setOpenDoorPhotoList(proAccessControlOldLog.getOpenDoorPhotoList());
		proAccessControlLog.setOpenDoorTime(proAccessControlOldLog.getOpenDoorTime());
		proAccessControlLog.setOpenDoorVideoList(proAccessControlOldLog.getOpenDoorVideoList());
		proAccessControlLog.setPersonnelID(proAccessControlOldLog.getPersonnelID());
		proAccessControlLog.setPersonnelName(proAccessControlOldLog.getPersonnelName());
		proAccessControlLog.setPhotoHost(proAccessControlOldLog.getPhotoHost());
		proAccessControlLog.setSign(proAccessControlOldLog.getSign());
		proAccessControlLog.setTenantCode(proAccessControlOldLog.getTenantCode());
		proAccessControlLog.setTimestamp(proAccessControlOldLog.getTimestamp());
		proAccessControlLogDao.save(proAccessControlLog);
		return proAccessControlLog;
	}
	
}
