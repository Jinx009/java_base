//package utils;
//
//import java.io.InputStream;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//public class WuhanPost {
//
//	public static void main(String[] args) {
//		try{
//			HttpClient httpclient = new HttpClient();
//
//			PostMethod postMethod = new PostMethod("http://61.183.70.70:8081/whdc/api.whdc");//测试
//
//			//泊位状态请求
//			String req = "{\"berthcode\":\"000119110700032A\",\"regionID\":\"001001\",\"berthstatus\":1,\"changetime\":\"2019-12-11 12:22:04\",\"electricity\":\"0\",\"log_id\":\"011471255809708\",\"voltage\":3.6}";
//			//设备状态心跳
//			//String req = "{\"EquipmentType\":1,\"EquipmentCode\":\"868681046288088\",\"EquipmentStatus\":0,\"PushTime\":\"2019-11-25 15:19:11\",\"Electricity\":\"0.80\",\"Voltage\":10,\"regionID\":\"002002\"}";
//			String timestamp = String.valueOf(new Date().getTime());
//			Map<String,String> params = new HashMap<String,String>();
//			params.put("appkey", "001001001");
//			params.put("userkey", "001001001");
//			params.put("timestamp", "20191125151911");
//			params.put("method", "uploadberthstatus");//泊位状态
//			//params.put("method", "pushheartbeat");//设备状态心跳
//			params.put("pushdata", req);
//			
//			postMethod.setParameter("appkey", params.get("appkey"));
//			postMethod.setParameter("userkey", params.get("userkey"));
//			postMethod.setParameter("timestamp", params.get("timestamp"));
//			postMethod.setParameter("method", params.get("method"));//泊位状态
//			postMethod.setParameter("pushdata", params.get("pushdata"));
//			
//			String checkSource = "timestamp=" + params.get("timestamp") + "&pushdata=" + params.get("pushdata") + "&method=" + params.get("method") + "&appkey=" + params.get("appkey")+"&userkey=" + params.get("userkey");
//			System.out.println(" 请求参数= " + checkSource);
//			String sign = MD5Util.toMD5(checkSource);
//			System.out.println("sign签名 = " + sign);
//			postMethod.setParameter("sign", sign);
//			System.out.println(postMethod);
//			httpclient.executeMethod(postMethod);
//			System.out.println(postMethod);
//			byte[] pByte = new byte[1024];
//			InputStream in = postMethod.getResponseBodyAsStream();
//			StringBuffer res = new StringBuffer();
//			while ((in.read(pByte)) != -1) {
//				res.append(new String(pByte));
//				pByte = new byte[1024];
//			}
//			String respParam = res.toString().trim();
//			System.out.println(respParam);
//			// 释放连接
//			postMethod.releaseConnection();
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	
//}
