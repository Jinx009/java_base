package main.entry.webapp;

//import java.io.File;
//import java.io.FileWriter;
//import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.device.DeviceSensor;
import database.models.log.LogSensorHeart;
import service.basicFunctions.device.DeviceSensorService;
import service.basicFunctions.log.LogSensorLogService;
import utils.HttpUtil;
import utils.StringUtil;
import utils.model.Resp;

@Controller
public class TelcomController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(TelcomController.class);
	
	@Autowired
	private DeviceSensorService deviceSensorService;
	@Autowired
	private LogSensorLogService logSensorLogService;

	/**
	 * 启动原始联通版本的udp倾角传感器服务器
	 * @return
	 */
	@RequestMapping(path = "/udpServer")
	@ResponseBody
	public Resp<?> udpServer() {
		try {
			DatagramSocket socket = new DatagramSocket(1121);
			UDPServerThread st = new UDPServerThread(socket);
			st.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	  /**
//     * 潮州智信UDP地磁接入
//     * @param bytes
//     * @return
//     */
//    @RequestMapping(value = "/iot/na/iocm/devNotify/v1.1.0/updateDeviceDatas", method = RequestMethod.POST, produces = "application/json")
//    @ResponseBody
//    public String pushs(@RequestBody byte[] bytes){
//        try {
//            String r = new String(bytes, "utf-8");
//            log.warn("telcom datas:{}",r);
//            if(StringUtil.isNotBlank(r)){
//                TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r, TelcomPushDataModel.class);
//                if (telcomPushDataModel != null) {
//                    List<PushModel> list = telcomPushDataModel.getServices();
//                    PushModel pushModel2 = telcomPushDataModel.getService();
//                    if (pushModel2 != null) {
//                        list.add(pushModel2);
//                    }
//                    if (list != null && !list.isEmpty()) {
//                        for(PushModel pushModel : list){
//                            TModel tModel = pushModel.getData();
//                            String data = tModel.getData();
//                            String mac = data.substring(0, 16);
//                            DeviceSensor sensor = deviceSensorService.findByMac(mac);
//                            if(sensor==null){
//                                sensor = new DeviceSensor();
//                                sensor.setAvailable(0);
//                                sensor.setLastSeenTime(new Date());
//                                sensor.setCreateTime(new Date());
//                                sensor.setMac(mac);
//                                sensor.setSensorStatus(0);
//                                sensor.setVedioStatus("");
//                                sensor.setCph("");
//                                sensor.setCpColor("");
//                                sensor.setCameraId("");
//                                sensor.setCameraName("");
//                                sensor.setPicLink("");
//                                sensor.setVedioTime("");
//                                sensor.setAreaId(2);
//                                sensor.setRecSt(1);
//                                deviceSensorService.save(sensor);
//                            }else{
//                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                if(sensor.getLastSeenTime()==null){
//                                    sensor.setLastSeenTime(new Date());
//                                    sensor.setHappenTime(new Date());
//                                    sensor.setSensorTime(sdf1.format(new Date()));
//                                    deviceSensorService.update(sensor);
//                                }
//                                String cmd = data.substring(20, 22).toUpperCase();
//                                if(cmd.equals("30")){
//                                    String av = data.substring(24, 26);
//                                    Integer avalable = Integer.valueOf(av);
////                                    String dif = data.substring(30, 34);
////                                    String zdif = data.substring(34, 38);
//                                    Date date = new Date();
//                                    if((date.getTime()-sensor.getLastSeenTime().getTime())/1000>300||avalable!=sensor.getAvailable()){
//                                        sensor.setAvailable(avalable);
//                                        sensor.setLastSeenTime(date);
//                                        sensor.setSensorStatus(avalable);
//                                        sensor.setLastSeenTime(new Date());
//                                        sensor.setHappenTime(new Date());
//                                        sensor.setSensorTime(sdf1.format(new Date()));
//                                        sensor.setVedioStatus("");
//                                        sensor.setCph("");
//                                        sensor.setCpColor("");
//                                        sensor.setCameraId("");
//                                        sensor.setPicLink("");
//                                        sensor.setVedioTime("");
//                                        sensor.setSensorStatus(sensor.getAvailable());
//                                        sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
//                                        sensor.setBluetooth("");
//                                        sensor.setBluetoothArray("");
////                                        sensor.setCameraName("");
//                                        deviceSensorService.update(sensor);
//                                        logSensorLogService.saveOperationLog(sensor);
//                                    }
//                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
////                                    File f = new File("/data/logs/sensor_status/"+sdf.format(date)+"/"+mac+".txt");
//                                    File f = new File("/zhanway/logs/sensor_status/"+sdf.format(date)+"/"+mac+".txt");
//                                    File fileParent = f.getParentFile();
//                                    if (!fileParent.exists()) {
//                                        fileParent.mkdirs();
//                                        f.createNewFile();
//                                    }
//                                    FileWriter fw = new FileWriter(f, true);
//                                    PrintWriter pw = new PrintWriter(fw);
//                                    pw.println(mac+"    "+data+"    "+sdf1.format(date));
//                                    pw.flush();
//                                    fw.flush();
//                                    pw.close();
//                                    fw.close();
//                                }
//                                if(cmd.equals("35")){//心跳
//                                   Map<String, Object> map = new HashMap<String, Object>();
//                                   map.put("mac", mac);
//                                   map.put("data",data.substring(16, data.length()).toUpperCase());
//                                   map.put("heart_time", new Date().getTime());
//                                   HttpUtil.postJson("http://backsite.czparking.com/api/heartbeat", JSONObject.toJSONString(map));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            log.warn("");
//        }catch (Exception e){
//            log.error("e:{}",e);
//        }
//        return "{\"status\":\"ok\"}";
//    }

	
	
	  /**
     * 新地磁接入
     * @param bytes
     * @return
     */
    @RequestMapping(value = "/na/iocm/devNotify/v1.1.0/updateDeviceDatas", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String pushs(@RequestBody byte[] bytes){
        try {
            String r = new String(bytes, "utf-8");
            log.warn("telcom datas:{}",r);
            if(StringUtil.isNotBlank(r)){
                TelcomPushDataModel telcomPushDataModel = JSONObject.parseObject(r, TelcomPushDataModel.class);
                if (telcomPushDataModel != null) {
                    List<PushModel> list = telcomPushDataModel.getServices();
                    PushModel pushModel2 = telcomPushDataModel.getService();
                    if (pushModel2 != null) {
                        list.add(pushModel2);
                    }
                    if (list != null && !list.isEmpty()) {
                        for(PushModel pushModel : list){
                            TModel tModel = pushModel.getData();
                            String data = tModel.getData();
                            String mac = data.substring(0, 16);
                            DeviceSensor sensor = deviceSensorService.findByMac(mac);
                            if(sensor==null){
                                sensor = new DeviceSensor();
                                sensor.setAvailable(0);
                                sensor.setLastSeenTime(new Date());
                                sensor.setCreateTime(new Date());
                                sensor.setMac(mac);
                                sensor.setSensorStatus(0);
                                sensor.setVedioStatus("");
                                sensor.setCph("");
                                sensor.setCpColor("");
                                sensor.setCameraId("");
                                sensor.setCameraName("");
                                sensor.setPicLink("");
                                sensor.setVedioTime("");
                                sensor.setAreaId(2);
                                sensor.setRecSt(1);
                                deviceSensorService.save(sensor);
                            }else{
                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                if(sensor.getLastSeenTime()==null){
                                    sensor.setLastSeenTime(new Date());
                                    sensor.setHappenTime(new Date());
                                    sensor.setSensorTime(sdf1.format(new Date()));
                                    deviceSensorService.update(sensor);
                                }
                                String cmd = data.substring(20, 22).toUpperCase();
                                if(cmd.equals("3D")){
                                    String av = data.substring(24, 26);
                                    Integer avalable = Integer.valueOf(av);
                                    String mode = data.substring(26, 28);
//                                    String dif = data.substring(30, 34);
//                                    String zdif = data.substring(34, 38);
                                    Date date = new Date();
                                    if((date.getTime()-sensor.getLastSeenTime().getTime())/1000>300||avalable!=sensor.getAvailable()){
                                    	if(avalable==sensor.getAvailable()){
                                            sensor.setAvailable(Math.abs(avalable-1));
                                            sensor.setLastSeenTime(date);
                                            sensor.setSensorStatus(Math.abs(avalable-1));
                                            sensor.setMode("");
                                            sensor.setHappenTime(date);
                                            sensor.setSensorTime(sdf1.format(date));
                                            sensor.setVedioStatus("");
                                            sensor.setCph("");
                                            sensor.setCpColor("");
                                            sensor.setCameraId("");
                                            sensor.setPicLink("");
                                            sensor.setVedioTime("");
                                            sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
                                            sensor.setBluetooth("");
                                            sensor.setBluetoothArray("");
                                            deviceSensorService.update(sensor);
                                            logSensorLogService.saveOperationLog(sensor);
                                    	}
                                    	date = new Date();
                                        sensor.setAvailable(avalable);
                                        sensor.setLastSeenTime(date);
                                        sensor.setSensorStatus(avalable);
                                        sensor.setMode(String.valueOf(Long.parseLong(mode, 16)));
                                        sensor.setHappenTime(date);
                                        sensor.setSensorTime(sdf1.format(date));
                                        sensor.setVedioStatus("");
                                        sensor.setCph("");
                                        sensor.setCpColor("");
                                        sensor.setCameraId("");
                                        sensor.setPicLink("");
                                        sensor.setVedioTime("");
                                        sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
                                        sensor.setBluetooth("");
                                        sensor.setBluetoothArray("");
                                        deviceSensorService.update(sensor);
                                        logSensorLogService.saveOperationLog(sensor);
                                    }
                                }
                                if(cmd.equals("09")){
                                    String av = data.substring(24, 26);
                                    Integer avalable = Integer.valueOf(av);
                                    String mode = data.substring(26, 28);
                                    Date date = new Date();
                                    if((date.getTime()-sensor.getLastSeenTime().getTime())/1000>300||avalable!=sensor.getAvailable()){
                                    	if(avalable==sensor.getAvailable()){
                                            sensor.setAvailable(Math.abs(avalable-1));
                                            sensor.setLastSeenTime(date);
                                            sensor.setSensorStatus(Math.abs(avalable-1));
                                            sensor.setMode("");
                                            sensor.setHappenTime(date);
                                            sensor.setSensorTime(sdf1.format(date));
                                            sensor.setVedioStatus("");
                                            sensor.setCph("");
                                            sensor.setCpColor("");
                                            sensor.setCameraId("");
                                            sensor.setPicLink("");
                                            sensor.setVedioTime("");
                                            sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
                                            sensor.setBluetooth("");
                                            sensor.setBluetoothArray("");
                                            deviceSensorService.update(sensor);
                                            logSensorLogService.saveOperationLog(sensor);
                                    	}
                                    	date = new Date();
                                        sensor.setAvailable(avalable);
                                        sensor.setLastSeenTime(date);
                                        sensor.setSensorStatus(avalable);
                                        sensor.setMode(String.valueOf(Long.parseLong(mode, 16)));
                                        sensor.setHappenTime(date);
                                        sensor.setSensorTime(sdf1.format(date));
                                        sensor.setVedioStatus("");
                                        sensor.setCph("");
                                        sensor.setCpColor("");
                                        sensor.setCameraId("");
                                        sensor.setPicLink("");
                                        sensor.setVedioTime("");
                                        sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
                                        sensor.setBluetooth("");
                                        sensor.setBluetoothArray("");
                                        deviceSensorService.update(sensor);
                                        logSensorLogService.saveOperationLog(sensor);
                                    }
                                }
                                if(cmd.equals("3E")){//心跳
                                    String dif1 = data.substring(26, 28);
                                    String dif2 = data.substring(24, 26);
                                    String dif = getDataBase(data.substring(26, 27), dif1+dif2);
                                    String av = data.substring(28, 30);
                                    Integer avalable = Integer.valueOf(av);
                                    String bat1 = data.substring(46, 48);
                                    String bat2 = data.substring(44, 46);
                                    String bat = String.valueOf(Double.valueOf(getDataBase(data.substring(46, 47), bat1+bat2))/1000);
                                    String soft = convertHexToString(data.substring(32, 44));
                                    String lt = String.valueOf(Integer.parseInt( data.substring(48, 50),16));
                                    String mode = String.valueOf(Integer.parseInt( data.substring(30, 32),16));
                                    String ht = String.valueOf(Integer.parseInt( data.substring(50, 52),16));
                                    String wl = String.valueOf(Integer.parseInt( data.substring(52, 54),16));
                                    String wh = String.valueOf(Integer.parseInt( data.substring(54, 56),16));
                                    String ang =  String.valueOf(Integer.parseInt( data.substring(56, 58),16));
                                    String wt =  String.valueOf(Integer.parseInt( data.substring(58, 60),16));
                                    String rssi =  String.valueOf(Integer.parseInt( data.substring(66, 68),16));
                                    String sdi =  String.valueOf(Integer.parseInt( data.substring(60, 62),16));
                                    String sdo =  String.valueOf(Integer.parseInt( data.substring(62, 64),16));
                                    String fdi =  String.valueOf(Integer.parseInt( data.substring(64, 66),16));
                                    String pci1 = data.substring(88, 90);
                                    String pci2 = data.substring(86, 88);
                                    String pci = getDataBase(data.substring(88, 89), pci1+pci2);
                                    String rsrp1 = data.substring(80, 82);
                                    String rsrp2 = data.substring(78, 80);
                                    String rsrp =  getDataBase(data.substring(80, 81), rsrp1+rsrp2);
                                    String snr1 = data.substring(84,86);
                                    String snr2 = data.substring(82, 84);
                                    String snr =   getDataBase(data.substring(84, 85), snr1+snr2);
                                    LogSensorHeart deviceLog = new LogSensorHeart();
                                    deviceLog.setCreateTime(new Date());
                                    deviceLog.setBatteryVoltage(bat);
                                    deviceLog.setVol(bat);
                                    deviceLog.setVer(soft);
                                    deviceLog.setDif(dif);
                                    deviceLog.setFdi(fdi);
                                    deviceLog.setMac(mac);
                                    deviceLog.setMode(mode);
                                    deviceLog.setHt(ht);
                                    deviceLog.setState(String.valueOf(avalable));
                                    deviceLog.setLt(lt);
                                    deviceLog.setWlt(wl);
                                    deviceLog.setWht(wh);
                                    deviceLog.setAt(ang);
                                    deviceLog.setWt(wt);
                                    deviceLog.setRssi(rssi);
                                    deviceLog.setRsrp(rsrp);
                                    deviceLog.setSdi(sdi);
                                    deviceLog.setSdo(sdo);
                                    deviceLog.setPci(pci);
                                    deviceLog.setSnr(snr);
                                    deviceLog.setMod(mode);
                                    logSensorLogService.saveHeart(deviceLog);
                                    sensor.setBatteryVoltage(bat);
                                    sensor.setRssi(rssi);
                                    sensor.setAddr(rssi);
                                    sensor.setLastSeenTime(new Date());
                                    if(sensor.getAvailable()!=avalable) {
                                    	Date date = new Date();
                                    	sensor.setAvailable(avalable);
                                        sensor.setLastSeenTime(date);
                                        sensor.setSensorStatus(avalable);
                                        sensor.setMode("");
                                        sensor.setHappenTime(date);
                                        sensor.setSensorTime(sdf1.format(date));
                                        sensor.setVedioStatus("");
                                        sensor.setCph("heart");
                                        sensor.setCpColor("");
                                        sensor.setCameraId("");
                                        sensor.setPicLink("");
                                        sensor.setVedioTime("");
                                        sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
                                        sensor.setBluetooth("");
                                        sensor.setBluetoothArray("");
                                        logSensorLogService.saveOperationLog(sensor);
                                    }
//                                    if (sensor.getAreaId() != null && 1 == sensor.getAreaId()) {
//                            			WuhanSendUtils.sendHeart(deviceLog, sensor);
//                            		}
                                    deviceSensorService.update(sensor);
                                    if("LT".equals(telcomPushDataModel.getGatewayId())) {
                                    	HttpUtil.get("http://139.196.205.157:8090/home/cloud/server/check?id=10");
                                    }
                                }
                                if(cmd.equals("06")){//雷达地磁心跳
                                	LogSensorHeart deviceLog = new LogSensorHeart();
                                    String dif1 = data.substring(26, 28);
                                    String dif2 = data.substring(24, 26);
                                    String dif = getDataBase(data.substring(26, 27), dif1+dif2);
                                    String av = data.substring(28, 30);
                                    Integer avalable = Integer.valueOf(av);
                                    String mode =  String.valueOf(Integer.parseInt( data.substring(30, 32),16));
                                    String soft = convertHexToString(data.substring(32, 44));
                                    String bat1 = data.substring(46, 48);
                                    String bat2 = data.substring(44, 46);
                                    String bat = String.valueOf(Double.valueOf(getDataBase(data.substring(46, 47), bat1+bat2))/1000);
                                    deviceLog.setBatteryVoltage(bat);
                                    deviceLog.setVol(bat);
                                    String lt = String.valueOf(Integer.parseInt( data.substring(48, 50),16));
                                    String ht = String.valueOf(Integer.parseInt( data.substring(50, 52),16));
                                    String wl = String.valueOf(Integer.parseInt( data.substring(52, 54),16));
                                    String wh = String.valueOf(Integer.parseInt( data.substring(54, 56),16));
                                    String ang =  String.valueOf(Integer.parseInt( data.substring(56, 58),16));
                                    String wt =  String.valueOf(Integer.parseInt( data.substring(58, 60),16));
                                    
                                    String angelDif = String.valueOf(Integer.parseInt( data.substring(60, 62),16));//角度值
                                    deviceLog.setAngelDif(angelDif);
                                    String noDif = String.valueOf(Integer.parseInt( data.substring(62, 64),16));//无车门限
                                    deviceLog.setNoDif(noDif);
                                    String yesDif = String.valueOf(Integer.parseInt( data.substring(64, 66),16));//有车门限
                                    deviceLog.setYesDif(yesDif);
                                    String lj = String.valueOf(Integer.parseInt( data.substring(66, 68),16));//雷达覆水检测起始距离
                                    deviceLog.setLj(lj);
                                    String lc = String.valueOf(Integer.parseInt( data.substring(68, 70),16));//雷达覆水检测长度
                                    deviceLog.setLc(lc);
                                    String lm1 = data.substring(70, 72);
                                    String lm2 = data.substring(72, 74);
                                    String lm = getDataBase(data.substring(72, 73), lm2+lm1);//雷达覆水检测门限
                                    deviceLog.setLm(lm);
                                    String lcm1 = data.substring(74, 76);
                                    String lcm2 = data.substring(76, 78);
                                    String lcm = getDataBase(data.substring(76, 77), lcm2+lcm1);//雷达覆水检测窗口门限
                                    deviceLog.setLcm(lcm);
                                    String ljt1 = data.substring(78, 80);
                                    String ljt2 = data.substring(80, 82);
                                    String ljt = getDataBase(data.substring(80, 81), ljt2+ljt1);//雷达覆水检测间隔
                                    deviceLog.setLjt(ljt);
                                    String ljj = String.valueOf(Integer.parseInt( data.substring(82, 84),16));//雷达车检起始距离
                                    deviceLog.setLjc(ljj);
                                    String ljc = String.valueOf(Integer.parseInt( data.substring(84, 86),16));//雷达车检检测长度
                                    deviceLog.setLjc(ljc);
                                    String ljg = String.valueOf(Integer.parseInt( data.substring(86, 88),16));//雷达车检检测距离个数 
                                    deviceLog.setLjg(ljg);
                                    String ljm1 = data.substring(88, 90);
                                    String ljm2 = data.substring(90, 92);
                                    String ljm = getDataBase(data.substring(90,91), ljm2+ljm1);//雷达车检门限值
                                    deviceLog.setLjm(ljm);
                                    String nj = String.valueOf(Integer.parseInt( data.substring(92, 94),16));//哪一段距离触发的车检
                                    deviceLog.setNj(nj);
                                    String ljq1 = data.substring(94, 96);
                                    String ljq2 = data.substring(96, 98);
                                    String ljq = getDataBase(data.substring(96,97), ljq2+ljq1);//雷达车检门限值
                                    deviceLog.setLjq(ljq);
                                    
                                    String rssi =  String.valueOf(Integer.parseInt( data.substring(98, 100),16));
                                    String sdi =  String.valueOf(Integer.parseInt( data.substring(100, 102),16));
                                    String pci1 = data.substring(110, 112);
                                    String pci2 = data.substring(112, 114);
                                    String pci = getDataBase(data.substring(112, 113), pci2+pci1);
                                    String rsrp1 = data.substring(102, 104);
                                    String rsrp2 = data.substring(104, 106);
                                    String rsrp =  getDataBase(data.substring(104, 105), rsrp2+rsrp1);
                                    String snr1 = data.substring(106,108);
                                    String snr2 = data.substring(108, 110);
                                    String snr =   getDataBase(data.substring(108, 109), snr2+snr1);
                                    String err = data.substring(118, 126);//错误码
                                    deviceLog.setErr(err);
                                    deviceLog.setCreateTime(new Date());
                                    deviceLog.setVer(soft);
                                    deviceLog.setDif(dif);
                                    deviceLog.setMac(mac);
                                    deviceLog.setMode(mode);
                                    deviceLog.setHt(ht);
                                    deviceLog.setState(String.valueOf(avalable));
                                    deviceLog.setLt(lt);
                                    deviceLog.setWlt(wl);
                                    deviceLog.setWht(wh);
                                    deviceLog.setAt(ang);
                                    deviceLog.setWt(wt);
                                    deviceLog.setRssi(rssi);
                                    deviceLog.setRsrp(rsrp);
                                    deviceLog.setSdi(sdi);
                                    deviceLog.setPci(pci);
                                    deviceLog.setSnr(snr);
                                    deviceLog.setMod(mode);
                                    logSensorLogService.saveHeart(deviceLog);
                                    sensor.setRssi(rssi);
                                    sensor.setAddr(rssi);
                                    sensor.setLastSeenTime(new Date());
                                    if(sensor.getAvailable()!=avalable) {
                                    	Date date = new Date();
                                    	sensor.setAvailable(avalable);
                                        sensor.setLastSeenTime(date);
                                        sensor.setSensorStatus(avalable);
                                        sensor.setMode("");
                                        sensor.setHappenTime(date);
                                        sensor.setSensorTime(sdf1.format(date));
                                        sensor.setVedioStatus("");
                                        sensor.setCph("heart");
                                        sensor.setCpColor("");
                                        sensor.setCameraId("");
                                        sensor.setPicLink("");
                                        sensor.setVedioTime("");
                                        sensor.setSensorTime(sdf1.format(sensor.getHappenTime()));
                                        sensor.setBluetooth("");
                                        sensor.setBluetoothArray("");
                                        logSensorLogService.saveOperationLog(sensor);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("e:{}",e);
        }
        return "{\"status\":\"ok\"}";
    }

    /**
     * 点新地磁接入
     * @param bytes
     * @return
     */
    @RequestMapping(value = "/na/iocm/devNotify/v1.1.0/updateDeviceData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String push(@RequestBody byte[] bytes){
        try {
            JSONObject json = JSONObject.parseObject(new String(bytes, "utf-8"));
            log.warn("telcom data:{}",json);
        }catch (Exception e){
            log.error("e:{}",e);
        }
        return "{\"status\":\"ok\"}";
    }

    private String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }


    private String getDataBase(String index, String _d) throws Exception {
        int _index = Integer.parseInt(index, 16);
        Integer a = Integer.valueOf(_d, 16);
        String b = Integer.toBinaryString(a);
        String[] arrs = b.split("");
        String[] arr = new String[16];
        int i = 0;
        for (String s : arrs) {
            if (s != null && !"".equals(s)) {
                arr[i] = s;
                i++;
            }
        }
        String c = "";
        Integer e = Integer.parseInt(b, 2);
        if (_index > 8) {
            for (String d : arr) {
                if (d != null && !"".equals(d)) {
                    if (d.equals("1")) {
                        c += "0";
                    } else {
                        c += "1";
                    }
                }
            }
            e = (Integer.parseInt(c, 2) + 1) * -1;
        } else {
            e = Integer.parseInt(_d, 16);
        }

        return String.valueOf(e);
    }
	
}
