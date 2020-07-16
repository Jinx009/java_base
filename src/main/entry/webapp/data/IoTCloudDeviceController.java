package main.entry.webapp.data;


import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
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
