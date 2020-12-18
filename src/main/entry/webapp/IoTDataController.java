package main.entry.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;

import database.models.device.DeviceJob;
import database.models.device.DeviceSensor;
import database.models.log.LogOperation;
import database.models.log.LogSensorSource;
import database.models.log.LogSensorStatus;
import service.basicFunctions.device.DeviceJobService;
import service.basicFunctions.device.DeviceSensorInfoService;
import service.basicFunctions.device.DeviceSensorService;
import service.basicFunctions.log.LogOperationService;
import service.basicFunctions.log.LogSensorLogService;
import service.basicFunctions.log.LogSensorSourceService;
import utils.Constant;
import utils.HttpUtil;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.MD5Util;
import utils.StringUtil;
import utils.baoxin.SendUtils;
import utils.model.Resp;

@Controller
public class IoTDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(IoTDataController.class);
	
	@Autowired
	private DeviceSensorService deviceSensorService;
	@Autowired
	private LogSensorLogService logSensorLogService;
	@Autowired
	private LogOperationService logOperationService;
	@Autowired
	private DeviceSensorInfoService deviceSensorInfoService;
	@Autowired
	private LogSensorSourceService logSensorSourceService;
	@Autowired
	private DeviceJobService deviceJobService;
	
	
	/**
	 * 模糊查询地址
	 * @param location
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/iot/iot/sensor/address")
	@ResponseBody
	public Resp<?> getAddress(String location,String address){
		Resp<?> resp = new Resp<>(false);
		try {
			if("wuhan".equals(location)) {
				List<Object> list = deviceSensorService.findParentMacByLike(address);
				return new Resp<>(list);
			}else if("shanghai".equals(location)) {
				List<Object> list = deviceSensorInfoService.findByAddressLike(address);
				return new Resp<>(list);
			}
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 模糊查询mac
	 * @param mac
	 * @return
	 */
	@RequestMapping(value = "/iot/iot/sensor/mac")
	@ResponseBody
	public Resp<?> getMac(String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			List<DeviceSensor> list = deviceSensorService.findByMacLike(mac);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * 模糊查询mac
	 * @param mac
	 * @return
	 */
	@RequestMapping(value = "/iot/iot/sensor/source")
	@ResponseBody
	public Resp<?> source(String mac,String dateStr){
		Resp<?> resp = new Resp<>(false);
		try {
			List<LogSensorSource> list = logSensorSourceService.findByMacAndDate(dateStr, mac);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 下发电信设备命令
	 * @param deviceId
	 * @param data
	 */
	private  void sendCmd(String deviceId,String data){
		try {
			HttpsUtil httpsUtil = new HttpsUtil();
			httpsUtil.initSSLConfigForTwoWay();
			String accessToken = login(httpsUtil);
			String urlPostAsynCmd = Constant.POST_ASYN_CMD;
			String appId = Constant.APPID;
			String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;
			String serviceId = "data";
			String method = "command";
			ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"CMD_DATA\":\"" + data + "\"}");
			Map<String, Object> paramCommand = new HashMap<>();
			paramCommand.put("serviceId", serviceId);
			paramCommand.put("method", method);
			paramCommand.put("paras", paras);
			Map<String, Object> paramPostAsynCmd = new HashMap<>();
			paramPostAsynCmd.put("deviceId", deviceId);
			paramPostAsynCmd.put("command", paramCommand);
			paramPostAsynCmd.put("callbackUrl", callbackUrl);
			String jsonRequest = JsonUtil.jsonObj2Sting(paramPostAsynCmd);
			Map<String, String> header = new HashMap<>();
			header.put(Constant.HEADER_APP_KEY, appId);
			header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
			HttpResponse responsePostAsynCmd = httpsUtil.doPostJson(urlPostAsynCmd, header, jsonRequest);
			String responseBody = httpsUtil.getHttpResponseBody(responsePostAsynCmd);
			log.warn("msg:{}", responseBody);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
	}
	
	@RequestMapping(value = "/iot/iot/sensor/job/update")
	@ResponseBody
	public Resp<?> jobUpdate(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			DeviceJob job = deviceJobService.find(id);
			job.setStatus(1);
			deviceJobService.update(job);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	/**
	 * 新增任务
	 * @param mac
	 * @return
	 */
	@RequestMapping(value = "/iot/iot/sensor/job")
	@ResponseBody
	public Resp<?> job(String mac,String cmd,String data1,String data2,String data3){
		Resp<?> resp = new Resp<>(false);
		try {
			List<DeviceJob> list = deviceJobService.findByMacAndStatus(mac,0);
			DeviceSensor sensor = deviceSensorService.findByMac(mac);
			String deviceId = sensor.getDeviceId();
			if(list!=null&&!list.isEmpty()) {
				resp.setMsg("还有未完成命令！");
				return resp;
			}else {
				DeviceJob job = new DeviceJob();
				job.setCreateTime(new Date());
				job.setCmd(cmd);
				String data = "4800"+cmd;
				if("38".equals(cmd)) {
					data+= "02"+data1+data2;
					job.setCmd(data);
					String cc = "";
					if("00".equals(data1)) {
						cc+="查询设备工作状态";
					}
					if("01".equals(data1)) {
						cc+="设置";
						if("01".equals(data2)) {
							cc+="设备工作状态:正常模式";
						}
						if("02".equals(data2)) {
							cc+="设备工作状态:睡眠模式";
						}
						if("03".equals(data2)) {
							cc+="设备工作状态:待机模式";
						}
					}
					job.setCmdContent(cc);
				}
				if("36".equals(cmd)) {
					data+= "00";
					job.setCmd(data);
					job.setCmdContent("重启");
				}
				if("31".equals(cmd)) {
					data+= "00";
					job.setCmd(data);
					job.setCmdContent("恢复出厂设置");
				}
				if("78".equals(cmd)) {
					data+= "0C000000000000000000000000";
					job.setCmd(data);
					job.setCmdContent("查询软硬件版本");
				}
				if("0F".equals(cmd)) {
					data+= "02"+data1+data2;
					job.setCmd(data);
					String cc = "";
					if("00".equals(data1)) {
						cc+="查询2530射频接收模式";
					}
					if("01".equals(data1)) {
						cc+="设置";
						if("01".equals(data2)) {
							cc+="2530射频接收模式打开";
						}
						if("00".equals(data2)) {
							cc+="2530射频接收模式关闭";
						}
					}
					job.setCmdContent(cc);
				}
				if("79".equals(cmd)) {
					data+= "02"+data1;
					String cc = "";
					if("00".equals(data1)) {
						data+="00";
						cc+="查询NB低信号阈值";
					}
					if("01".equals(data1)) {
						data+=Integer.toHexString(Integer.valueOf(data2));
						cc+="设置NB低信号阈值："+data2;
					}
					job.setCmd(data);
					job.setCmdContent(cc);
				}
				if("62".equals(cmd)) {
					data+= "02"+data1+data2;
					String cc = "";
					if("00".equals(data1)) {
						cc+="查询浮动基准开关";
					}
					if("01".equals(data1)) {
						data+= Integer.toHexString(Integer.valueOf(data2));
						job.setCmd(data);
						if("01".equals(data2)) {
							cc+="设置浮动基准开启";
						}
						if("00".equals(data2)) {
							cc+="设置浮动基准关闭";
						}
					}
					job.setCmd(data);
					job.setCmdContent(cc);
				}
				if("34".equals(cmd)) {
					data+= "04"+data1+data2;
					job.setCmd(data);
					String cc = "";
					if("00".equals(data1)) {
						cc+="查询心跳间隔:";
						data+="0000";
					}
					if("01".equals(data1)) {
						cc+="设置心跳间隔:";
						String d = Integer.toHexString(Integer.valueOf(data3));
						if(d.length()==1) {
							d = "000"+d;
						}
						if(d.length()==2) {
							d = "00"+d;
						}
						if(d.length()==3) {
							d = "0"+d;
						}
						String d1 = d.substring(2, 4)+d.substring(0, 2);
						data+= d1;
					}
					if("01".equals(data2)) {
						cc+="NB；";
					}
					if("00".equals(data2)) {
						cc+="2530；";
					}
					cc+= data3;
					job.setCmd(data);
					job.setCmdContent(cc);
				}
				if("3A".equals(cmd)) {
					data+= "02"+data1+data2;
					job.setCmd(data);
					String cc = "";
					if("00".equals(data1)) {
						cc+="查询NB锁定小区";
					}
					if("01".equals(data1)) {
						cc+="设置NB锁定小区:";
						if("01".equals(data2)) {
							cc+="锁定；";
						}
						if("00".equals(data2)) {
							cc+="不锁定；";
						}
					}
					job.setCmdContent(cc);
				}
				if("3B".equals(cmd)) {
					String d = toHexString(data1);
					String dl = Integer.toHexString(d.length()/2);
					if(dl.length()<2) {
						dl = "0"+dl;
					}
					data+= dl+d;
					job.setCmd(data);
					String cc = "AT命令"+data1;
					job.setCmdContent(cc);
				}
				if("32".equals(cmd)) {
					String cc ="设置参数";
					job.setCmdContent(cc);
					String d =data1;
					int i = 1;
					Map<String, Object> map = JSONObject.parseObject(data2);
					if(!"0000".equals("s10")) {
						d+= getHex(i);
						d+="10";
						d+="04";
						d+= map.get("s10");
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s11")))) {
						d+= getHex(i);
						d+="11";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s11")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s12")))) {
						d+= getHex(i);
						d+="12";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s12")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s13")))) {
						d+= getHex(i);
						d+="13";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s13")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s14")))) {
						d+= getHex(i);
						d+="14";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s14")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s15")))) {
						d+= getHex(i);
						d+="15";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s15")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s16")))) {
						d+= getHex(i);
						d+="16";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s16")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s17")))) {
						d+= getHex(i);
						d+="17";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s17")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s18")))) {
						d+= getHex(i);
						d+="18";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s18")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s19")))) {
						d+= getHex(i);
						d+="19";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s19")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s1A")))) {
						d+= getHex(i);
						d+="1A";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s1A")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s20")))) {
						d+= getHex(i);
						d+="20";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s20")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s21")))) {
						d+= getHex(i);
						d+="21";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s21")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s22")))) {
						d+= getHex(i);
						d+="22";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s22")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s23")))) {
						d+= getHex(i);
						d+="23";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s23")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s24")))) {
						d+= getHex(i);
						d+="24";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s24")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s25")))) {
						d+= getHex(i);
						d+="25";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s25")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s26")))) {
						d+= getHex(i);
						d+="26";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s26")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s27")))) {
						d+= getHex(i);
						d+="27";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s27")));
						i++;
					}
					if(StringUtil.isNotBlank(String.valueOf(map.get("s28")))) {
						d+= getHex(i);
						d+="28";
						d+="04";
						d+= getBigHex(String.valueOf(map.get("s28")));
						i++;
					}
					int length = d.length()/2;
					data+=getHex(length)+d;
					job.setCmd(data);
				}
				if("3C".equals(cmd)) {
					String d = toHexString(data3);
					String dl = Integer.toHexString(d.length()/2+2);
					if(dl.length()<2) {
						dl = "0"+dl;
					}
					data+= dl+data1+data2+d;
					job.setCmd(data);
					String cc = "";
					if("00".equals(data1)) {
						cc+="查询NB服务器地址";
					}
					if("01".equals(data1)) {
						cc+="设置NB服务器地址:";
						if("01".equals(data2)) {
							cc+="备用；";
						}
						if("00".equals(data2)) {
							cc+="主地址；";
						}
						cc+=d;
					}
					job.setCmdContent(cc);
				}
				job.setRecSt(1);
				job.setTarget(mac);
				job.setStatus(0);
				deviceJobService.save(job);
				try {
					sendCmd(deviceId, data);
					return new Resp<>(true);
				} catch (Exception e) {
					log.error("e:{}",e);
				}
			}
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	private String getBigHex(String str) {
		Integer num = Integer.valueOf(str);
		String hex = Integer.toHexString(num);
		if(hex.length()==1) {
			hex = "0000000"+hex;
		}
		if(hex.length()==2) {
			hex = "000000"+hex;
		}
		if(hex.length()==3) {
			hex = "00000"+hex;
		}
		if(hex.length()==4) {
			hex = "0000"+hex;
		}
		if(hex.length()==5) {
			hex = "000"+hex;
		}
		if(hex.length()==6) {
			hex = "00"+hex;
		}
		if(hex.length()==7) {
			hex = "0"+hex;
		}
		return hex.substring(6, 8)+hex.substring(4, 6)+hex.substring(2, 4)+hex.substring(0, 2);
	}

	private String getHex(int i ) {
		if(i<17) {
			return "0"+Integer.toHexString(i);
		}else {
			return Integer.toHexString(i);
		}
	}
	
	public static String toHexString(String s) {
	   String str = "";
	   for (int i = 0; i < s.length(); i++) {
	    int ch = (int) s.charAt(i);
	    String s4 = Integer.toHexString(ch);
	    str = str + s4;
	   }
	   return str;
	}
	
	public static void main(String[] args) {
		System.out.println(toHexString("AT+CGATT?").toUpperCase());
	}

	
	/**
	 * 维修、安装、操作等记录新增
	 * @param mac
	 * @param lng
	 * @param lat
	 * @return
	 */
	@RequestMapping(value = "/iot/iot/sensor/operation/save")
	@ResponseBody
	public Resp<?> save(String mac,String lng,String lat,String picUrl,String ver,String type,String address,String description,String location){
		Resp<?> resp = new Resp<>(false);
		try {
			LogOperation log = new LogOperation();
			if(type.equals("安装和校准")) {
				DeviceSensor sensor = deviceSensorService.findByMac(mac);
				if(sensor!=null) {
					sensor.setParentMac(address);
					sensor.setDesc(description);
					deviceSensorService.update(sensor);
				}else {
					sensor = new DeviceSensor();
					sensor.setCreateTime(new Date());
					sensor.setParentMac(address);
					sensor.setDesc(description);
					sensor.setMac(mac);
				}
				if(location.equals("wuhan")) {
					sensor.setAreaId(64);
					sensor.setCameraName("001001");
					deviceSensorService.update(sensor);
				}
			}
			log.setCreateTime(new Date());
			log.setLat(lat);
			log.setLng(lng);
			log.setType(type);
			log.setVer(ver);
			log.setMac(mac);
			log.setPicUrl(picUrl);
			logOperationService.save(log);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 查找列表
	 * @param mac
	 * @return
	 */
	@RequestMapping(value = "/iot/iot/sendor/operation/list")
	@ResponseBody
	public Resp<?> list(String mac){
		Resp<?> resp = new Resp<>(false);
		try {
			List<LogOperation> list = logOperationService.findByMac(mac);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	
	/**
	 * 2020-01-08
     * 摄像头数据输入
     * @param bytes
     * @return
     */
    @RequestMapping(value = "/iot/iot/sensor/vedioReport", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String vedioSend(@RequestBody byte[] bytes){
        try {
            JSONObject json = JSONObject.parseObject(new String(bytes, "utf-8"));
            String mac = json.getString("mac");
            String cameraTime =  json.getString("ChangeTime");
            String cameraId = json.getString("cameraId");
            String cph = json.getString("cph");
            String cpColor = json.getString("cpColor");
            String status = json.getString("status");
            String picLink = json.getString("picLink");
            DeviceSensor sensor = deviceSensorService.findByMac(mac);
            sensor.setPicLink(picLink);
            sensor.setCameraName(cameraId);
            sensor.setVedioStatus(status);
            sensor.setCameraId(cameraId);
            sensor.setCpColor(cpColor);
            sensor.setVedioTime(cameraTime);
            sensor.setCId(cameraId);
            boolean res = false;
            LogSensorStatus log = new LogSensorStatus();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            log.setMac(sensor.getMac());
            log.setChangeTime(sdf.parse(cameraTime));
            log.setAvailable(Integer.valueOf(status));
            log.setFailTimes(0);
            log.setSendStatus(0);
            log.setAreaId(sensor.getAreaId());
            log.setCreateTime(new Date());
            log.setCameraId(cameraId);
            log.setCpColor(cpColor);
            log.setCph(cph);
            log.setPicLink(picLink);
            log.setType(1);
            log.setDescription(sensor.getDesc());
            log.setStatus(status);
            logSensorLogService.saveStatusLog(log);
            String[] macs2 = {
                    "0001180614000011",
                    "00011806140000A0",
                    "00011806140000A6",
                    "000118061400007A"
            };
            boolean type = false;
            String av = String.valueOf(sensor.getAvailable());
            for(String m : macs2){
                if(m.equals(sensor.getMac())){
                    type = true;
                }
            }
            if(!type&&!"0001180614000062".equals(sensor.getMac())){//不是12-15号车位只推送视频信息，不夹杂地磁信息且地磁不是8号车位
                sensor.setSensorTime("");
                av = "";
            }
            if(status.equals(String.valueOf(sensor.getAvailable()))){
                Date last = sensor.getHappenTime();
                Date now = sdf.parse(sensor.getVedioTime());
                int c = (int)((now.getTime() - last.getTime()) / 1000);
                if(-180<c&&c<180&&!sensor.getCph().equals(cph)) {//小于三分钟车牌号一致的过滤掉
                    sensor.setCph(cph);
                    deviceSensorService.update(sensor);
                    res = SendUtils.send(sensor.getHappenTime(), sensor.getMac(), av,
                            "", sensor.getSensorTime(), sensor.getVedioTime(), sensor.getCameraId(),
                            sensor.getCph(), sensor.getCpColor(), sensor.getVedioStatus(), sensor.getPicLink());
                }
                if(c>180&&!sensor.getCph().equals(cph)){//大于三分钟状态车牌一样的视频先不过滤
                    if(type&&0==sensor.getAvailable()){//12-15号车位单独报一个车出不推送
                        return "{\"status\":\"ok\"}";
                    }else{
                        if(!"0001180614000062".equals(sensor.getMac())){//8号不享受超三分钟推送
                            sensor.setCph(cph);
                            sensor.setHappenTime(sdf.parse(cameraTime));
                            deviceSensorService.update(sensor);
                            res = SendUtils.send(sensor.getHappenTime(), sensor.getMac(), av,
                                    "", sensor.getSensorTime(), sensor.getVedioTime(), sensor.getCameraId(),
                                    sensor.getCph(), sensor.getCpColor(), sensor.getVedioStatus(), sensor.getPicLink());
                        }
                    }
                }
            }else{
                if(type&&"0".equals(sensor.getVedioStatus())){//12-15号车位单独报一个车出不推送
                    return "{\"status\":\"ok\"}";
                }else{
                    if(!"0001180614000062".equals(sensor.getMac())){
                        sensor.setCph(cph);
                        sensor.setAvailable(Integer.valueOf(sensor.getVedioStatus()));
                        sensor.setHappenTime(sdf.parse(cameraTime));
                        deviceSensorService.update(sensor);
                        res = SendUtils.send(sensor.getHappenTime(),sensor.getMac(),av,
                                "",sensor.getSensorTime(),sensor.getVedioTime(),sensor.getCameraId(),
                                sensor.getCph(),sensor.getCpColor(),sensor.getVedioStatus(),sensor.getPicLink());
                    }
                }
            }
            log = logSensorLogService.getStatusLog(log.getId());
            if(res){
                log.setSendStatus(1);
                log.setSendTime(new Date());
                logSensorLogService.updateStatus(log);
            }else{
                log.setFailTimes(log.getFailTimes() + 1);
                logSensorLogService.updateStatus(log);
            }
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return "{\"status\":\"ok\"}";
    }
	
	@RequestMapping(path = "/chaozhouPush")
	@ResponseBody
	public Resp<?> str(@RequestBody String s) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("data:{}",s);
			HttpUtil.postJson("http://www.czparking.com/api/heartbeat", s);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	

	/**
	 * 2020-01-08
	 * @param status
	 * @param sign
	 * @param mac
	 * @return
	 */
    @RequestMapping(value = "/iot/openApi/changeSendStatus")
    @ResponseBody
    public Map<String,String> changeStatus(@RequestParam(value = "status", required = true) String status,
                                           @RequestParam(value = "sign", required = true) String sign,
                                           @RequestParam(value = "mac", required = true) String mac){
        Map<String,String> map = new HashMap<String, String>();
        map.put("code","500");
        map.put("msg","Sign not invalid");
        try {
            if(MD5Util.toMD5("mac="+mac+"&status="+status+"&sign=zhanway_guozhi").toLowerCase().equals(sign)){
                DeviceSensor sensor = deviceSensorService.findByMac(mac);
                sensor.setAreaId(1);
                if("0".equals(status)){
                    sensor.setAreaId(2);
                }
                deviceSensorService.update(sensor);
                map.put("code","200");
                map.put("msg","ok");
            }
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return map;
    }
	
}
