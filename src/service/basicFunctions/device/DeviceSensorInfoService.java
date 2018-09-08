package service.basicFunctions.device;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.device.DeviceSensorInfoDao;
import database.models.device.DeviceSensorInfo;
import utils.StringUtil;

@Service
public class DeviceSensorInfoService {

	@Autowired
	private DeviceSensorInfoDao deviceSensorInfoDao;
	
	public List<DeviceSensorInfo> find(){
		List<DeviceSensorInfo> list = new ArrayList<DeviceSensorInfo>();
		List<DeviceSensorInfo> list2 = deviceSensorInfoDao.findAll();
		for(DeviceSensorInfo sesnorInfo:list2) {
			if(StringUtil.isNotBlank(sesnorInfo.getMac())) {
				list.add(sesnorInfo);
			}
		}
		return list;
	}

	public List<DeviceSensorInfo> findByMacAndAddress(String mac, String address) {
		return deviceSensorInfoDao.findByMacAndAddress(mac,address);
	}
	
}
