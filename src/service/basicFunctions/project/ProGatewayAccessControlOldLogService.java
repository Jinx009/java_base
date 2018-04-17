package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayAccessControlLogDao;
import database.basicFunctions.dao.project.ProGatewayAccessControlOldLogDao;
import database.basicFunctions.dao.project.ProGatewayAccessControlPersonDao;
import database.models.project.ProGatewayAccessControlLog;
import database.models.project.ProGatewayAccessControlOldLog;
import database.models.project.ProGatewayAccessControlPerson;

@Service
public class ProGatewayAccessControlOldLogService {

	@Autowired
	private ProGatewayAccessControlOldLogDao proGatewayAccessControlOldLogDao;
	@Autowired
	private ProGatewayAccessControlLogDao proGatewayAccessControlLogDao;
	@Autowired
	private ProGatewayAccessControlPersonDao proGatewayAccessControlPersonDao;
	
	public ProGatewayAccessControlLog random(){
		ProGatewayAccessControlOldLog proAccessControlOldLog = proGatewayAccessControlOldLogDao.random();
		ProGatewayAccessControlPerson proGatewayAccessControlPerson = proGatewayAccessControlPersonDao.findNear();
		ProGatewayAccessControlLog proAccessControlLog = new ProGatewayAccessControlLog();
		proAccessControlLog.setAccessResult(proAccessControlOldLog.getAccessResult());
		proAccessControlLog.setAccessWay(proAccessControlOldLog.getAccessWay());
		proAccessControlLog.setCardSerialNumber(proAccessControlOldLog.getCardSerialNumber());
		proAccessControlLog.setCertificateCardNo(proAccessControlOldLog.getCertificateCardNo());
		proAccessControlLog.setDeviceID(proAccessControlOldLog.getDeviceID());
		proAccessControlLog.setDeviceLocalDirectory(proAccessControlOldLog.getDeviceLocalDirectory());
		proAccessControlLog.setDeviceName(proGatewayAccessControlPerson.getAreaName());
		proAccessControlLog.setDirection(proAccessControlOldLog.getDirection());
		proAccessControlLog.setMobile(proAccessControlOldLog.getMobile());
		proAccessControlLog.setOpenDoorMsgID(proAccessControlOldLog.getOpenDoorMsgID());
		proAccessControlLog.setOpenDoorPhotoList(proAccessControlOldLog.getOpenDoorPhotoList());
		proAccessControlLog.setOpenDoorTime(proAccessControlOldLog.getOpenDoorTime());
		proAccessControlLog.setOpenDoorVideoList(proAccessControlOldLog.getOpenDoorVideoList());
		proAccessControlLog.setPersonnelID(proGatewayAccessControlPerson.getId());
		proAccessControlLog.setPersonnelName(proGatewayAccessControlPerson.getName());
		proAccessControlLog.setPhotoHost(proAccessControlOldLog.getPhotoHost());
		proAccessControlLog.setSign(proAccessControlOldLog.getSign());
		proAccessControlLog.setTenantCode(proAccessControlOldLog.getTenantCode());
		proAccessControlLog.setTimestamp(proAccessControlOldLog.getTimestamp());
		proGatewayAccessControlLogDao.save(proAccessControlLog);
		proGatewayAccessControlPerson.setUpdateTime(new Date());
		proGatewayAccessControlPersonDao.update(proGatewayAccessControlPerson);
		return proAccessControlLog;
	}
	
}
