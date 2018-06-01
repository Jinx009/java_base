package main.entry.webapp.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import common.helper.StringUtil;
import database.models.project.ProIoTOrder;
import database.models.project.ProUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProIoTOrderService;
import service.basicFunctions.project.ProUserService;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/openApi")
public class OpenApiDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(OpenApiDataController.class);

	private static final String NOTICE_URL = "http://120.92.101.137:8083/magnetic_striple_event";

	@Autowired
	private ProIoTOrderService proIoTOrderService;
	@Autowired
	private ProUserService proUserService;

	@RequestMapping(path = "/rfid/push")
	@ResponseBody
	public Resp<?> setCarNum(String mac, String card) {
		Resp<?> resp = new Resp<>(true);
		try {
			log.warn("mac:{},card:{}", mac, card);
			ProIoTOrder proIoTOrder = proIoTOrderService.findByMacNear(mac);
			if (proIoTOrder != null && proIoTOrder.getEndTime() == null) {
				ProUser proUser = proUserService.findByCard(card);
				if (proUser != null) {
					proIoTOrder.setCarNum(proUser.getCarNum());
					proIoTOrderService.update(proIoTOrder);
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/status/push")
	@ResponseBody
	public Resp<?> getOrder(String mac, String logId, String changeTime, Integer status, String desc) {
		Resp<?> resp = new Resp<>(true);
		try {
			log.warn("mac:{},logId:{},status:{},changeTime:{},desc:{}", mac, logId, status, changeTime, desc);
			Long time = Long.valueOf(changeTime);
			String uuid = StringUtil.add(mac, "_", logId);
			if (1 == status) {
				ProIoTOrder proIoTOrder = new ProIoTOrder();
				proIoTOrder.setCreateTime(new Date());
				proIoTOrder.setDescription(desc);
				proIoTOrder.setLogId(logId);
				proIoTOrder.setMac(mac);
				proIoTOrder.setStartTime(new Date(time));
				proIoTOrder.setStartUuid(uuid);
				proIoTOrder.setStatus("0");
				proIoTOrderService.save(proIoTOrder);
			} else if (0 == status) {
				ProIoTOrder proIoTOrder = proIoTOrderService.findByMacNear(mac);
				if (proIoTOrder != null && proIoTOrder.getEndTime() == null) {
					proIoTOrder.setEndUuid(uuid);
					proIoTOrder.setEndTime(new Date(time));
					proIoTOrder.setStatus("1");
					proIoTOrderService.update(proIoTOrder);
				} else {
					proIoTOrder = new ProIoTOrder();
					proIoTOrder.setCreateTime(new Date());
					proIoTOrder.setDescription(desc);
					proIoTOrder.setEndUuid(uuid);
					proIoTOrder.setLogId(logId);
					proIoTOrder.setMac(mac);
					proIoTOrder.setEndTime(new Date(time));
					proIoTOrder.setStatus("3");
					proIoTOrderService.save(proIoTOrder);
				}
			}

		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/mofang/session")
	@ResponseBody
	public String getSession() {
		try {
			String sessionId = getMofangSessionId();
			return sessionId;
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return null;
	}

	@RequestMapping(value = "/mofang/notice")
	@ResponseBody
	public String sendNotice(String status) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("magneticStripleId", "000117122800001E");
			map.put("status", status);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("occurrenceTimeString", sdf.format(new Date()));
			map.put("companyOrganId", "10039");
			map.put("storeOrganId", "10040");
			map.put("eventId", "000117122800001E1");
			String jsonStr = JSON.toJSONString(map);
			return HttpUtils.postMofangJson(getMofangSessionId(), NOTICE_URL, jsonStr);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return null;
	}
}
