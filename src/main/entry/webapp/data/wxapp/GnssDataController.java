package main.entry.webapp.data.wxapp;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/gnss")
public class GnssDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(GnssDataController.class);
	

	@RequestMapping(path = "/device")
	@ResponseBody
	public Resp<?> device() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(utils.HttpUtils.get("http://139.196.205.157:8086/d/openDeviceData"));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/log")
	@ResponseBody
	public Resp<?> log(String mac) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(utils.HttpUtils.get("http://139.196.205.157:8080/d/openLogData?mac="+mac));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
}
