package main.entry.webapp.data;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.StringUtil;
import database.model.GnssRtkControl;
import database.model.GnssRtkTopic;
import main.entry.webapp.BaseController;
import main.entry.webapp.mqtt.MqttUtils;
import service.GnssRtkControlService;
import service.GnssRtkTopicService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtktopic")
public class GnssRtkTopicDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(GnssRtkTopicDataController.class);

	@Autowired
	private GnssRtkTopicService gnssRtkTopicService;
	@Autowired
	private GnssRtkControlService gnssRtkControlService;

	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String mac, String topic) {
		Resp<?> resp = new Resp<>(false);
		try {
			GnssRtkTopic t = gnssRtkTopicService.findByMacAndTopic(mac, topic);
			if (t != null) {
				resp.setMsg("您已经订阅过这个主题！");
				return resp;
			} else {
				t = new GnssRtkTopic();
				t.setCreateTime(new Date());
				t.setMac(mac);
				t.setTopic(topic);
				gnssRtkTopicService.save(t);
				StringBuilder sb = new StringBuilder();
				GnssRtkControl grc = new GnssRtkControl();
				grc.setCmd("33");
				grc.setCreateTime(new Date());
				grc.setMac(mac);
				grc.setResultStr("");
				grc.setStatus(0);
				grc = gnssRtkControlService.save(grc);
				String sn = StringUtil.getMore(Integer.toHexString(grc.getId()));
				grc.setCmdName("设置gnss订阅/退订主题设定");
				sb.append("4800");
				sb.append(sn);
				sb.append("33");
				String ac = StringUtil.stringToA(topic);
				sb.append(topic.length()+1);
				sb.append("01");
				sb.append(ac);
				String content = sb.toString();
				grc.setContent(content);
				gnssRtkControlService.update(grc);
				MqttUtils.sendCmd(1,content,mac);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/del")
	@ResponseBody
	public Resp<?> del(int id) {
		Resp<?> resp = new Resp<>(false);
		try {
			GnssRtkTopic t = gnssRtkTopicService.find(id);
			StringBuilder sb = new StringBuilder();
			GnssRtkControl grc = new GnssRtkControl();
			grc.setCmd("33");
			grc.setCreateTime(new Date());
			grc.setMac(t.getMac());
			grc.setResultStr("");
			grc.setStatus(0);
			grc = gnssRtkControlService.save(grc);
			String sn = StringUtil.getMore(Integer.toHexString(grc.getId()));
			grc.setCmdName("设置gnss订阅/退订主题设定");
			sb.append("4800");
			sb.append(sn);
			sb.append("33");
			String ac = StringUtil.stringToA(t.getTopic());
			sb.append(t.getTopic().length()+1);
			sb.append("00");
			sb.append(ac);
			String content = sb.toString();
			grc.setContent(content);
			gnssRtkControlService.update(grc);
			MqttUtils.sendCmd(1,content,t.getMac());
			gnssRtkTopicService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(String mac,int p) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(gnssRtkTopicService.list(mac,p));
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return resp;
	}



}
