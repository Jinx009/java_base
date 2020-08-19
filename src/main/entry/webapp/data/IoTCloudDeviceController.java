package main.entry.webapp.data;


import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import database.models.IoTCloudDevice;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.Resp;
import utils.StreamClosedHttpResponse;

@Controller
@RequestMapping(value = "/iot/device")
public class IoTCloudDeviceController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(IoTCloudDeviceController.class);

	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(Integer p,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.pageList(p,type));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/wuhan")
	@ResponseBody
	public Resp<?> wh(Integer p,Integer type){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.getWuhan());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 近三日失联
	 * @return
	 */
	@RequestMapping(path = "/daysLost")
	@ResponseBody
	public Resp<?> daysLost(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNow = sdf.format(date)+" 00:00:00";
			date = sdf2.parse(dateNow);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, -4);
			Date yestoday4 = c.getTime();
			for(IoTCloudDevice device:list){
				if(device.getDataTime()!=null&&device.getDataTime().before(date)&&device.getDataTime().after(yestoday4)){
					l.add(device);
				}
			}
			return new Resp<>(l);
		}catch(Exception e){
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 转发后失联
	 * @return
	 */
	@RequestMapping(path = "/lost")
	@ResponseBody
	public Resp<?> lost(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNow = sdf.format(date)+" 00:00:00";
			date = sdf2.parse(dateNow);
			for(IoTCloudDevice device:list){
				if(device.getUpdateTime()==null||device.getUpdateTime().before(date)){
					if(device.getLocalIp().indexOf("WUHAN")>-1){
						l.add(device);
					}
				}
			}
			return new Resp<>(l);
		}catch(Exception e){
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 湖北失联
	 * @return
	 */
	@RequestMapping(path = "/hubeiLost")
	@ResponseBody
	public Resp<?> hubeiLost(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNow = sdf.format(date)+" 00:00:00";
			date = sdf2.parse(dateNow);
			for(IoTCloudDevice device:list){
				if(device.getDataTime()==null||device.getDataTime().before(date)){
					if(device.getLocalIp().indexOf("WUHAN")>-1){
						l.add(device);
					}
				}
			}
			return new Resp<>(l);
		}catch(Exception e){
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 四川失联
	 * @return
	 */
	@RequestMapping(path = "/sichuanLost")
	@ResponseBody
	public Resp<?> sichuanLost(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNow = sdf.format(date)+" 00:00:00";
			date = sdf2.parse(dateNow);
			for(IoTCloudDevice device:list){
				if(device.getDataTime()==null||device.getDataTime().before(date)){
					if(device.getLocalIp().indexOf("YIBIN")>-1){
						l.add(device);
					}
				}
			}
			return new Resp<>(l);
		}catch(Exception e){
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 广东失联
	 * @return
	 */
	@RequestMapping(path = "/guangdongLost")
	@ResponseBody
	public Resp<?> guangdongLost(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNow = sdf.format(date)+" 00:00:00";
			date = sdf2.parse(dateNow);
			for(IoTCloudDevice device:list){
				if(device.getDataTime()==null||device.getDataTime().before(date)){
					if(device.getLocalIp().indexOf("GUANGDONG")>-1){
						l.add(device);
					}
				}
			}
			return new Resp<>(l);
		}catch(Exception e){
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 数据量异常
	 * @return
	 */
	@RequestMapping(path = "/dataError")
	@ResponseBody
	public Resp<?> dataError(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			for(IoTCloudDevice device:list){
				if(device.getDataNum()>35){
					l.add(device);
				}
			}
			return new Resp<>(l);
		}catch(Exception e){
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 角度异常
	 * @return
	 */
	@RequestMapping(path = "/angleError")
	@ResponseBody
	public Resp<?> getAngleError(){
		Resp<?> resp = new Resp<>(false);
		try {
			List<IoTCloudDevice> l = new ArrayList<IoTCloudDevice>();
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			for(IoTCloudDevice device:list){
				if(Math.abs(device.getMaxX())>5||Math.abs(device.getMaxY())>5){
					l.add(device);
				}
			}
			return new Resp<>(l);
		}catch(Exception e){
			log.error("e:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/map")
	@ResponseBody
	public Resp<?> map(){
		Resp<?> resp = new Resp<>(false);
		try {
			int beforeDayLostNum = 0;
			int beforeDayLostNum2 = 0;
			int beforeDayLostNum3 = 0;
			int beforeDayLostNum4 = 0;
			int totalLostNum = 0;
			int totalRealLostNum = 0;
			int dayBrokenNum = 0;
			int dayDataNum = 0;
			int deviceTotal = 0;
			int hubeiTotal = 0;
			int sichuanTotal = 0;
			int guangdongTotal = 0;
			int hubeiTotalLost = 0;
			int hubeiTotalReal = 0;
			int sichuanTotalLost = 0;
			int sichuanTotalReal = 0;
			int guangdongTotalLost = 0;
			int guangdongTotalReal = 0;
			double maxAngleX = 0.00;
			double maxAngleY = 0.00;
			List<IoTCloudDevice> list= iotCloudDeviceService.getMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String dateNow = sdf.format(date)+" 00:00:00";
			date = sdf2.parse(dateNow);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, -1);
			Date yestoday = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, -1);
			Date yestoday2 = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, -1);
			Date yestoday3 = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, -1);
			Date yestoday4 = c.getTime();
			Map<String, Object> map = new HashMap<String,Object>();
			for(IoTCloudDevice device:list){
				deviceTotal++;
				if(device.getLocalIp().indexOf("WUHAN")>-1){
					hubeiTotal++;
				}
				if(device.getLocalIp().indexOf("GUANGDONG")>-1){
					guangdongTotal++;
				}
				if(device.getLocalIp().indexOf("YIBIN")>-1){
					sichuanTotal++;
				}
				if(device.getDataTime()!=null&&device.getDataTime().before(date)&&device.getDataTime().after(yestoday)){
					beforeDayLostNum++;
				}
				if(device.getDataTime()!=null&&device.getDataTime().before(yestoday)&&device.getDataTime().after(yestoday2)){
					beforeDayLostNum2++;
				}
				if(device.getDataTime()!=null&&device.getDataTime().before(yestoday2)&&device.getDataTime().after(yestoday3)){
					beforeDayLostNum3++;
				}
				if(device.getDataTime()!=null&&device.getDataTime().before(yestoday3)&&device.getDataTime().after(yestoday4)){
					beforeDayLostNum4++;
				}
				if(device.getDataTime()==null||device.getDataTime().before(date)){
					totalRealLostNum++;
					if(device.getLocalIp().indexOf("WUHAN")>-1){
						hubeiTotalReal++;
					}
					if(device.getLocalIp().indexOf("GUANGDONG")>-1){
						guangdongTotalReal++;
					}
					if(device.getLocalIp().indexOf("YIBIN")>-1){
						sichuanTotalReal++;
					}
				}
				if(device.getDataNum()>35){
					dayDataNum++;
				}
				if(device.getUpdateTime()==null||device.getUpdateTime().before(date)){
					totalLostNum++;
					if(device.getLocalIp().indexOf("WUHAN")>-1){
						hubeiTotalLost++;
					}
					if(device.getLocalIp().indexOf("GUANGDONG")>-1){
						guangdongTotalLost++;
					}
					if(device.getLocalIp().indexOf("YIBIN")>-1){
						sichuanTotalLost++;
					}
				}
				if(Math.abs(device.getMaxX())>5||Math.abs(device.getMaxY())>5){
					dayBrokenNum++;
					if(Math.abs(device.getMaxX())>Math.abs(maxAngleX)){
						maxAngleX = device.getMaxX();
					}
					if(Math.abs(device.getMaxY())>Math.abs(maxAngleY)){
						maxAngleY = device.getMaxY();
					}
				}
			}
			map.put("beforeDayLostNum", beforeDayLostNum);
			map.put("beforeDayLostNum2", beforeDayLostNum2);
			map.put("beforeDayLostNum3", beforeDayLostNum3);
			map.put("totalRealLostNum", totalRealLostNum);
			map.put("dayDataNum", dayDataNum);
			map.put("totalLostNum", totalLostNum);
			map.put("dayBrokenNum", dayBrokenNum);
			map.put("deviceTotal", deviceTotal);
			map.put("sichuanTotal", sichuanTotal);
			map.put("guangdongTotal", guangdongTotal);
			map.put("hubeiTotal", hubeiTotal);
			map.put("beforeDayLostNum4", beforeDayLostNum4);
			map.put("sichuanTotalLost", sichuanTotalLost);
			map.put("sichuanTotalReal", sichuanTotalReal);
			map.put("hubeiTotalLost", hubeiTotalLost);
			map.put("hubeiTotalReal", hubeiTotalReal);
			map.put("guangdongTotalLost", guangdongTotalLost);
			map.put("guangdongTotalReal", guangdongTotalReal);
			map.put("maxAngleX", maxAngleX);
			map.put("maxAngleY", maxAngleY);
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/broken")
	@ResponseBody
	public Resp<?> broken(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.getBroken());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/localIp")
	@ResponseBody
	public Resp<?> type(String localIp){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(iotCloudDeviceService.findByLocalIp(localIp) );
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 设备注册
	 * 
	 * @param imei
	 * @param mac
	 * @param ipLocal
	 * @return
	 */
	@SuppressWarnings({ "resource", "deprecation" })
	@RequestMapping(path = "/register_local")
	@ResponseBody
	public Resp<?> register_local() {
		Resp<?> resp = new Resp<>(false);
		try {
			HSSFWorkbook work = new HSSFWorkbook(new FileInputStream("/Users/jinx/Downloads/test.xls"));// 得到这个excel表格对象
			HSSFSheet sheet = work.getSheetAt(0); //得到第一个sheet
			int rowNo = sheet.getLastRowNum(); //得到行数
			for (int i = 1; i <= rowNo; i++) {
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell1 = row.getCell((short) 0);
				HSSFCell cell2 = row.getCell((short) 1);
				HSSFCell cell3 = row.getCell((short) 2);
				HSSFCell cell4 = row.getCell((short) 3);
				HSSFCell cell5 = row.getCell((short) 4);
				cell1.setCellType(Cell.CELL_TYPE_STRING);
				cell2.setCellType(Cell.CELL_TYPE_STRING);
				cell3.setCellType(Cell.CELL_TYPE_STRING);
				cell4.setCellType(Cell.CELL_TYPE_STRING);
				cell5.setCellType(Cell.CELL_TYPE_STRING);
				String imei = String.valueOf(cell1.getStringCellValue());
				String mac =String.valueOf(cell2.getStringCellValue());
				String name =String.valueOf(cell3.getStringCellValue());
				String card = String.valueOf(cell4.getStringCellValue());
				String local = String.valueOf(cell5.getStringCellValue());
				HttpsUtil httpsUtil = new HttpsUtil();
				httpsUtil.initSSLConfigForTwoWay();
				String accessToken = login(httpsUtil);
				String appId = Constant.APPID;
				String urlReg = Constant.REGISTER_DEVICE;
				String verifyCode = imei;
				String nodeId = verifyCode;
				Integer timeout = 0;
				Map<String, Object> paramReg = new HashMap<>();
				paramReg.put("verifyCode", verifyCode.toUpperCase());
				paramReg.put("nodeId", nodeId.toUpperCase());
				paramReg.put("timeout", timeout);
				String jsonRequest = JsonUtil.jsonObj2Sting(paramReg);
				Map<String, String> header = new HashMap<>();
				header.put(Constant.HEADER_APP_KEY, appId);
				header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
				StreamClosedHttpResponse responseReg = httpsUtil.doPostJsonGetStatusLine(urlReg, header, jsonRequest);
				JSONObject jsonObject = JSONObject.parseObject(responseReg.getContent());
				log.warn("regInfo------:{}", jsonObject);
				String deviceId = jsonObject.getString("deviceId");
				httpsUtil = new HttpsUtil();
				httpsUtil.initSSLConfigForTwoWay();
				String accessToken2 = login(httpsUtil);
				String urlModifyDeviceInfo = Constant.MODIFY_DEVICE_INFO + "/" + deviceId;
				String manufacturerId = "Zhanway";
				String manufacturerName = "Zhanway";
				String deviceType = "SmartDevice";
				String model = "ZWMNB01";
				String protocolType = "CoAP";
				Map<String, Object> paramModifyDeviceInfo = new HashMap<>();
				paramModifyDeviceInfo.put("manufacturerId", manufacturerId);
				paramModifyDeviceInfo.put("manufacturerName", manufacturerName);
				paramModifyDeviceInfo.put("deviceType", deviceType);
				paramModifyDeviceInfo.put("model", model);
				paramModifyDeviceInfo.put("name", name);
				paramModifyDeviceInfo.put("protocolType", protocolType);
				String jsonRequest2 = JsonUtil.jsonObj2Sting(paramModifyDeviceInfo);
				Map<String, String> header2 = new HashMap<>();
				header2.put(Constant.HEADER_APP_KEY, appId);
				header2.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken2);
				StreamClosedHttpResponse responseModifyDeviceInfo = httpsUtil.doPutJsonGetStatusLine(urlModifyDeviceInfo,
						header2, jsonRequest2);
				jsonObject = JSONObject.parseObject(responseModifyDeviceInfo.getContent());
				log.warn("ModifyDeviceInfo-------:{}", jsonObject);
				IoTCloudDevice ioTCloudDevice = new IoTCloudDevice();
				ioTCloudDevice.setCreateTime(new Date());
				ioTCloudDevice.setImei(imei);
				ioTCloudDevice.setLocalIp(local);
				ioTCloudDevice.setMac(mac);
				ioTCloudDevice.setType(2);
				ioTCloudDevice.setSimCard(card);
				ioTCloudDevice.setDeviceId(deviceId);
				iotCloudDeviceService.save(ioTCloudDevice);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	
	@SuppressWarnings({ "deprecation", "resource" })
	public static void main(String[] args) {
		try {
			HSSFWorkbook work = new HSSFWorkbook(new FileInputStream("/Users/jinx/Downloads/test.xls"));// 得到这个excel表格对象
			HSSFSheet sheet = work.getSheetAt(0); //得到第一个sheet
			int rowNo = sheet.getLastRowNum(); //得到行数
			System.out.println(rowNo);
			for (int i = 1; i <=rowNo; i++) {
				HSSFRow row = sheet.getRow(i);
				HSSFCell cell1 = row.getCell((short) 0);
				HSSFCell cell2 = row.getCell((short) 1);
				HSSFCell cell3 = row.getCell((short) 2);
				HSSFCell cell4 = row.getCell((short) 3);
				HSSFCell cell5 = row.getCell((short) 4);
				cell1.setCellType(Cell.CELL_TYPE_STRING);
				cell2.setCellType(Cell.CELL_TYPE_STRING);
				cell3.setCellType(Cell.CELL_TYPE_STRING);
				cell4.setCellType(Cell.CELL_TYPE_STRING);
				cell5.setCellType(Cell.CELL_TYPE_STRING);
				String imei = String.valueOf(cell1.getStringCellValue());
				String mac =String.valueOf(cell2.getStringCellValue());
				String name =String.valueOf(cell3.getStringCellValue());
				String card = String.valueOf(cell4.getStringCellValue());
				String local = String.valueOf(cell5.getStringCellValue());
				System.out.println(imei+"-"+mac+"-"+name+"-"+card+"-"+local);
			}
		} catch (Exception e) {
			log.error("e:{}",e);
		}
	}
	
	
}
