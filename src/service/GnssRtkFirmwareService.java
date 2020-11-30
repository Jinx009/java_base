package service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkFirmwareDao;
import database.model.GnssRtkFirmware;

@Service
public class GnssRtkFirmwareService {

	@Autowired
	private GnssRtkFirmwareDao gnssRtkFirmwareDao;
	
	public void save(String url,String name,String versionNumber) {
		GnssRtkFirmware gnssRtkFirmware = new GnssRtkFirmware();
		gnssRtkFirmware.setCreateTime(new Date());
		gnssRtkFirmware.setName(name);
		gnssRtkFirmware.setUrl(url);
		gnssRtkFirmware.setVersionNumber(versionNumber);
		gnssRtkFirmwareDao.save(gnssRtkFirmware);
	}
	
	public PageDataList<GnssRtkFirmware> findByPage(int p){
		return gnssRtkFirmwareDao.findByPage(p);
	}
	
	
	public void del(int id) {
		gnssRtkFirmwareDao.delete(id);
	}
	
}
