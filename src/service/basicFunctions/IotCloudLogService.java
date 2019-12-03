package service.basicFunctions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.IotCloudLogDao;
import database.models.IotCloudLog;

@Service
public class IotCloudLogService {

	@Autowired
	private IotCloudLogDao ioTCloudLogDao;
	
	public void save(IotCloudLog iotCloudLog){
		ioTCloudLogDao.save(iotCloudLog);
	}
	
	public PageDataList<IotCloudLog> pageList(Integer p, Integer type, String mac){
		return ioTCloudLogDao.findAll(p,type,mac);
	}

	public Object pageList(Integer p, Integer type, String mac, String fromSite) {
		return ioTCloudLogDao.findAll(p,type,mac,fromSite);
	}
	
	public List<IotCloudLog> findByMacLike(String mac) {
		return ioTCloudLogDao.findByMacLike(mac);
	}

	public PageDataList<IotCloudLog> cmdList(Integer p) {
		return ioTCloudLogDao.cmdList(p);
	}

	public IotCloudLog saveA(IotCloudLog iotCloudLog) {
		return ioTCloudLogDao.save(iotCloudLog);
	}

	public void update(IotCloudLog iotCloudLog) {
		ioTCloudLogDao.update(iotCloudLog);
	}

	public IotCloudLog findById(int dec_num) {
		return ioTCloudLogDao.find(dec_num);
	}

	public List<IotCloudLog> findByMacLikeD(String mac) {
		return ioTCloudLogDao.findByMacLikeD(mac);
	}
	
	
}
