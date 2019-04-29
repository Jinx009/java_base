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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
			Integer maxId = parkInfoService.getMaxBaseId();
			if(maxId==null||maxId==0){
				maxId = 233;
			}
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "v_admin", "v_admin");
			c.setAutoCommit(false);
			log.warn("Connect Sql Success!");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from v_parking_info where id>" + maxId );
			while (rs.next()) {
				ParkInfo parkInfo = new ParkInfo();
				int id = rs.getInt("id");
				parkInfo.setBaseId(id);
				String sLocation = rs.getString("slocation");
				parkInfo.setSLocation(sLocation);
				String sCameraName = rs.getString("scameraname");
				parkInfo.setSCameraName(sCameraName);
				String sCameraIndex = rs.getString("scameraindex");
				parkInfo.setSCameraIndex(sCameraIndex);
				int iVehicleEnterstate = rs.getInt("ivehicleenterstate");
				parkInfo.setIVehicleEnterstate(iVehicleEnterstate);
				if(iVehicleEnterstate==2){
					parkInfo.setIVehicleEnterstate(0);
				}
				String sParkingid = rs.getString("sparkingid");
				parkInfo.setSParkingid(sParkingid);
				String tEventTime = rs.getString("teventtime");
				parkInfo.setTEventTime(tEventTime);
				parkInfo.setCreateTime(new Date());
				String sPlateNo = rs.getString("splateno");
				parkInfo.setSPlateNo(sPlateNo);
				String sPlateColor = rs.getString("splatecolor");
				String pColor = "其他颜色";
				if("0".equals(sPlateColor)){
					pColor = "白色";
				}
				if("1".equals(sPlateColor)){
					pColor = "黄色";
				}
				if("2".equals(sPlateColor)){
					pColor = "蓝色";
				}
				if("3".equals(sPlateColor)){
					pColor = "黑色";
				}
				if("5".equals(sPlateColor)){
					pColor = "绿色";
				}
				parkInfo.setSPlateColor(pColor);
				String sVehicleColor = rs.getString("svehiclecolor");
				String color = "未识别";
				if("1".equals(sVehicleColor)){
					color = "白色";
				}
				if("4".equals(sVehicleColor)){
					color = "黑色";
				}
				if("5".equals(sVehicleColor)){
					color = "红色";
				}
				if("7".equals(sVehicleColor)){
					color = "蓝色";
				}
				if("2".equals(sVehicleColor)){
					color = "银色";
				}
				if("3".equals(sVehicleColor)){
					color = "灰色";
				}
				if("8".equals(sVehicleColor)){
					color = "黄色";
				}
				if("10".equals(sVehicleColor)){
					color = "棕色";
				}
				if("11".equals(sVehicleColor)){
					color = "粉色";
				}
				if("12".equals(sVehicleColor)){
					color = "紫色";
				}
				if("6".equals(sVehicleColor)){
					color = "深蓝";
				}
				if("9".equals(sVehicleColor)){
					color = "绿色";
				}
				parkInfo.setSVehicleColor(color);
				String sWholeSenceUrl = rs.getString("swholesenceurl");
				parkInfo.setSWholeSenceUrl(sWholeSenceUrl);
				String sFutrureUrl = rs.getString("sfutrureurl");
				parkInfo.setSFutrureUrl(sFutrureUrl);
				log.warn("id:{}", id);
				parkInfoService.save(parkInfo);
				Date date = new Date();
				String dirPath = sdf.format(date);
				ParkingSpace parkingSpace = parkingSpaceService.getByCameraNameAndParkNumber(parkInfo.getSCameraName(),parkInfo.getSParkingid());
				String picPath = dirPath+"/"+parkingSpace.getMac()+"_"+iVehicleEnterstate+"_"+date.getTime();
				savePic(sWholeSenceUrl, picPath+".jpeg");
				Map<String,String> map = new HashMap<>();
				map.put("mac", parkingSpace.getMac());
				map.put("ChangeTime", sdf3.format(sdf2.parse(tEventTime)));
				map.put("cameraId", sCameraIndex);
				map.put("cph",sPlateNo);
				map.put("cpColor", sPlateColor);
				map.put("status", String.valueOf(parkInfo.getIVehicleEnterstate()));
				map.put("picLink","http://58.246.184.99:801/" +picPath);
				if(iVehicleEnterstate!=0){
					HttpUtil.postJson("http://112.64.46.113:8102/iot/iot/sensor/vedioReport", JSONObject.toJSONString(map));
				}
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
