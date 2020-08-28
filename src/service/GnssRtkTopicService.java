package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.GnssRtkTopicDao;
import database.model.GnssRtkTopic;

@Service
public class GnssRtkTopicService {

	@Autowired
	private GnssRtkTopicDao gnssRtkTopicDao;

	public void del(int id) {
		gnssRtkTopicDao.delete(id);
	}

	public void save(GnssRtkTopic t) {
		gnssRtkTopicDao.save(t);
	}

	public GnssRtkTopic findByMacAndTopic(String mac, String topic) {
		return gnssRtkTopicDao.findByMacAndTopic(mac,topic);
	}

	public GnssRtkTopic find(int id) {
		return gnssRtkTopicDao.find(id);
	}

	public List<GnssRtkTopic> list(String mac) {
		return gnssRtkTopicDao.list(mac);
	}
	
}
