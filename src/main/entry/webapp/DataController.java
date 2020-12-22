package main.entry.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import database.models.device.DeviceSensor;
import database.models.home.HomeUser;
import service.basicFunctions.business.InvoiceService;
import service.basicFunctions.business.NoticeService;
import service.basicFunctions.device.DeviceSensorService;
import service.basicFunctions.home.HomeUserService;
import utils.Constant;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.StreamClosedHttpResponse;
import utils.StringUtil;
import utils.model.BaseConstant;
import utils.model.Resp;

@Controller
@RequestMapping(value = "/data")
public class DataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(DataController.class);

	@Autowired
	private HomeUserService homeUserService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private DeviceSensorService deviceSensorService;
	
	
	/**
	 * 电信设备注册
	 * 
	 * @param imei
	 * @param mac
	 * @param ipLocal
	 * @return
	 */
	@RequestMapping(path = "/register")
	@ResponseBody
	public Resp<?> register(String imei, String mac,String name, String simCard) {
		Resp<?> resp = new Resp<>(false);
		try {
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
			DeviceSensor sensor = new DeviceSensor();
            sensor.setImei(imei);
            sensor.setMac(mac);
            sensor.setSimCard(simCard);
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
            sensor.setAreaId(66);
            sensor.setRecSt(1);
            deviceSensorService.save(sensor);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	
	

	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String username, String pwd, HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = homeUserService.login(username, pwd);
			if (homeUser != null) {
				setSessionHomeUser(homeUser, request);
				return new Resp<>(true);
			} else {
				return new Resp<>(BaseConstant.NOT_VALIDATE_COE, BaseConstant.NOT_VALIDATE_MSG, null);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/notice")
	@ResponseBody
	public Resp<?> notcie() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(noticeService.findAll());
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/invoice")
	@ResponseBody
	public Resp<?> invoice() {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(invoiceService.findOne());
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/loginOut")
	@ResponseBody
	public Resp<?> loginOut(String username, String pwd, HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
			setSessionNull(request);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	@RequestMapping(path = "/download/log")
	public void down(String fileName, HttpServletRequest request, HttpServletResponse response) {
		InputStream inputStream = null;
		try {
			String fullName = StringUtil.add(BaseConstant.DEVICE_LOG_FILE_PATH, fileName);
			File file = new File(fullName);
			if (!file.exists()){
				log.error("error: file not found!");
				return;
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", StringUtil.add("attachment;fileName=", fileName));

			inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (Exception e) {
			log.error("error:{}", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.error("error:{}", e);
			} finally {
				inputStream = null;
			}
		}  
	}

	@RequestMapping(path = "/uploadFile")
	@ResponseBody
	public Resp<?> uploadFileHandler(@RequestParam("file") MultipartFile file) {
		Resp<?> resp = new Resp<>(false);
		InputStream in = null;
		OutputStream out = null;
		try {
			if (!file.isEmpty()) {
				String filePath =  StringUtil.add(BaseConstant.BASE_DERICTORY_NAME,file.getOriginalFilename());
				log.warn("file path:{}",filePath);
				File serverFile = new File(filePath);
				in = file.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					out.write(b, 0, len);
				}
				out.close();
				in.close();
				resp = new Resp<>(true);
				resp.setMsg("上传成功！");
			} else {
				resp.setMsg("未找到文件！");
			}
		} catch (Exception e) {
			log.error("upload error:{}", e);
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				log.error("close error:{}", e);
			}
		}
		return resp;
	}

}
