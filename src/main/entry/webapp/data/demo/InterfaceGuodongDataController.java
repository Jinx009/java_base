package main.entry.webapp.data.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.java_websocket.drafts.Draft_17;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.MD5Util;
import database.models.guodong.GuodongSensor;
import service.basicFunctions.guodong.GuodongSensorService;
import utils.Resp;
import utils.guodong.WebSocketApi;

@Controller
@RequestMapping(value = "/interface/gd")
public class InterfaceGuodongDataController {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceGuodongDataController.class);

	@Autowired
	private GuodongSensorService guodongSensorService;

	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> getData() {
		Resp<?> resp = new Resp<>(false);
		try {
			List<GuodongSensor> list = guodongSensorService.findAll();
			logger.warn("data:{}", list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/socket")
	@ResponseBody
	public String startSocket() {
		try {
			logger.warn("start websocket ...");
			String appEUI = "000000000000006c";
			String userSec = "a7da4728e374343d37021e4be5593311";
			String currentTimeStr = String.valueOf(System.currentTimeMillis());
			String tokenStr = MD5Util.toMD5(currentTimeStr + appEUI + userSec);
			String url = "ws://www.guodongiot.com:92/webs?appEUI=" + appEUI + "&token=" + tokenStr + "&time_s="
					+ currentTimeStr;
			WebSocketApi c;
			try {
				c = new WebSocketApi(new URI(url), new Draft_17());
				c.connect();
				return "started";
			} catch (URISyntaxException e) {
				logger.error("error", e);
			}
			while (true) {
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return "failed";
	}

}
