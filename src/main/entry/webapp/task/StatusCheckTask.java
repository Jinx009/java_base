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
import database.models.ParkingVedio;
import service.basicFunctions.ParkInfoService;
import service.basicFunctions.ParkingSpaceService;
import service.basicFunctions.ParkingVedioService;
import utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy(value = false)
public class StatusCheckTask {

	private static final Logger log = LoggerFactory.getLogger(StatusCheckTask.class);

	@Autowired
	private ParkInfoService parkInfoService;
	@Autowired
	private ParkingSpaceService parkingSpaceService;
	@Autowired
	private ParkingVedioService parkingVedioService;

	@Scheduled(cron = "0/60 * * * * ? ") // 每1分钟
	public void getVedio() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByTime();
			if (list != null && !list.isEmpty()) {
				for (ParkingVedio parkingVedio : list) {
					saveVedio(parkingVedio);
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	public static void main(String[] args) {
		// savePic("http://10.0.0.17/ISAPI/Traffic/ContentMgmt/Picture/ch00_00000000000003190076001047112",
		// "20190429/1.jpeg");
		ParkingVedio parkingVedio = new ParkingVedio();
		parkingVedio.setCameraIndex("19042811061310409505");
		parkingVedio.setChangeTime("20190430091813");
		parkingVedio.setCreateTime(new Date());
		parkingVedio.setEventTime("20190430091813");
		parkingVedio.setType(1);
		parkingVedio.setMac("00011806140000A");
		parkingVedio.setId(1);
		saveVedio(parkingVedio);
	}

	private static void saveVedio(ParkingVedio parkingVedio) {
		Connection c = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
			PreparedStatement pstmt;
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "v_admin", "v_admin");
			c.setAutoCommit(false);

			Date date = sdf2.parse(parkingVedio.getEventTime());
			Date beginTime = new Date(date.getTime() - 30000);
			Date endTime = new Date(date.getTime() + 30000);
			Date date2 = sdf2.parse(parkingVedio.getChangeTime());
			String filePath = "ftp://10.0.0.1.11/" + sdf3.format(date2) + "/" + parkingVedio.getMac() + "_"
					+ parkingVedio.getChangeTime() + "_";
			if (parkingVedio.getType() == 0) {
				filePath += "_outCarVideo.mp4";
			} else {
				filePath += "_inCarVideo.mp4";
			}
			String sql = "insert into record_download_info (" + "iserialno," + "tcreatetime," + "scameraindex,"
					+ "splateno," + "tbegintime," + "tendtime," + "sfilesavepath," + "sftpusername" + ",sftppassword,"
					+ "tupdatetime," + "iuploadstatus," + "suploadmsg," + "ifilesize) " + "values(" + ""
					+ parkingVedio.getId() + "," + "'" +sdf.format(new Date())+ "'," + "'" + parkingVedio.getCameraIndex()
					+ "'," + "''," + "'" + sdf.format(beginTime)+ "'," + "'" +sdf.format(endTime) + "'," + "'" + filePath
					+ "'," + "'ftp_user1'," + "'"
					+ Base64.getEncoder().encodeToString("Zhanway2017".getBytes(StandardCharsets.UTF_8)) + "'," + "'"
					+ sdf.format(new Date())+ "'," + "0," + "''," + "0)";
			log.warn("sql:{}", sql);
			int i = 0;  
			pstmt = c.prepareStatement(sql);
			i = pstmt.executeUpdate();
			c.commit();
			log.warn("num:{}",i);
			pstmt.close();
			c.close();
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	@Scheduled(cron = "0/3 * * * * ? ") // 每三秒
	public void init() {
		Connection c = null;
		Statement stmt = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
			Integer maxId = parkInfoService.getMaxBaseId();
			if (maxId == null || maxId == 0) {
				maxId = 233;
			}
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "v_admin", "v_admin");
			c.setAutoCommit(false);
			log.warn("Connect Sql Success!");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from v_parking_info where id>" + maxId);
			while (rs.next()) {
				int id = rs.getInt("id");
				String sLocation = rs.getString("slocation");
				String sCameraName = rs.getString("scameraname");
				String sCameraIndex = rs.getString("scameraindex");
				int iVehicleEnterstate = rs.getInt("ivehicleenterstate");
				String sParkingid = rs.getString("sparkingid");
				String tEventTime = rs.getString("teventtime");
				String sPlateNo = rs.getString("splateno");
				String sPlateColor = rs.getString("splatecolor");
				String sVehicleColor = rs.getString("svehiclecolor");
				String sWholeSenceUrl = rs.getString("swholesenceurl");
				String sFutrureUrl = rs.getString("sfutrureurl");
				ParkInfo parkInfo = saveParkInfo(id, sLocation, sCameraName, sCameraIndex, iVehicleEnterstate,
						sParkingid, tEventTime, sPlateNo, sPlateColor, sVehicleColor, sWholeSenceUrl, sFutrureUrl);
				Date date = sdf2.parse(tEventTime);
				String ChangeTime = sdf3.format(date);
				String dirPath = sdf.format(date);
				ParkingSpace parkingSpace = parkingSpaceService.getByCameraNameAndParkNumber(parkInfo.getSCameraName(),
						parkInfo.getSParkingid());
				parkInfo.setMac(parkingSpace.getMac());
				parkInfoService.update(parkInfo);
				parkingSpace.setCameraNumber(sCameraName);
				parkingSpace.setStatus(parkInfo.getIVehicleEnterstate());
				String picPath = dirPath + "/" + parkingSpace.getMac() + "_";
				ParkingVedio parkingVedio = new ParkingVedio();
				parkingVedio.setCameraIndex(sCameraIndex);
				parkingVedio.setEventTime(ChangeTime);
				if (iVehicleEnterstate == 1) {
					parkingSpace.setHappenTime(date);
					parkingVedio.setChangeTime(ChangeTime);
					picPath += ChangeTime;
					savePic(sWholeSenceUrl, picPath + "_inCarImg.jpeg");
				}
				if (iVehicleEnterstate == 2) {
					picPath += sdf3.format(parkingSpace.getHappenTime());
					parkingVedio.setChangeTime(sdf3.format(parkingSpace.getHappenTime()));
					savePic(sWholeSenceUrl, picPath + "_outCarImg.jpeg");
				}
				parkingVedio.setEventTime(ChangeTime);
				parkingVedio.setMac(parkInfo.getMac());
				parkingVedio.setType(parkInfo.getIVehicleEnterstate());
				parkingVedioService.save(parkingVedio);
				sendData(parkingSpace, ChangeTime, sCameraIndex, sPlateNo, sPlateColor, parkInfo, picPath,
						iVehicleEnterstate);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 发送数据
	 * 
	 * @param parkingSpace
	 * @param ChangeTime
	 * @param sCameraIndex
	 * @param sPlateNo
	 * @param sPlateColor
	 * @param parkInfo
	 * @param picPath
	 * @param iVehicleEnterstate
	 */
	private void sendData(ParkingSpace parkingSpace, String ChangeTime, String sCameraIndex, String sPlateNo,
			String sPlateColor, ParkInfo parkInfo, String picPath, int iVehicleEnterstate) {
		Map<String, String> map = new HashMap<>();
		map.put("mac", parkingSpace.getMac());
		map.put("ChangeTime", ChangeTime);
		map.put("cameraId", sCameraIndex);
		map.put("cph", sPlateNo);
		map.put("cpColor", sPlateColor);
		map.put("status", String.valueOf(parkInfo.getIVehicleEnterstate()));
		map.put("picLink", "http://58.246.184.99:801/" + picPath);
		if (iVehicleEnterstate != 0) {
			HttpUtil.postJson("http://112.64.46.113:8102/iot/iot/sensor/vedioReport", JSONObject.toJSONString(map));
		}
	}

	/**
	 * 保存记录
	 * 
	 * @param id
	 * @param sLocation
	 * @param sCameraName
	 * @param sCameraIndex
	 * @param iVehicleEnterstate
	 * @param sParkingid
	 * @param tEventTime
	 * @param sPlateNo
	 * @param sPlateColor
	 * @param sVehicleColor
	 * @param sWholeSenceUrl
	 * @param sFutrureUrl
	 * @return
	 */
	private ParkInfo saveParkInfo(int id, String sLocation, String sCameraName, String sCameraIndex,
			int iVehicleEnterstate, String sParkingid, String tEventTime, String sPlateNo, String sPlateColor,
			String sVehicleColor, String sWholeSenceUrl, String sFutrureUrl) {
		ParkInfo parkInfo = new ParkInfo();
		parkInfo.setBaseId(id);
		parkInfo.setSLocation(sLocation);
		parkInfo.setSCameraName(sCameraName);
		parkInfo.setSCameraIndex(sCameraIndex);
		parkInfo.setIVehicleEnterstate(iVehicleEnterstate);
		if (iVehicleEnterstate == 2) {
			parkInfo.setIVehicleEnterstate(0);
		}
		parkInfo.setSParkingid(sParkingid);
		parkInfo.setTEventTime(tEventTime);
		parkInfo.setCreateTime(new Date());
		parkInfo.setSPlateNo(sPlateNo);
		String pColor = "其他颜色";
		if ("0".equals(sPlateColor)) {
			pColor = "白色";
		}
		if ("1".equals(sPlateColor)) {
			pColor = "黄色";
		}
		if ("2".equals(sPlateColor)) {
			pColor = "蓝色";
		}
		if ("3".equals(sPlateColor)) {
			pColor = "黑色";
		}
		if ("5".equals(sPlateColor)) {
			pColor = "绿色";
		}
		parkInfo.setSPlateColor(pColor);
		String color = "未识别";
		if ("1".equals(sVehicleColor)) {
			color = "白色";
		}
		if ("4".equals(sVehicleColor)) {
			color = "黑色";
		}
		if ("5".equals(sVehicleColor)) {
			color = "红色";
		}
		if ("7".equals(sVehicleColor)) {
			color = "蓝色";
		}
		if ("2".equals(sVehicleColor)) {
			color = "银色";
		}
		if ("3".equals(sVehicleColor)) {
			color = "灰色";
		}
		if ("8".equals(sVehicleColor)) {
			color = "黄色";
		}
		if ("10".equals(sVehicleColor)) {
			color = "棕色";
		}
		if ("11".equals(sVehicleColor)) {
			color = "粉色";
		}
		if ("12".equals(sVehicleColor)) {
			color = "紫色";
		}
		if ("6".equals(sVehicleColor)) {
			color = "深蓝";
		}
		if ("9".equals(sVehicleColor)) {
			color = "绿色";
		}
		parkInfo.setSVehicleColor(color);
		parkInfo.setSWholeSenceUrl(sWholeSenceUrl);
		parkInfo.setSFutrureUrl(sFutrureUrl);
		log.warn("id:{}", id);
		return parkInfoService.save(parkInfo);
	}

	/**
	 * 保存图片
	 * 
	 * @param picPathUrl
	 * @param path
	 */
	private static void savePic(String picPathUrl, String path) {
		byte[] btImg = getImageFromNetByUrl(picPathUrl);
		if (null != btImg && btImg.length > 0) {
			writeImageToDisk(btImg, path);
		} else {
			log.warn("no things");
		}
	}

	/**
	 * 写入硬盘
	 * 
	 * @param data
	 * @param fileName
	 */
	public static void writeImageToDisk(byte[] data, String fileName) {
		try {
			File file = new File("/data/ftp_pic/" + fileName); // 本地目录
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
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

	/**
	 * 获取远程视频流
	 * 
	 * @param strUrl
	 * @return
	 */
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

	/**
	 * 流写入
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
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

}