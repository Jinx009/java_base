package main.entry.webapp.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.model.GnssMongoDeviceModel;
import database.model.GnssRtkDevice;
import main.entry.webapp.BaseController;
import main.entry.webapp.mongo.MongoUtil;
import service.GnssRtkDeviceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtkdevice")
public class GnssRtkDeviceDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(GnssRtkDeviceDataController.class);
	
	@Autowired
	private GnssRtkDeviceService gnssRtkDeviceService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>();
		try {
			List<GnssRtkDevice> list = gnssRtkDeviceService.findAll();
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/find")
	@ResponseBody
	public Resp<?> find(String rovertag){
		Resp<?> resp = new Resp<>();
		try {
			GnssRtkDevice device = gnssRtkDeviceService.findByRoverTag(rovertag);
			return new Resp<>(device);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/setTag")
	@ResponseBody
	public Resp<?> find(String mac,String basetag,Integer subStatus,String t){
		Resp<?> resp = new Resp<>();
		try {
			GnssMongoDeviceModel model = MongoUtil.selectTag(mac);
			Integer tagType = 0;
			if(basetag.equals(mac)) {
				tagType = 1;
				basetag = "";
			}
			String topic = "/device/"+mac+"/"+t;
			if(model==null) {
				MongoUtil.save(basetag, mac, subStatus, topic, tagType);
			}else {
				MongoUtil.updateTag(mac, subStatus, tagType);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
