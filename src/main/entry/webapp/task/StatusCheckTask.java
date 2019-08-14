package main.entry.webapp.task;

import java.text.SimpleDateFormat;
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
	@Scheduled(cron = "0/3000 * * * * ? ") // 每1分钟
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
								String $cmd = pz.getFeatureCtx();
								String cmd = UrlUtils.parse($cmd, "$cmd");
								if (iot.getType() != 2) {
									pz.setCreateTime(new Date());
									pz.setMac(iot.getMac());
									pz.setCmd(cmd);
									pz.setTaskStatus(3);
									puzhiJobService.save(pz);
									Map<String, Object> _r = new HashMap<>();
									String r = "$cmd=" + cmd + "&result= 此类设备不支持下发&msgid=" + pz.getMsgid();
									_r.put("data", r);
									HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
								}
								//如果是电信设备才往下发
								if (iot.getType() == 2) {
									log.warn("pz:{}", JSONObject.toJSONString(pz));
									Map<String, Object> map = new HashMap<>();
									String data = "";
									map.put("mac", iot.getMac());
									// 保存
									pz.setCreateTime(new Date());
									pz.setMac(iot.getMac());
									pz.setCmd(cmd);
									pz.setTaskStatus(0);
									pz = puzhiJobService.save(pz);
									try {
										// 解析指令
										if (cmd.equals("reboot")) {//重启设备
											data = "48003600"+UrlUtils.getSixHex(pz.getMac(),pz.getId()).toUpperCase();
											map.put("data", data);
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
											pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
											puzhiJobService.update(pz);
										}else if (cmd.equals("getstatus")) {//获取设备状态
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&state={}";
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											pz.setTaskStatus(1);
											puzhiJobService.update(pz);
										}else if (cmd.equals("reqtime")) {//获取设备时间
											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&time="+sdf.format(new Date());
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											pz.setTaskStatus(1);
											puzhiJobService.update(pz);
										} else if (cmd.equals("getsensorID")) {//获取传感器类型
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&sensor_id=103_3";
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											pz.setTaskStatus(1);
											puzhiJobService.update(pz);
										}else if (cmd.equals("getworkmode")) {//获取传感器工作模式
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&mode=0";
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											pz.setTaskStatus(1);
											puzhiJobService.update(pz);
										}else if (cmd.equals("setsensortime")) {//设置传感器时间
											String upload_intv = UrlUtils.parse($cmd, "upload_intv");
											Integer value = Integer.valueOf(upload_intv);
											data = "480034040101" + UrlUtils.getHex(value).toUpperCase()+UrlUtils.getSixHex(pz.getMac(),pz.getId()).toUpperCase();
											map.put("data", data);
											String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
											pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
											puzhiJobService.update(pz);
											String sample_intv = UrlUtils.parse($cmd, "sample_intv");
											data = "48007A02010" + sample_intv;
											map.put("data", data);
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
										} else if (cmd.equals("setsensorattr")) {//设置传感器属性
											String threshold = UrlUtils.parse($cmd, "threshold");
											String[] s = threshold.split(",");
											double a = Double.valueOf(s[0]);
											double b = Double.valueOf(s[1]);
											data = "4800450501" + UrlUtils.getHex((int) (a * 1000)).toUpperCase()+ UrlUtils.getHex((int) (b * 1000)).toUpperCase()+UrlUtils.getSixHex(pz.getMac(),pz.getId()).toUpperCase();;
											map.put("data", data);
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
											pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
											puzhiJobService.update(pz);
										} else {
											Map<String, Object> _r = new HashMap<>();
											String r = "$cmd=" + cmd + "&result=暂不支持&msgid=" + pz.getMsgid();
											_r.put("data", r);
											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											pz.setTaskStatus(2);//不支持的命令状态为2
											puzhiJobService.update(pz);
										}
									} catch (Exception e) {
										log.error("e:{}",e);
										Map<String, Object> _r = new HashMap<>();
										String r = "$cmd=" + cmd + "&result=指令参数异常&msgid=" + pz.getMsgid();
										_r.put("data", r);
										HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
										pz.setTaskStatus(-3);//参数异常状态为-3
										puzhiJobService.update(pz);
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

	public static void main(String[] args) {
//		double d = Double.valueOf("0.03");
//		int a = (int) (d * 1000);
//		String c = UrlUtils.getHex(a);
		  long dec_num = Long.parseLong("01869f", 16);  
		System.out.println(dec_num);
	}

}