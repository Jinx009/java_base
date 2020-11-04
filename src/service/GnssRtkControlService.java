package service;

import java.util.Date;
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

	public void updateGrc(String payload, String mac) {
		String id = payload.substring(4,12);
		long dec_num = Long.parseLong(id, 16);
		GnssRtkControl grc = gnssRtkControlDao.find((int)dec_num);
		if(grc!=null) {
			grc.setResultStr(payload);
			grc.setStatus(1);
			gnssRtkControlDao.update(grc);
		}
	}

	public GnssRtkControl saveCmd(String cmd, String payload, int status) {
		GnssRtkControl grc = new GnssRtkControl();
		grc.setCmd(cmd);
		grc.setCreateTime(new Date());
		grc.setMac(payload);
		grc.setResultStr("");
		grc.setStatus(status);
		return gnssRtkControlDao.save(grc);
	}
}
