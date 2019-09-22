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
	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000)
	public void getVedio() {
		try {
//			List<IoTCloudDevice> list = iotCloudDeviceService.findByLocalIp("QJ_PUSHI");
//			if (list != null && !list.isEmpty()) {
//				for (IoTCloudDevice iot : list) {
//					String deviceId = iot.getUdpIp().split("_")[0];
//					String json = HttpUtils.getPuzhiJob(deviceId);
//					JSONObject obj = JSONObject.parseObject(json);
//					String _d = obj.getString("data");
//					if (StringUtil.isNotBlank(_d)) {
//						String listStr = JSONObject.parseObject(_d).getString("list");
//						List<PuzhiJob> jobs = JSONObject.parseArray(listStr, PuzhiJob.class);
//						if (jobs != null && !jobs.isEmpty()) {
//							for (PuzhiJob pz : jobs) {
//								String $cmd = pz.getFeatureCtx();
//								String cmd = UrlUtils.parse($cmd, "$cmd");
//								if (iot.getType() != 2) {
//									pz.setCreateTime(new Date());
//									pz.setMac(iot.getMac());
//									pz.setCmd(cmd);
//									pz.setTaskStatus(3);
//									puzhiJobService.save(pz);
//									Map<String, Object> _r = new HashMap<>();
//									String r = "$cmd=" + cmd + "&result= 此类设备不支持下发&msgid=" + pz.getMsgid();
//									_r.put("data", r);
//									HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//								}
//								//如果是电信设备才往下发
//								if (iot.getType() == 2) {
//									log.warn("pz:{}", JSONObject.toJSONString(pz));
//									Map<String, Object> map = new HashMap<>();
//									String data = "";
//									map.put("mac", iot.getMac());
//									// 保存
//									pz.setCreateTime(new Date());
//									pz.setMac(iot.getMac());
//									pz.setCmd(cmd);
//									pz.setTaskStatus(0);
//									pz = puzhiJobService.save(pz);
//									try {
//										// 解析指令
//										if (cmd.equals("reboot")) {//重启设备
//											data = "48003600"+UrlUtils.getSixHex(pz.getMac(),pz.getId()).toUpperCase();
//											map.put("data", data);
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//											String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
//											pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
//											puzhiJobService.update(pz);
//										}else if (cmd.equals("getstatus")) {//获取设备状态
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&state={}";
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//											pz.setTaskStatus(1);
//											puzhiJobService.update(pz);
//										}else if (cmd.equals("reqtime")) {//获取设备时间
//											SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&time="+sdf.format(new Date());
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//											pz.setTaskStatus(1);
//											puzhiJobService.update(pz);
//										} else if (cmd.equals("getsensorID")) {//获取传感器类型
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&sensor_id=103_3";
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//											pz.setTaskStatus(1);
//											puzhiJobService.update(pz);
//										}else if (cmd.equals("getworkmode")) {//获取传感器工作模式
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&mode=0";
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//											pz.setTaskStatus(1);
//											puzhiJobService.update(pz);
//										}else if (cmd.equals("setsensortime")) {//设置传感器时间
//											String upload_intv = UrlUtils.parse($cmd, "upload_intv");
//											Integer value = Integer.valueOf(upload_intv);
//											data = "480034040101" + UrlUtils.getHex(value).toUpperCase()+UrlUtils.getSixHex(pz.getMac(),pz.getId()).toUpperCase();
//											map.put("data", data);
//											String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
//											pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
//											puzhiJobService.update(pz);
//											String sample_intv = UrlUtils.parse($cmd, "sample_intv");
//											data = "48007A02010" + sample_intv;
//											map.put("data", data);
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//										} else if (cmd.equals("setsensorattr")) {//设置传感器属性
//											String threshold = UrlUtils.parse($cmd, "threshold");
//											String[] s = threshold.split(",");
//											double a = Double.valueOf(s[0]);
//											double b = Double.valueOf(s[1]);
//											data = "4800450501" + UrlUtils.getHex((int) (a * 1000)).toUpperCase()+ UrlUtils.getHex((int) (b * 1000)).toUpperCase()+UrlUtils.getSixHex(pz.getMac(),pz.getId()).toUpperCase();;
//											map.put("data", data);
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//											String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
//											pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
//											puzhiJobService.update(pz);
//										} else {
//											Map<String, Object> _r = new HashMap<>();
//											String r = "$cmd=" + cmd + "&result=暂不支持&msgid=" + pz.getMsgid();
//											_r.put("data", r);
//											HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//											pz.setTaskStatus(2);//不支持的命令状态为2
//											puzhiJobService.update(pz);
//										}
//									} catch (Exception e) {
//										log.error("e:{}",e);
//										Map<String, Object> _r = new HashMap<>();
//										String r = "$cmd=" + cmd + "&result=指令参数异常&msgid=" + pz.getMsgid();
//										_r.put("data", r);
//										HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
//										pz.setTaskStatus(-3);//参数异常状态为-3
//										puzhiJobService.update(pz);
//									}
//								}
//							}
//						}
//					}
//				}
//			}
			//新固件
			List<IoTCloudDevice> list2 = iotCloudDeviceService.findByLocalIp("QJ_ZHANWAY_V_3.0_PUSHI");
			if (list2 != null && !list2.isEmpty()) {
				for (IoTCloudDevice iot : list2) {
					String deviceId = iot.getUdpIp().split("_")[0];
					String json = HttpUtils.getPuzhiJob(deviceId);
					JSONObject obj = JSONObject.parseObject(json);
					String _d = obj.getString("data");
					if (StringUtil.isNotBlank(_d)) {
						String listStr = JSONObject.parseObject(_d).getString("list");
						List<PuzhiJob> jobs = JSONObject.parseArray(listStr, PuzhiJob.class);
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
									PuzhiJob j = puzhiJobService.findByUuid(pz.getMsgid());
									if(j==null) {
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
												data = "48003600"+getMore(Integer.toHexString(pz.getId()));
												map.put("data", data);
												Map<String, Object> _r = new HashMap<>();
												String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
												_r.put("data", r);
												HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
												String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
												pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
												pz.setStatus("已接收任务");
												pz.setTaskStatus(1);
												puzhiJobService.update(pz);
											}else if (cmd.equals("getstatus")) {//获取设备状态
												Map<String, Object> _r = new HashMap<>();
												String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&state={}";
												_r.put("data", r);
												HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
												pz.setTaskStatus(1);
												pz.setStatus("直接返回");
												puzhiJobService.update(pz);
											}else if (cmd.equals("reqtime")) {//获取设备时间
												SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
												Map<String, Object> _r = new HashMap<>();
												String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&time="+sdf.format(new Date());
												_r.put("data", r);
												HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
												pz.setTaskStatus(1);
												pz.setStatus("直接返回");
												puzhiJobService.update(pz);
											} else if (cmd.equals("getsensorID")) {//获取传感器类型
												Map<String, Object> _r = new HashMap<>();
												String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&sensor_id=103_3";
												_r.put("data", r);
												HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
												pz.setTaskStatus(1);
												pz.setStatus("直接返回");
												puzhiJobService.update(pz);
											}else if (cmd.equals("getworkmode")) {//获取传感器工作模式
												Map<String, Object> _r = new HashMap<>();
												String r = "$cmd=" + cmd + "&result=succ&msgid=" + pz.getMsgid()+"&mode=0";
												_r.put("data", r);
												HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
												pz.setTaskStatus(1);
												pz.setStatus("直接返回");
												puzhiJobService.update(pz);
											}else if (cmd.equals("setsensortime")) {//设置传感器时间 采样间隔
												String upload_intv = UrlUtils.parse($cmd, "sample_intv");
												String[] s = upload_intv.split(",");
												data = "48007D0701" + getFourHex(Integer.valueOf(s[0])).toUpperCase()
														+ getFourHex(Integer.valueOf(s[1])).toUpperCase()
														+ getFourHex(Integer.valueOf(s[2])).toUpperCase()+getMore(Integer.toHexString(pz.getId()));
												map.put("data", data);
												String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
												pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
												pz.setTaskStatus(1);
												pz.setStatus("设置成功");
												puzhiJobService.update(pz);
//												String sample_intv = UrlUtils.parse($cmd, "sample_intv");
//												data = "48007A02010" + sample_intv;
//												map.put("data", data);
												Map<String, Object> _r = new HashMap<>();
												String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
												_r.put("data", r);
												HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
											} else if (cmd.equals("setsensorattr")) {//设置传感器属性 阈值
												String threshold = UrlUtils.parse($cmd, "threshold");
												String[] s = threshold.split(",");
												String ax = FloatToHexString(Float.valueOf(s[0]));
												String ay = FloatToHexString(Float.valueOf(s[1]));
												String az = FloatToHexString(Float.valueOf(s[2]));
												String x = FloatToHexString(Float.valueOf(s[0]));
												String y = FloatToHexString(Float.valueOf(s[1]));
												String z = FloatToHexString(Float.valueOf(s[2]));
												data = "48007B1901"+ax+ay+az+x+y+z+getMore(Integer.toHexString(pz.getId()));
												map.put("data", data);
												Map<String, Object> _r = new HashMap<>();
												String r = "$cmd=" + cmd + "&result=已接收&msgid=" + pz.getMsgid();
												_r.put("data", r);
												HttpUtils.postPuzhiJob(deviceId, JSONObject.toJSONString(_r));
												String res = HttpUtils.postJson("http://106.14.94.245:8091/job/send",JSONObject.toJSONString(map));
												pz.setTelcomTaskId(JSONObject.parseObject(res).getString("data"));
												pz.setTaskStatus(1);
												pz.setStatus("设置成功");
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
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}
	
	public static String getFourHex(Integer id) {
		String _num =  Integer.toHexString(id);
		if(_num.length()==1){
			return "0"+_num+"00";
		}
		if(_num.length()==2){
			return _num+"00";
		}
		if(_num.length()==3){
			return _num.substring(1,3)+"0"+_num.substring(0,1);
		}
		if(_num.length()==4){
			return _num.substring(2,4)+_num.substring(0,2);
		}
		return _num;
	}

	
	/**
	 * 浮点数按IEEE754标准转16进制字符串
	 * @param f
	 * @return
	 */
	public  String FloatToHexString(float f){
		int i  = Float.floatToIntBits(f);
        String str = Integer.toHexString(i).toUpperCase();
        return str;
	}
	
	private static String getMore(String hexString) {
		hexString = hexString.toUpperCase();
		if(hexString.length()==1){
			return "0000000"+hexString;
		}
		if(hexString.length()==2){
			return "000000"+hexString;
		}
		if(hexString.length()==3){
			return "00000"+hexString;
		}
		if(hexString.length()==4){
			return "0000"+hexString;
		}
		if(hexString.length()==5){
			return "000"+hexString;
		}
		if(hexString.length()==6){
			return "00"+hexString;
		}
		if(hexString.length()==7){
			return "0"+hexString;
		}
		return hexString;
	}


	public static void main(String[] args) {
//		double d = Double.valueOf("0.03");
//		int a = (int) (d * 1000);
//		String c = UrlUtils.getHex(a);
		  long dec_num = Long.parseLong("01869f", 16);  
		System.out.println(dec_num);
	}

}