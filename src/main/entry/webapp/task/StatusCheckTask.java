package main.entry.webapp.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import database.models.IoTCloudDevice;
import database.models.PuzhiJob;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.PuzhiJobService;
import utils.HttpUtils;
import utils.StringUtil;
import utils.UrlUtils;

@Component
@Lazy(value = false)
public class StatusCheckTask {

	private static Logger log = LoggerFactory.getLogger(StatusCheckTask.class);

	@Autowired
	private PuzhiJobService puzhiJobService;
	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;

	/**
	 * 搜索任务
	 */
	@Scheduled(cron = "0/10 * * * * ? ") // 每1分钟
	public void getVedio() {
		try {
			List<IoTCloudDevice> list = iotCloudDeviceService.findByLocalIp("QJ_PUSHI");
			if (list != null && !list.isEmpty()) {
				for (IoTCloudDevice iot : list) {
					String deviceId = iot.getUdpIp().split("_")[0];
					String json = HttpUtils.getPuzhiJob(deviceId);
					JSONObject obj = JSONObject.parseObject(json);
					String _d = obj.getString("data");
					if (StringUtil.isNotBlank(_d)) {
						List<PuzhiJob> jobs = JSONObject.parseArray(_d, PuzhiJob.class);
						if (jobs != null && !jobs.isEmpty()) {
							PuzhiJob pz = jobs.get(0);
							int taskNum = puzhiJobService.findByMacAndTaskStatus(iot.getMac(), 0);
							if (taskNum == 0&&iot.getType()==2) {
								log.warn("pz:{}", JSONObject.toJSONString(pz));
								Map<String, Object> map = new HashMap<>();
								String data = "";
								map.put("mac", iot.getMac());
								String $cmd = pz.getFeatureCtx();
								String cmd = UrlUtils.parse($cmd, "$cmd");
								//保存
								pz.setCreateTime(new Date());
								pz.setMac(iot.getMac());
								pz.setCmd(cmd);
								pz.setTaskStatus(0);
								puzhiJobService.save(pz);
								//解析指令
								if (cmd.equals("reboot")) {
									data = "48003600";
									map.put("data", data);
									
									Map<String, Object> _r = new HashMap<>();
									String r = "$cmd="+cmd+"&result=已接收&msgid="+pz.getMsgid();
									_r.put("data", r);
									HttpUtils.postPuzhiJob(deviceId,JSONObject.toJSONString(_r));
									
									String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
									pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
									puzhiJobService.update(pz);
								} else if (cmd.equals("setsensortime")) {
									String upload_intv = UrlUtils.parse($cmd, "upload_intv");
									Integer value = Integer.valueOf(upload_intv);
									data = "480034040101" + UrlUtils.getHex(value);
									map.put("data", data);
									
									String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
									pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
									puzhiJobService.update(pz);
									
									String sample_intv = UrlUtils.parse($cmd, "sample_intv");
									data = "48007A02010" + sample_intv;
									map.put("data", data);
									
									Map<String, Object> _r = new HashMap<>();
									String r = "$cmd="+cmd+"&result=已接收&msgid="+pz.getMsgid();
									_r.put("data", r);
									HttpUtils.postPuzhiJob(deviceId,JSONObject.toJSONString(_r));
									
									// HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
								} else if (cmd.equals("setsensorattr")) {
									String threshold = UrlUtils.parse($cmd, "threshold");
									String[] s = threshold.split(",");
									double a = Double.valueOf(s[0]);
									double b = Double.valueOf(s[1]);
									data = "4800450501" + UrlUtils.getHex((int) (a * 1000))+ UrlUtils.getHex((int) (b * 1000));
									map.put("data", data);
									
									Map<String, Object> _r = new HashMap<>();
									String r = "$cmd="+cmd+"&result=已接收&msgid="+pz.getMsgid();
									_r.put("data", r);
									HttpUtils.postPuzhiJob(deviceId,JSONObject.toJSONString(_r));
									String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
									pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
									puzhiJobService.update(pz);
								}
//								else if (cmd.equals("setworkmode")) {
//									String mode = UrlUtils.parse($cmd, "mode");
//									data = "480038010" + mode;
//									map.put("data", data);
//									// HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
//								}
								else {
									Map<String, Object> _r = new HashMap<>();
									String r = "$cmd="+cmd+"&result=暂不支持&msgid="+pz.getMsgid();
									_r.put("data", r);
									HttpUtils.postPuzhiJob(deviceId,JSONObject.toJSONString(_r));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	public static void main(String[] args) {
		double d = Double.valueOf("0.03");
		int a = (int) (d * 1000);
		String c = UrlUtils.getHex(a);
		System.out.println(a + "---" + c);
	}

}