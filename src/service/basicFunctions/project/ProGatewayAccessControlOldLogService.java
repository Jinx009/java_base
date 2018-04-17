package service.basicFunctions.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayAccessControlLogDao;
import database.basicFunctions.dao.project.ProGatewayAccessControlOldLogDao;
import database.models.project.ProGatewayAccessControlLog;
import database.models.project.ProGatewayAccessControlOldLog;

@Service
public class ProGatewayAccessControlOldLogService {

	@Autowired
	private ProGatewayAccessControlOldLogDao proGatewayAccessControlOldLogDao;
	@Autowired
	private ProGatewayAccessControlLogDao proGatewayAccessControlLogDao;
	
	public ProGatewayAccessControlLog random(){
		ProGatewayAccessControlOldLog proAccessControlOldLog = proGatewayAccessControlOldLogDao.random();
		ProGatewayAccessControlLog proAccessControlLog = new ProGatewayAccessControlLog();
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
		proGatewayAccessControlLogDao.save(proAccessControlLog);
		return proAccessControlLog;
	}
	
}
