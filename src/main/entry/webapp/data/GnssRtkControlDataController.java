package main.entry.webapp.data;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.StringUtil;
import database.model.GnssRtkControl;
import main.entry.webapp.BaseController;
import main.entry.webapp.mqtt.MqttUtils;
import service.GnssRtkControlService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtkcontrol")
public class GnssRtkControlDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GnssRtkControlDataController.class);
	
	@Autowired
	private GnssRtkControlService gnssRtkControlService;
	
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(gnssRtkControlService.findByMac(mac));
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "update")
	@ResponseBody
	public Resp<?> update(int id){
		Resp<?> resp = new Resp<>(false);
		try {
			GnssRtkControl grc = gnssRtkControlService.find(id);
			grc.setStatus(2);
			gnssRtkControlService.update(grc);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String mac,String p1,String p2,String p3,String cmd){
		Resp<?> resp = new Resp<>(false);
		try {
			List<GnssRtkControl> list = gnssRtkControlService.findByMacAndStatus(mac,0);
			if(list!=null&&!list.isEmpty()) {
				resp.setMsg("该设备有尚未完成的命令！");
				return resp;
			}else {
				String content = "";
				StringBuilder sb = new StringBuilder();
				GnssRtkControl grc = new GnssRtkControl();
				grc.setCmd(cmd);
				grc.setCreateTime(new Date());
				grc.setMac(mac);
				grc.setResultStr("");
				grc.setStatus(0);
				grc = gnssRtkControlService.save(grc);
				String sn = StringUtil.getMore(Integer.toHexString(grc.getId()));
				if(cmd.equals("30")) {
					grc.setCmdName("重启");
					sb.append("4800");
					sb.append(sn);
					sb.append("3000");
				}
				if(cmd.equals("32")) {
					grc.setCmdName("设置、查询GNSS接收机定位工作模式");
					sb.append("4800");
					sb.append(sn);
					sb.append("3203");
					sb.append(p1);
					sb.append(p2);
					sb.append(p3);
				}
				if(cmd.equals("34")) {
					grc.setCmdName("设置debug模式命令");
					sb.append("4800");
					sb.append(sn);
					sb.append("3401");
					sb.append(p1);
				}
				if(cmd.equals("35")) {
					grc.setCmdName("设置gnss输出速率模式命令");
					sb.append("4800");
					sb.append(sn);
					sb.append("3501");
					sb.append(StringUtil.getLess(Integer.toHexString(Integer.valueOf(p1))));
				}
				if(cmd.equals("36")) {
					grc.setCmdName("校准加速度传感器");
					sb.append("4800");
					sb.append(sn);
					sb.append("3600");
				}
				if(cmd.equals("37")) {
					grc.setCmdName("设置/查询加速度传感器时间设定");
					sb.append("4800");
					sb.append(sn);
					sb.append("3707");
					sb.append(p1);
					String[] s = p2.split("_");
					sb.append(StringUtil.getLess(Integer.toHexString(Integer.valueOf(s[0]))));
					sb.append(StringUtil.getLess(Integer.toHexString(Integer.valueOf(s[1]))));
					sb.append(StringUtil.getLess(Integer.toHexString(Integer.valueOf(s[2]))));
				}
				if(cmd.equals("38")) {
					grc.setCmdName("设置/查询加速度传感器采样间隔");
					sb.append("4800");
					sb.append(sn);
					sb.append("3802");
					sb.append(p1);
					sb.append(p2);
				}
				if(cmd.equals("39")) {
					grc.setCmdName("设置/查询加速度传感器采样间隔");
					sb.append("4800");
					sb.append(sn);
					sb.append("3919");
					sb.append(p1);
					String[] s = p2.split(",");
					String[] s2 = p3.split(",");
					sb.append(StringUtil.FloatToHexString(Float.valueOf(s[0])));
					sb.append(StringUtil.FloatToHexString(Float.valueOf(s[1])));
					sb.append(StringUtil.FloatToHexString(Float.valueOf(s[2])));
					sb.append(StringUtil.FloatToHexString(Float.valueOf(s2[0])));
					sb.append(StringUtil.FloatToHexString(Float.valueOf(s2[1])));
					sb.append(StringUtil.FloatToHexString(Float.valueOf(s2[2])));
				}
				content = sb.toString();
				grc.setContent(content);
				gnssRtkControlService.update(grc);
				MqttUtils.sendCmd(1,content,mac);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	

	
	
}
