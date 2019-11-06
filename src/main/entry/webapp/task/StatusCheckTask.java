package main.entry.webapp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.sql.PreparedStatement;

import database.models.Camera;
import database.models.ParkingRecord;
import database.models.ParkingVedio;
import service.basicFunctions.CameraService;
import service.basicFunctions.ParkingRecordService;
import service.basicFunctions.ParkingVedioService;
import utils.GifUtils;
import utils.HttpUtil;
import utils.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Lazy(value = false)
public class StatusCheckTask {

	private static final Logger log = LoggerFactory.getLogger(StatusCheckTask.class);

	@Autowired
	private ParkingVedioService parkingVedioService;
	@Autowired
	private CameraService cameraService;
	@Autowired
	private ParkingRecordService parkingRecordService;

	/**
	 * 第一步：文件夹权限问题
	 */
	@Scheduled(cron = "0 1 0 * * ?") // 每天晚上0点01分创建新文件夹
	public void chmod() {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String data = "create_file";
			File file = new File("/media/zhanway/DATA/data/" + sdf.format(date) + "/create_file.txt"); // 本地目录
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
	 * 第二步：新建任务
	 */
	@Scheduled(cron = "0/120 * * * * ? ") // 每2分钟
	public void newVedio() {
		try {
			List<Camera> list = cameraService.findAll();
			if(list!=null&&!list.isEmpty()){
				for(Camera camera:list){
					Date date = camera.getVedioTime();
					if(date==null){
						date = new Date((new Date()).getTime()-(1000*120));
					}
					Date dateEnd = new Date(date.getTime()+(1000*120));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					ParkingVedio parkingVedio = new ParkingVedio();
					parkingVedio.setCameraIndex(camera.getCameraIndex());
					parkingVedio.setCreateTime(new Date());
					parkingVedio.setStatus(0);
					parkingVedio.setTime(date.getTime());
					parkingVedio.setVedioStart(sdf.format(date));
					parkingVedio.setVedioEnd(sdf.format(dateEnd));
					parkingVedioService.save(parkingVedio);
					cameraService.update(camera.getId(), dateEnd);
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	/**
	 * 第三步：修改zip名称
	 */
	@Scheduled(cron = "0/30 * * * * ? ") // 每30s
	public void updateVedio() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByStatus(0);
			if(list!=null&&!list.isEmpty()){
				for(ParkingVedio vedio:list){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String fileName = sdf.format(vedio.getVedioStart())+"/"+vedio.getCameraIndex()+"_"+vedio.getTime()+"_"+vedio.getId();
					vedio.setDirPath("/media/zhanway/DATA/data/"+fileName);
					vedio.setZipName("/media/zhanway/DATA/data/"+fileName+"/"+vedio.getCameraIndex()+"_"+vedio.getTime()+"_"+vedio.getId()+".zip");
					vedio.setStatus(1);
					parkingVedioService.update(vedio);
					saveVedio(vedio);
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}


	/**
	 * 第四步：下发录制任务
	 * 
	 */
	private  void saveVedio(ParkingVedio parkingVedio) {
		Connection c = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement pstmt;
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "v_admin", "v_admin");
			c.setAutoCommit(false);

			String sql = "insert into record_download_info (" + "iserialno," + "tcreatetime," + "scameraindex,"
					+ "splateno," + "tbegintime," + "tendtime," + "sfilesavepath," + "sftpusername" + ",sftppassword,"
					+ "tupdatetime," + "iuploadstatus," + "suploadmsg," + "ifilesize) " + "values(" + ""
					+ parkingVedio.getId() + "," + "'" + sdf.format(new Date()) + "'," + "'"
					+ parkingVedio.getCameraIndex() + "'," + "''," + "'" + parkingVedio.getVedioStart() + "'," + "'"
					+ parkingVedio.getVedioEnd()  + "'," + "'" + parkingVedio.getDirPath()+"/main.mp4" + "'," + "'zhanway'," + "'"
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
			parkingVedio.setStatus(2);
			parkingVedioService.update(parkingVedio);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}
	
	
	/**
	 * 第五步：开始截图
	 */
	@Scheduled(cron = "0/30 * * * * ? ") // 每30s
	public void checkVedio() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByStatus(2);
			if(list!=null&&!list.isEmpty()){
				for(ParkingVedio vedio:list){
					File file = new File(vedio.getDirPath()+"/main.mp4");
					if(file.exists()){
						vedio.setStatus(3);
						parkingVedioService.update(vedio);
						new Thread(){
							public void run(){
						        String fileName = vedio.getDirPath();
						        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
						        Date d;
								try {
									d = format.parse("1998-01-01 00:00:00");
									String time = "";
									long num = 60;
									File[] files = new File[(int) num];
							        for( int i = 0;i<num;i++){
							            Calendar calendar = Calendar.getInstance();
							            calendar.setTime(d);
							            calendar.add(Calendar.SECOND,2);
							            d = calendar.getTime();
							            time = f.format(d);
							            GifUtils.covPic(fileName,time,vedio.getDirPath()+"/ffmpeg_"+(i+1)+".jpg");
							            files[i] = new File(vedio.getDirPath()+"/ffmpeg_"+(i+1)+".jpg");
							        }
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}.start();
					}
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	
	
	/**
	 * 第六步：截图完成打包
	 */
	@Scheduled(cron = "0/30 * * * * ? ") // 每30s
	public void zipVedio() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByStatus(3);
			if(list!=null&&!list.isEmpty()){
				for(ParkingVedio vedio:list){
					boolean r = true;
					for(int i = 1;i<=60;i++){
						File file = new File(vedio.getDirPath()+"/ffmpeg_"+i+".jpg");
						if(!file.exists()){
							r = false;
							break;
						}
					}
					if(r){
						File[] files = new File[60];
						for(int i = 1;i<=60;i++){
							files[i-1] = new File(vedio.getDirPath()+"/ffmpeg_"+i+".jpg");
						}
						GifUtils.zipFiles(files, new File(vedio.getZipName()));
						vedio.setStatus(4);
						parkingVedioService.update(vedio);
					}
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	
	/**
	 * 第七步：打包完成发任务
	 */
	@Scheduled(cron = "0/30 * * * * ? ") // 每30s
	public void zipComplate() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByStatus(4);
			if(list!=null&&!list.isEmpty()){
				for(ParkingVedio vedio:list){
					File file = new File(vedio.getZipName());
					if(file.exists()){
						GifUtils.postFile("http://localhost/vehicle/upload", vedio.getZipName());
						vedio.setStatus(6);
						parkingVedioService.update(vedio);
					}
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}
	
	/**
	 * 第八步：查结果
	 */
	@Scheduled(cron = "0/30 * * * * ? ") // 每30s
	public void result() {
		try {
			List<ParkingVedio> list = parkingVedioService.findByStatus(6);
			if(list!=null&&!list.isEmpty()){
				for(ParkingVedio vedio:list){
					String res = HttpUtil.get("http://localhost/vehicle/result");
					vedio.setResult(res);
					vedio.setStatus(7);
					parkingVedioService.update(vedio);
					JSONObject jsonObject = JSONObject.parseObject(vedio.getResult());
					String date = vedio.getVedioStart();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date d = format.parse(date);
					for(int i = 0;i<60;i++){
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(d);
						calendar.add(Calendar.SECOND, 2);
						d = calendar.getTime();
						String s = jsonObject.getString("ffmpeg_"+(i+1)+".jpg");
						if(StringUtil.isNotBlank(s)){
							JSONObject obj = JSONObject.parseObject(s);
							insert(obj,0,vedio,format.format(d),(i+1));
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	public void insert(JSONObject obj,int num,ParkingVedio vedio,String d,int picNum){
		String ss = obj.getString(""+num+"");
		if(StringUtil.isNotBlank(ss)){
			num++;
			ParkingRecord vedioLog = new ParkingRecord();
			vedioLog.setVedioTime(d);
			vedioLog.setTaskId(vedio.getId());
			vedioLog.setCreateTime(new Date());
			String carArry = JSONObject.parseObject(ss).getString("bbox");
			List<String> cArry = JSONObject.parseArray(carArry, String.class);
			vedioLog.setCarX(cArry.get(0));
			vedioLog.setCarX2(cArry.get(2));
			vedioLog.setCarY(cArry.get(1));
			vedioLog.setCarY2(cArry.get(3));
			vedioLog.setStatus(-1);
			vedioLog.setPicNumber(picNum);
			JSONArray jsonArray = JSONObject.parseArray( JSONObject.parseObject(ss).getString("chepai"));
			vedioLog.setCpX("");
			vedioLog.setCpX2("");
			vedioLog.setCpY("");
			vedioLog.setCpY2("");
			if(!jsonArray.isEmpty()){
				JSONObject j = jsonArray.getJSONObject(0);
				String cph = unicodeToCn(j.getString("text"));
				vedioLog.setCarNumber(cph);
				List<String> cpArry = JSONObject.parseArray(j.getString("bbox"), String.class);
				vedioLog.setCpX(cpArry.get(0));
				vedioLog.setCpX2(cpArry.get(2));
				vedioLog.setCpY(cpArry.get(1));
				vedioLog.setCpY2(cpArry.get(3));
			}
			parkingRecordService.save(vedioLog);
			insert(obj, num,vedio,d,picNum);
		}
	}
	
	 public static String unicodeToCn(String s) {
		   String cph = "";
		   char[] c = s.toCharArray();
			for(int i = 0;i<c.length;i++){
				cph+=c[i];
			}
			return cph;
	    }
	

}
