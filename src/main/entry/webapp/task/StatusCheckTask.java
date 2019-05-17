package main.entry.webapp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import java.sql.PreparedStatement;

import database.models.ParkInfo;
import database.models.ParkingSpace;
import database.models.ParkingVedio;
import service.basicFunctions.ParkInfoService;
import service.basicFunctions.ParkingSpaceService;
import service.basicFunctions.ParkingVedioService;
import utils.GifUtils;
import utils.HttpUtil;
import utils.PicUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
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

	/**
	 * 文件夹权限问题
	 */
	@Scheduled(cron = "0 1 0 * * ?") // 每天晚上0点01分创建新文件夹
	public void chmod() {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String data = "create_file";
			File file = new File("/data/ftp_pic/" + sdf.format(date) + "/create_file.txt"); // 本地目录
			File fileParent = file.getParentFile();
			if (!fileParent.exists()) {
				fileParent.mkdirs();
				file.createNewFile();
			}
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(data.getBytes());
			fops.flush();
			fops.close();
			String[] cmd = { "sh", "-c", "chmod 777 /data/ftp_pic/*" };
			log.warn("log:chmod dir");
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			p.destroy();
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

	/**
	 * 录制视频
	 */
	@Scheduled(cron = "0/60 * * * * ? ") // 每1分钟
	public void getVedio() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
			if (list != null && !list.isEmpty()) {
				for (ParkingVedio parkingVedio : list) {
					saveVedio(parkingVedio);
					Date date = sdf2.parse(parkingVedio.getEventTime());
					String filePath = "/data/ftp_pic/" + sdf.format(date) + "/" + parkingVedio.getMac() + "_"
							+ parkingVedio.getChangeTime();
					if (parkingVedio.getType() == 0) {
						filePath += "_outCarVideo_.mp4";
					} else if (parkingVedio.getType() == 1){
						filePath += "_inCarVideo_.mp4";
					}else if (parkingVedio.getType() == 2){
						filePath += "_steadyCarImg_.mp4";
					}
					parkingVedio.setFilePath(filePath);
					parkingVedio.setUpdateStatus(0);
					parkingVedio.setSendStatus(1);
					parkingVedio.setSendTime(new Date());
					parkingVedioService.update(parkingVedio);
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 转换视频格式
	 */
	@Scheduled(cron = "0/59 * * * * ? ") // 每1分钟
	public void updateVedio() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByStatus();
			if (list != null && !list.isEmpty()) {
				for (ParkingVedio pv : list) {
					//log.warn("vedio path:{}", pv.getFilePath());
					File file = new File(pv.getFilePath());
					String fileName = pv.getFilePath().split("_.mp4")[0];
					File file2 = new File(fileName+".mp4");
					if (file.exists() && !file2.exists()) {
						GifUtils.covMp4(pv.getFilePath());
					}
					if (file2.exists()) {
						if(pv.getVedioStatus()==1){
							pv.setUpdateStatus(1);
							parkingVedioService.update(pv);
							if(pv.getType()==1){
								GifUtils.covPic(fileName,"00:00:21",fileName.split("_inCarVideo")[0]+"_inCarImg.jpeg");
							}
							if(pv.getType()==0){
								GifUtils.covPic(fileName,"00:00:21",fileName.split("_outCarVideo")[0]+"_outCarImg.jpeg");
							}
						}
						if(pv.getVedioStatus()==2){//停稳截取第一帧
							String picPath = fileName.split("_steadyCarImg")[0]+"_step4.jpeg";
							File picFile = new File(picPath);
							if(!picFile.exists()){
								GifUtils.covPic(fileName,"00:00:01",picPath);
							}else{
								pv.setUpdateStatus(1);
								parkingVedioService.update(pv);
								//合成图片
								PicUtils.checkPics(fileName.split("_steadyCarImg")[0]);
							}
						}
					}
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

	/**
	 * 录制任务
	 * 
	 * @param parkingVedio
	 */
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
			//正常视频截取时间
			Date date = sdf2.parse(parkingVedio.getEventTime());
			Date beginTime = new Date(date.getTime() + 6000);
			Date endTime = new Date(date.getTime() + 36000);
			//地磁录制时间
			if(parkingVedio.getVedioStatus()==1){
				beginTime = new Date(date.getTime() - 20000);
				endTime = new Date(date.getTime() + 10000);
			}
			if(parkingVedio.getVedioStatus()==2){//视频停稳只录制三秒
				beginTime = new Date(date.getTime() + 180000);
				endTime = new Date(date.getTime() + 183000);
			}
			Date date2 = sdf2.parse(parkingVedio.getChangeTime());
			String filePath = "ftp://10.0.0.11/" + sdf3.format(date2) + "/" + parkingVedio.getMac() + "_"
					+ parkingVedio.getChangeTime();
			if (parkingVedio.getType() == 0) {
				filePath += "_outCarVideo_.mp4";
			} else if(parkingVedio.getType() == 1){
				filePath += "_inCarVideo_.mp4";
			}else if(parkingVedio.getType() == 2){
				filePath += "_steadyCarImg_.mp4";
			}
			String sql = "insert into record_download_info (" + "iserialno," + "tcreatetime," + "scameraindex,"
					+ "splateno," + "tbegintime," + "tendtime," + "sfilesavepath," + "sftpusername" + ",sftppassword,"
					+ "tupdatetime," + "iuploadstatus," + "suploadmsg," + "ifilesize) " + "values(" + ""
					+ parkingVedio.getId() + "," + "'" + sdf.format(new Date()) + "'," + "'"
					+ parkingVedio.getCameraIndex() + "'," + "''," + "'" + sdf.format(beginTime) + "'," + "'"
					+ sdf.format(endTime) + "'," + "'" + filePath + "'," + "'ftp_user1'," + "'"
					+ Base64.getEncoder().encodeToString("Zhanway2017".getBytes(StandardCharsets.UTF_8)) + "'," + "'"
					+ sdf.format(new Date()) + "'," + "0," + "''," + "0)";
			log.warn("sql:{}", sql);
			int i = 0;
			pstmt = c.prepareStatement(sql);
			i = pstmt.executeUpdate();
			c.commit();
			log.warn("num:{}", i);
			pstmt.close();
			c.close();
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	@Scheduled(cron = "0/10 * * * * ? ") // 每三秒
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
				String sWholeSenceUrl = rs.getString("spic3");
				String sFutrureUrl = rs.getString("spic3");//合成图
				String pic1 = rs.getString("swholesenceurl");//原图1
				String pic2 = rs.getString("sfutrureurl");//原图2
				String pic3 = rs.getString("spic1");//原图3
				ParkInfo parkInfo = saveParkInfo(id, sLocation, sCameraName, sCameraIndex, iVehicleEnterstate,
						sParkingid, tEventTime, sPlateNo, sPlateColor, sVehicleColor, sWholeSenceUrl, sFutrureUrl);
				Date date = sdf2.parse(tEventTime);
				String ChangeTime = sdf3.format(date);
				String dirPath = sdf.format(date);
				ParkingSpace parkingSpace = parkingSpaceService.getByCameraIndexAndParkNumber(parkInfo.getSCameraIndex(),
						parkInfo.getSParkingid());
				// 添加mac信息
				parkInfo.setMac(parkingSpace.getMac());
				parkInfoService.update(parkInfo);
				parkingSpace.setCameraNumber(sCameraName);
				parkingSpace.setStatus(parkInfo.getIVehicleEnterstate());
				String picPath = dirPath + "/" + parkingSpace.getMac() + "_";
				ParkingVedio parkingVedio = new ParkingVedio();
				parkingVedio.setCameraIndex(sCameraIndex);
				parkingVedio.setEventTime(ChangeTime);
				parkingVedio.setSendStatus(0);
				parkingVedio.setCreateTime(new Date());
				parkingSpace.setHappenTime(date);
				parkingVedio.setChangeTime(ChangeTime);
				parkingVedio.setVedioStatus(0);
				picPath += ChangeTime;
				if (iVehicleEnterstate == 1) {
					savePic(sWholeSenceUrl, picPath + "_inCarImg.jpeg");
					savePic(pic1, picPath + "_step1.jpeg");
					savePic(pic2, picPath + "_step2.jpeg");
					savePic(pic3, picPath + "_step3.jpeg");
				}
				if (iVehicleEnterstate == 2) {
					savePic(sWholeSenceUrl, picPath + "_outCarImg.jpeg");
				}
//				if (iVehicleEnterstate == 1) {
//					parkingSpace.setHappenTime(date);
//					parkingVedio.setChangeTime(ChangeTime);
//					picPath += ChangeTime;
//					savePic(sWholeSenceUrl, picPath + "_inCarImg.jpeg");
//				}
//				if (iVehicleEnterstate == 2) {
//					if (parkingSpace.getHappenTime() == null) {
//						parkingSpace.setHappenTime(date);
//					}
//					picPath += sdf3.format(parkingSpace.getHappenTime());
//					parkingVedio.setChangeTime(sdf3.format(parkingSpace.getHappenTime()));
//					savePic(sWholeSenceUrl, picPath + "_outCarImg.jpeg");
//				}
				parkingVedio.setEventTime(ChangeTime);
				parkingVedio.setMac(parkInfo.getMac());
				parkingVedio.setType(parkInfo.getIVehicleEnterstate());
				parkingSpaceService.update(parkingSpace);
				if (iVehicleEnterstate != 0 ) {
					parkingVedioService.save(parkingVedio);
				}
				sendData(parkingSpace, ChangeTime, sCameraIndex, sPlateNo, parkInfo.getSPlateColor(), parkInfo, picPath,
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
			if (parkInfo.getIVehicleEnterstate() == 0) {
				new Thread(new Runnable() {
					public void run() {
						try {
							Thread.sleep(10000);
							HttpUtil.postJson("http://112.64.46.113:8102/iot/iot/sensor/vedioReport",JSONObject.toJSONString(map));
						} catch (Exception e) {
							log.error("e:{}", e);
						}
					}
				}).start();
			} else {
				HttpUtil.postJson("http://112.64.46.113:8102/iot/iot/sensor/vedioReport",JSONObject.toJSONString(map));
			}
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
		if (iVehicleEnterstate == 0) {
			parkInfo.setIVehicleEnterstate(3);
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
