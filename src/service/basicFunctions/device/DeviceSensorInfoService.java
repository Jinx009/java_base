package service.basicFunctions.device;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.device.DeviceSensorInfoDao;
import database.models.device.DeviceSesnorInfo;
import utils.StringUtil;

@Service
public class DeviceSensorInfoService {

	@Autowired
	private DeviceSensorInfoDao deviceSensorInfoDao;
	
	public List<DeviceSesnorInfo> find(){
		List<DeviceSesnorInfo> list = new ArrayList<DeviceSesnorInfo>();
		List<DeviceSesnorInfo> list2 = deviceSensorInfoDao.findAll();
		for(DeviceSesnorInfo sesnorInfo:list2) {
			if(StringUtil.isNotBlank(sesnorInfo.getMac())) {
				list.add(sesnorInfo);
			}
		}
		return list;
	}
	
}
