package main.entry.webapp.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.common.PageDataList;
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
	
	@RequestMapping(path = "/page")
	@ResponseBody
	public Resp<?> page(int p){
		Resp<?> resp = new Resp<>();
		try {
			PageDataList<GnssRtkDevice> list = gnssRtkDeviceService.page(p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/changeTag")
	@ResponseBody
	public Resp<?> changeTag(String mac,Integer type,String dataType,String basetag,Integer switchType){
		Resp<?> resp = new Resp<>();
		try {
			GnssMongoDeviceModel model = MongoUtil.selectTag(mac);
			String  topic = "/device/"+mac+"/"+dataType;
			if(model!=null&&model.getTag()!=null) {
				MongoUtil.updateTag(mac, switchType, type,basetag,topic);
			}else {
				MongoUtil.save(basetag, mac, switchType, topic, type);
			}
			GnssRtkDevice device = gnssRtkDeviceService.findByMac(mac);
			if(device!=null) {
				device.setSwitchType(switchType);
				device.setDataType(dataType);
				device.setBasetag(basetag);
				gnssRtkDeviceService.update(device);
			}
			return new Resp<>(true);
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
	
	
	
}
