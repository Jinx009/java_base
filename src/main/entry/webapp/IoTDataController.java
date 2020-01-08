package main.entry.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import database.models.device.DeviceSensor;
import database.models.log.LogSensorStatus;
import service.basicFunctions.device.DeviceSensorService;
import service.basicFunctions.log.LogSensorLogService;
import utils.HttpUtil;
import utils.MD5Util;
import utils.baoxin.SendUtils;
import utils.model.Resp;

@Controller
public class IoTDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(IoTDataController.class);
	
	@Autowired
	private DeviceSensorService deviceSensorService;
	@Autowired
	private LogSensorLogService logSensorLogService;
	
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
