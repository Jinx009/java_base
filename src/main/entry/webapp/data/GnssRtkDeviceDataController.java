package main.entry.webapp.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.model.GnssRtkDevice;
import main.entry.webapp.BaseController;
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
	
}
