package service.basicFunctions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.IotCloudDeviceDao;
import database.models.IoTCloudDevice;

@Service
public class IotCloudDeviceService {

	@Autowired
	private IotCloudDeviceDao iotCloudDeviceDao;
	
	public void save(IoTCloudDevice ioTCloudDevice){
		iotCloudDeviceDao.save(ioTCloudDevice);
	}
	
	public String getByImei(String imei){
		IoTCloudDevice ioTCloudDevice = iotCloudDeviceDao.findByImei(imei);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice.getMac();
		}
		return "";
	}

	public IoTCloudDevice findByMac(String mac) {
		IoTCloudDevice ioTCloudDevice = iotCloudDeviceDao.findByMac(mac);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice;
		}
		return null;
	}

	public IoTCloudDevice findByDeviceId(String deviceId) {
		IoTCloudDevice ioTCloudDevice = iotCloudDeviceDao.findByDeviceId(deviceId);
		if(ioTCloudDevice!=null){
			return ioTCloudDevice;
		}
		return null;
	}
	
	public PageDataList<IoTCloudDevice> pageList(Integer p, Integer type){
		return iotCloudDeviceDao.findAll(p,type);
	}

	public List<IoTCloudDevice> findByLocalIp(String localIp) {
		return  iotCloudDeviceDao.findByLocalIp(localIp);
	}

	public void update(IoTCloudDevice ioTCloudDevice) {
		iotCloudDeviceDao.update(ioTCloudDevice);
	}

	public List<IoTCloudDevice> getWuhan() {
		return iotCloudDeviceDao.getWuhan();
	}

	public List<IoTCloudDevice> getBroken() {
		List<IoTCloudDevice> list = iotCloudDeviceDao.findByLocalIp("QJ_ZHANWAY_V_3.0_WUHAN");
		List<IoTCloudDevice> list2 = iotCloudDeviceDao.findByLocalIp("QJ_ZHANWAY_V_3.0_GUANGDONG");
		List<IoTCloudDevice> list3 = iotCloudDeviceDao.findByLocalIp("QJ_ZHANWAY_V_3.0_YIBIN");
		List<IoTCloudDevice> list4 = iotCloudDeviceDao.findByLocalIp("QJ_ZHANWAY_V_3.1_WUHAN");
		List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
		for(IoTCloudDevice d : list){
			if(d.getDataNum()==0){
				l.add(d);
			}
		}
		for(IoTCloudDevice d : list2){
			if(d.getDataNum()==0){
				l.add(d);
			}
		}
		for(IoTCloudDevice d : list3){
			if(d.getDataNum()==0){
				l.add(d);
			}
		}
		for(IoTCloudDevice d : list4){
			if(d.getDataNum()==0){
				l.add(d);
			}
		}
		return l;
	}

	public List<IoTCloudDevice> findBySecret(String secret) {
		return iotCloudDeviceDao.findBySecret(secret);
	}

	public List<IoTCloudDevice> getMap() {
		return iotCloudDeviceDao.getMap();
	}

	public List<IoTCloudDevice> getByParkNameOrMac(String parkName) {
		return iotCloudDeviceDao.getByParkNameOrMac(parkName);
	}

	public IoTCloudDevice findBy(Integer id) {
		return iotCloudDeviceDao.find(id);
	}

	public List<IoTCloudDevice> findLost() {
		return iotCloudDeviceDao.getLost();
	}

	
}
