package main.entry.webapp.data.project;

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
import database.models.guodong.GuodongJob;
import database.models.guodong.GuodongSensor;
import main.entry.webapp.BaseController;
import net.sf.json.JSONObject;
import service.basicFunctions.guodong.GuodongJobService;
import service.basicFunctions.guodong.GuodongSensorService;
import utils.BaseConstant;
import utils.Resp;
import utils.guodong.OpenApi;
import utils.guodong.WebSocketApi;

@Controller
@RequestMapping(value = "/interface/gd")
public class InterfaceGuodongDataController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(InterfaceGuodongDataController.class);

	@Autowired
	private GuodongSensorService guodongSensorService;
	@Autowired
	private GuodongJobService guodongJobService;

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
	
	@RequestMapping(path = "/jobs")
	@ResponseBody
	public Resp<?> jobs() {
		Resp<?> resp = new Resp<>(false);
		try {
			List<GuodongJob> list = guodongJobService.findAll();
			logger.warn("data:{}", list);
			resp = new Resp<>(list);
			return resp;
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/job")
	@ResponseBody
	public Resp<?> saveJob(String devEui,Integer status){
		Resp<?> resp = new Resp<>(false);
		try {
			GuodongJob guodongJob = guodongJobService.findByDevEui(devEui);
			if(guodongJob!=null&&0==status){
				resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,"该设备当前存在未完成任务！",null);
				return resp;
			}else if(guodongJob!=null&&2==status){
				guodongJob.setStatus(status);
				guodongJobService.update(guodongJob);
				resp = new Resp<>(true);
				return resp;
			}else{
				JSONObject jsonObject =  OpenApi.sendDataToNodes(devEui);
				if(0==jsonObject.getInt("error")){
					JSONObject jsonObject2 = (JSONObject) jsonObject.get("result");
					guodongJobService.save(devEui,jsonObject2.getString("task_id"));
					resp = new Resp<>(true);
					return resp;
				}else{
					resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,"任务创建失败！",null);
					return resp;
				}
			}
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
