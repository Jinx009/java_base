package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssRtkControlDao;
import database.model.GnssRtkControl;

@Service
public class GnssRtkControlService {

	@Autowired
	private GnssRtkControlDao gnssRtkControlDao;

	public List<GnssRtkControl> findByMacAndStatus(String mac, int status) {
		return gnssRtkControlDao.findByMacAndStatus(mac,status);
	}

	public GnssRtkControl save(GnssRtkControl grc) {
		return gnssRtkControlDao.save(grc);
	}
	
	public void update(GnssRtkControl grc) {
		gnssRtkControlDao.update(grc);
	}

	public GnssRtkControl find(int id) {
		return gnssRtkControlDao.find(id);
	}

	public Object findByMac(String mac) {
		return gnssRtkControlDao.findByMac(mac);
	}
}
