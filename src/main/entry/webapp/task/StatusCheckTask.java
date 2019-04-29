package main.entry.webapp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import database.models.ParkInfo;
import database.models.ParkingSpace;
import service.basicFunctions.ParkInfoService;
import service.basicFunctions.ParkingSpaceService;
import utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@Lazy(value = false)
public class StatusCheckTask {

	private static final Logger log = LoggerFactory.getLogger(StatusCheckTask.class);

	@Autowired
	private ParkInfoService parkInfoService;
	@Autowired
	private ParkingSpaceService parkingSpaceService;

	@Scheduled(cron = "0/3 * * * * ? ") // 每三秒
	public void init() {
		Connection c = null;
		Statement stmt = null;
		try {
			Integer maxId = parkInfoService.getMaxBaseId();
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "gv", "Hik12345");
			c.setAutoCommit(false);
			log.warn("Connect Sql Success!");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from v_parking_info where id>" + maxId);
			while (rs.next()) {
				ParkInfo parkInfo = new ParkInfo();
				int id = rs.getInt("id");
				parkInfo.setBaseId(id);
				String sLocation = rs.getString("sLocation");
				parkInfo.setSLocation(sLocation);
				String sCameraName = rs.getString("sCameraName");
				parkInfo.setSCameraName(sCameraName);
				String sCameraIndex = rs.getString("sCameraIndex");
				parkInfo.setSCameraIndex(sCameraIndex);
				int iVehicleEnterstate = rs.getInt("iVehicleEnterstate");
				parkInfo.setIVehicleEnterstate(iVehicleEnterstate);
				String sParkingid = rs.getString("sParkingid");
				parkInfo.setSParkingid(sParkingid);
				String tEventTime = rs.getString("tEventTime");
				parkInfo.setTEventTime(tEventTime);
				parkInfo.setCreateTime(new Date());
				String sPlateNo = rs.getString("sPlateNo");
				parkInfo.setSPlateNo(sPlateNo);
				String sPlateColor = rs.getString("sPlateColor");
				parkInfo.setSPlateColor(sPlateColor);
				String sVehicleColor = rs.getString("sVehicleColor");
				parkInfo.setSVehicleColor(sVehicleColor);
				String sWholeSenceUrl = rs.getString("sWholeSenceUrl");
				parkInfo.setSWholeSenceUrl(sWholeSenceUrl);
				String sFutrureUrl = rs.getString("sFutrureUrl");
				parkInfo.setSFutrureUrl(sFutrureUrl);
				log.warn("id:{}", id);
				parkInfoService.save(parkInfo);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String dirPath = sdf.format(date);
				ParkingSpace parkingSpace = parkingSpaceService.getByCameraNameAndParkNumber(parkInfo.getSCameraName(),parkInfo.getSParkingid());
				String picPath = dirPath+"/"+parkingSpace.getMac()+"_"+iVehicleEnterstate+"_"+date.getTime();
				savePic(sWholeSenceUrl, picPath+".jpeg");
				Map<String,String> map = new HashMap<>();
				map.put("mac", parkingSpace.getMac());
				map.put("cameraTime", sdf3.format(sdf2.parse(tEventTime)));
				map.put("cameraId", sCameraIndex);
				map.put("cph",sPlateNo);
				map.put("cpColor", sPlateColor);
				map.put("status", String.valueOf(iVehicleEnterstate));
				map.put("picLink", picPath);
				HttpUtil.postJson("http://124.74.252.162:1122/iot/vedio/dataSend", JSONObject.toJSONString(map));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	private static void savePic(String picPathUrl,String path) {
		byte[] btImg = getImageFromNetByUrl(picPathUrl);
		if (null != btImg && btImg.length > 0) {
			writeImageToDisk(btImg, path);
		} else {
			log.warn("no things");
		}
	}

	public static void writeImageToDisk(byte[] data, String fileName) {
		try {
			File file = new File("/data/ftp_pic/"+fileName); // 本地目录
			File fileParent = file.getParentFile();  
			if(!fileParent.exists()){  
			    fileParent.mkdirs(); 
			    file.createNewFile();
			} 
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(data);
			fops.flush();
			fops.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();
			byte[] btData = readInputStream(inStream);
			return btData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public static void main(String[] args) {
//		Connection c = null;
//		Statement stmt = null;
//		try {
//			Class.forName("org.postgresql.Driver");
//			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "gv", "Hik12345");
//			c.setAutoCommit(false);
//			log.warn("Connect Sql Success!");
//			stmt = c.createStatement();
//
//			ResultSet rs = stmt.executeQuery("select * from device_type_code");
//			while (rs.next()) {
//				String id = rs.getString("name");
//				log.warn("name:{}", id);
//			}
//			rs.close();
//			stmt.close();
//			c.close();
//		} catch (Exception e) {
//			log.error("e:{}", e);
//		}
		savePic("http://10.0.0.17/ISAPI/Traffic/ContentMgmt/Picture/ch00_00000000000003190076001047112", "20190429/1.jpeg");
	}

}
