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
							for (PuzhiJob pz : jobs) {
								PuzhiJob job = puzhiJobService.findByUuid(pz.getMsgid());
								if (job == null) {
									log.warn("pz:{},job:{}",JSONObject.toJSONString(pz),JSONObject.toJSONString(job));
									pz.setCreateTime(new Date());
									pz.setMac(iot.getMac());
									puzhiJobService.save(pz);
									Map<String, Object> map = new HashMap<>();
									String data = "";
									map.put("mac", iot.getMac());
									if (pz.getFeatureCtx().equals("$cmd=reboot")) {
										data = "48003600";
										map.put("data", data);
										HttpUtils.postJson("http://106.14.94.245:8091/job/send",
												JSONObject.toJSONString(map));
									} else {
										String $cmd = pz.getFeatureCtx();
										String cmd = UrlUtils.parse($cmd, "$cmd");
										if (cmd.equals("setsensortime")) {
											String upload_intv = UrlUtils.parse($cmd, "upload_intv");
											Integer value = Integer.valueOf(upload_intv);
											data = "480034040101" + UrlUtils.getHex(value);
											map.put("data", data);
											HttpUtils.postJson("http://106.14.94.245:8091/job/send",
													JSONObject.toJSONString(map));
											String sample_intv = UrlUtils.parse($cmd, "sample_intv");
											data = "48007A02010" + sample_intv;
											map.put("data", data);
										//	HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
										}
										if (cmd.equals("setsensorattr")) {
											String threshold = UrlUtils.parse($cmd, "threshold");
											String[] s = threshold.split(",");
											Integer a = Integer.valueOf(s[0]);
											Integer b = Integer.valueOf(s[1]);
											data = "4800450501" + UrlUtils.getHex(a * 1000) + UrlUtils.getHex(b * 1000);
											map.put("data", data);
											HttpUtils.postJson("http://106.14.94.245:8091/job/send",
													JSONObject.toJSONString(map));
										}
										if (cmd.equals("setworkmode")) {
											String mode = UrlUtils.parse($cmd, "mode");
											data = "480038010" + mode;
											map.put("data", data);
											HttpUtils.postJson("http://106.14.94.245:8091/job/send",
													JSONObject.toJSONString(map));
										}
									}
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

}