package service.basicFunctions.pro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.pro.ProQRCodeDao;
import database.models.pro.ProQRCode;

@Service
public class ProQRCodeService {

	@Autowired
	private ProQRCodeDao proQRCodeDao;
	
	public ProQRCode save(ProQRCode proQRCode) {
		return proQRCodeDao.save(proQRCode);
	}

	public ProQRCode findByKey(String key) {
		return proQRCodeDao.getByKey(key);
	}

	public List<ProQRCode> findAll() {
		return proQRCodeDao.findAllByTime();
	}
	
}
