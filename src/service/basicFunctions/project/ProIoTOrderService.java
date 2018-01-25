package service.basicFunctions.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProIoTOrderDao;
import database.common.PageDataList;
import database.models.project.ProIoTOrder;

@Service
public class ProIoTOrderService {

	@Autowired
	private ProIoTOrderDao proIoTOrderDao;
	

	public ProIoTOrder save(ProIoTOrder proIoTOrder){
		return proIoTOrderDao.save(proIoTOrder);
	}
	
	public void findByMacAndStartUuid(String mac,String uuid){
		
	}

	public ProIoTOrder findByMacNear(String mac) {
		return proIoTOrderDao.findByMacNear(mac);
	}

	public void update(ProIoTOrder proIoTOrder) {
		proIoTOrderDao.update(proIoTOrder);
	}
	
	public PageDataList<ProIoTOrder> findUse(Integer p){
		return proIoTOrderDao.findUse(p);
	}

	public List<ProIoTOrder> findByStatus(String string) {
		return proIoTOrderDao.findByStatus("1");
	}

	
}
