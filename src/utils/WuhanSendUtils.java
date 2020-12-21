package utils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import database.models.device.DeviceSensor;
import database.models.log.LogSensorHeart;
import database.models.log.LogSensorStatus;

public class WuhanSendUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(WuhanSendUtils.class);

	private static String appKey = "001001001";
	private static String userKey = "001001001";
	private static String URL  = "http://61.183.70.70:8081/whdc/api.whdc";
	
	

	public static String sendStatus(LogSensorStatus log,DeviceSensor sensor){
		try{
			HttpClient httpclient = new HttpClient();
			PostMethod postMethod = new PostMethod(URL);
			//泊位状态请求
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String req = "{\"berthcode\":\""+sensor.getDesc()+"\",\"regionID\":\""+sensor.getCameraName()+"\",\"berthstatus\":"+
			sensor.getAvailable()+",\"changetime\":\""+sdf.format(log.getChangeTime())+"\",\"electricity\":\"0\",\"log_id\":\""+log.getId()+"\",\"voltage\":"+sensor.getBatteryVoltage()+"}";
			//设备状态
			logger.warn("req:{}",req);
			//String req = "{\"EquipmentType\":1,\"EquipmentCode\":\"868681046288088\",\"EquipmentStatus\":0,\"PushTime\":\"2019-11-25 15:19:11\",\"Electricity\":\"0.80\",\"Voltage\":10,\"regionID\":\"002002\"}";
			long time = new Date().getTime();
			String timestamp = String.valueOf(time);
			Map<String,String> params = new HashMap<String,String>();
			params.put("appkey", appKey);
			params.put("userkey", userKey);
			params.put("timestamp", timestamp);
			params.put("method", "uploadberthstatus");//泊位状态
			//params.put("method", "pushheartbeat");//设备状态心跳
			params.put("pushdata", req);
			
			postMethod.setParameter("appkey", params.get("appkey"));
			postMethod.setParameter("userkey", params.get("userkey"));
			postMethod.setParameter("timestamp", params.get("timestamp"));
			postMethod.setParameter("method", params.get("method"));//泊位状态
			postMethod.setParameter("pushdata", params.get("pushdata"));
			
			String checkSource = "timestamp=" + params.get("timestamp") + "&pushdata=" + params.get("pushdata") + "&method=" + params.get("method") + "&appkey=" + params.get("appkey")+"&userkey=" + params.get("userkey");
			String sign =MD5Util.toMD5(checkSource).toUpperCase();
			postMethod.setParameter("sign", sign);
			httpclient.executeMethod(postMethod);
			byte[] pByte = new byte[1024];
			InputStream in = postMethod.getResponseBodyAsStream();
			StringBuffer res = new StringBuffer();
			while ((in.read(pByte)) != -1) {
				res.append(new String(pByte));
				pByte = new byte[1024];
			}
			String respParam = res.toString().trim();
			// 释放连接
			postMethod.releaseConnection();
			logger.warn("respParam:{}",respParam);
			String status = JSONObject.parseObject(respParam).getString("status");
			return status;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "0";
	}
	
	
	public static String sendHeart(LogSensorHeart log,DeviceSensor sensor){
		try{
			HttpClient httpclient = new HttpClient();
			PostMethod postMethod = new PostMethod(URL);
			//泊位状态请求
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//设备状态心跳
			String req = "{\"EquipmentType\":1,\"EquipmentCode\":\""+sensor.getMac()+"\",\"EquipmentStatus\":0,\"PushTime\":\""+sdf.format(new Date())
			+"\",\"Electricity\":\""+String.format("%.2f",Double.valueOf(sensor.getBatteryVoltage())/3.50)+"\",\"Voltage\":"+log.getVol()+",\"regionID\":\""+sensor.getCameraName()+"\"}";
			long time = new Date().getTime();
			logger.warn("req:{}",req);
			String timestamp = String.valueOf(time);
			Map<String,String> params = new HashMap<String,String>();
			params.put("appkey", appKey);
			params.put("userkey", userKey);
			params.put("timestamp", timestamp);
//			params.put("method", "uploadberthstatus");//泊位状态
			params.put("method", "pushheartbeat");//设备状态心跳
			params.put("pushdata", req);
			
			postMethod.setParameter("appkey", params.get("appkey"));
			postMethod.setParameter("userkey", params.get("userkey"));
			postMethod.setParameter("timestamp", params.get("timestamp"));
			postMethod.setParameter("method", params.get("method"));//泊位状态
			postMethod.setParameter("pushdata", params.get("pushdata"));
			
			String checkSource = "timestamp=" + params.get("timestamp") + "&pushdata=" + params.get("pushdata") + "&method=" + params.get("method") + "&appkey=" + params.get("appkey")+"&userkey=" + params.get("userkey");
			String sign =MD5Util.toMD5(checkSource).toUpperCase();
			postMethod.setParameter("sign", sign);
			httpclient.executeMethod(postMethod);
			byte[] pByte = new byte[1024];
			InputStream in = postMethod.getResponseBodyAsStream();
			StringBuffer res = new StringBuffer();
			while ((in.read(pByte)) != -1) {
				res.append(new String(pByte));
				pByte = new byte[1024];
			}
			String respParam = res.toString().trim();
			// 释放连接
			postMethod.releaseConnection();
			logger.warn("respParam:{}",respParam);
			String status = JSONObject.parseObject(respParam).getString("status");
			return status;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "0";
	}
	
	public static void main(String[] args) {
//		DeviceSensor sensor = new DeviceSensor();
//		sensor.setMac("0001191107000324");
//		sensor.setBatteryVoltage("3.60");
//		sensor.setDesc("430103");
//		sensor.setCameraName("001001");
//		sensor.setAvailable(0);
//		LogSensorStatus log = new LogSensorStatus();
//		log.setId(100861);
//		log.setChangeTime(new Date());
//		LogSensorHeart logSensorHeart = new LogSensorHeart();
//		logSensorHeart.setVol("3.60");
//		
//		sendStatus(log, sensor);
//		sendHeart(logSensorHeart, sensor);
		System.out.println(Math.abs(1-1));
	}
	
}
