package main.entry.webapp.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import utils.HttpUtils;
import utils.StringUtil;

public class SendUtils {
	
	public  static  final  int  START =  0 ;    //定义范围开始数字
    
    public  static  final  int  END =  99 ;  //定义范围结束数字

	public static void main(String[] args) {
		try {
//			String data = readFileContent("/Users/jinx/Downloads/base.txt");
//			String data2 = readFileContent("/Users/jinx/Downloads/new.txt");
//			JSONArray base = JSONObject.parseArray(data);
//			JSONArray new2 = JSONObject.parseArray(data2);
//			for(int i = 0;i<base.size();i++) {
//				JSONObject obj = base.getJSONObject(i);
//				String sim = obj.getString("C");
//				for(int j=0;j<new2.size();j++) {
//					JSONObject obj2 = new2.getJSONObject(j);
//					if(sim.equals(obj2.getString("sim"))) {
//						System.out.println(obj2.getString("mo"));
//					}
//				}
//			}
//			String[] sn_arr = new String[] {"01010400056","01010400069","01010400065","01010400059"};
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//			Map< String, Object> map = new HashMap<String, Object>();
//			Map< String, Object> d = new HashMap<String, Object>();
//			for(int i = 5;i<arr.size();i++) {
//				for(int j = 0;j<sn_arr.length;j++) {
//					JSONObject obj = arr.getJSONObject(i);
//					d.put("gX", hexToFloat_1(obj.getString("base_acce_x")));
//					d.put("gY", hexToFloat_1(obj.getString("base_acce_y")));
//					d.put("gZ", hexToFloat_1(obj.getString("base_acce_z")));
//					d.put("X", hexToFloat_1(obj.getString("base_x")));
//					d.put("Y", hexToFloat_1(obj.getString("base_y")));
//					d.put("Z", 0);
//					map.put("sblxbm", "103");
//					map.put("jczb",d);
//					map.put("jcsj", sdf.format(sdf2.parse(obj.getString("create_time"))));
//					map.put("cgq", "1");
//					HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+sn_arr[j]+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
//				}
//			}
		       String s = "[" +
		               "{\"mac\":\"0009200422000004\",\"sn\":\"01010400229\",\"type\":\"103\",\"date\":\"2020-12-06 03:45:22\"}," +
		               "{\"mac\":\"0009190906000024\",\"sn\":\"01010400116\",\"type\":\"103\",\"date\":\"2020-12-04 03:15:56\"}," +
		               "{\"mac\":\"0009190906000017\",\"sn\":\"01010400103\",\"type\":\"103\",\"date\":\"2020-12-01 22:34:23\"}," +
		               "{\"mac\":\"000920050800003E\",\"sn\":\"01010400302\",\"type\":\"103\",\"date\":\"2020-12-01 08:31:06\"}," +
		               "{\"mac\":\"000920050800005D\",\"sn\":\"01010400333\",\"type\":\"103\",\"date\":\"2020-12-01 08:31:06\"}," +
		               "{\"mac\":\"0009190906000012\",\"sn\":\"01010400098\",\"type\":\"103\",\"date\":\"2020-12-01 00:59:47\"}," +
		               "{\"mac\":\"0009200508000075\",\"sn\":\"01010400378\",\"type\":\"103\",\"date\":\"2020-11-30 16:38:36\"}," +
		               "{\"mac\":\"0009190906000058\",\"sn\":\"01010400220\",\"type\":\"103\",\"date\":\"2020-11-30 14:14:06\"}," +
		               "{\"mac\":\"0009190906000056\",\"sn\":\"01010400218\",\"type\":\"103\",\"date\":\"2020-11-30 13:54:56\"}," +
		               "{\"mac\":\"0009200422000002\",\"sn\":\"01010400228\",\"type\":\"103\",\"date\":\"2020-11-30 13:47:33\"}," +
		               "{\"mac\":\"0009190906000057\",\"sn\":\"01010400219\",\"type\":\"103\",\"date\":\"2020-11-30 13:47:33\"}," +
		               "{\"mac\":\"000919090600001A\",\"sn\":\"01010400106\",\"type\":\"103\",\"date\":\"2020-11-30 07:14:23\"}," +
		               "{\"mac\":\"0009190906000050\",\"sn\":\"01010400157\",\"type\":\"103\",\"date\":\"2020-11-29 13:03:41\"}," +
		               "{\"mac\":\"000920050800005E\",\"sn\":\"01010400334\",\"type\":\"103\",\"date\":\"2020-11-29 03:51:25\"}," +
		               "{\"mac\":\"0009200508000025\",\"sn\":\"01010400277\",\"type\":\"103\",\"date\":\"2020-11-28 17:15:23\"}," +
		               "{\"mac\":\"0009190906000002\",\"sn\":\"01010400083\",\"type\":\"103\",\"date\":\"2020-11-26 06:54:20\"}," +
		               "{\"mac\":\"0009200508000102\",\"sn\":\"01010400519\",\"type\":\"103\",\"date\":\"2020-11-26 00:04:36\"}," +
		               "{\"mac\":\"00092005080000FF\",\"sn\":\"01010400516\",\"type\":\"103\",\"date\":\"2020-11-25 01:31:47\"}," +
		               "{\"mac\":\"0009190906000049\",\"sn\":\"01010400150\",\"type\":\"103\",\"date\":\"2020-11-23 20:40:01\"}," +
		               "{\"mac\":\"0009190906000044\",\"sn\":\"01010400146\",\"type\":\"103\",\"date\":\"2020-11-23 05:05:28\"}," +
		               "{\"mac\":\"0009200422000003\",\"sn\":\"01010400226\",\"type\":\"103\",\"date\":\"2020-11-23 01:48:21\"}," +
		               "{\"mac\":\"0009200508000120\",\"sn\":\"01010400549\",\"type\":\"103\",\"date\":\"2020-11-21 06:24:20\"}," +
		               "{\"mac\":\"0009200508000121\",\"sn\":\"01010400550\",\"type\":\"103\",\"date\":\"2020-11-21 06:24:18\"}," +
		               "{\"mac\":\"000920050800010A\",\"sn\":\"01010400527\",\"type\":\"103\",\"date\":\"2020-11-21 06:24:18\"}," +
		               "{\"mac\":\"000920050800001E\",\"sn\":\"01010400270\",\"type\":\"103\",\"date\":\"2020-11-19 19:36:28\"}," +
		               "{\"mac\":\"0009190906000009\",\"sn\":\"01010400090\",\"type\":\"103\",\"date\":\"2020-11-15 05:38:22\"}," +
		               "{\"mac\":\"0009190906000001\",\"sn\":\"01010400082\",\"type\":\"103\",\"date\":\"2020-11-15 05:38:22\"}," +
		               "{\"mac\":\"0009190906000027\",\"sn\":\"01010400119\",\"type\":\"103\",\"date\":\"2020-11-14 18:50:56\"}," +
		               "{\"mac\":\"000919090600004F\",\"sn\":\"01010400156\",\"type\":\"103\",\"date\":\"2020-11-13 16:46:18\"}," +
		               "{\"mac\":\"0009190906000015\",\"sn\":\"01010400101\",\"type\":\"103\",\"date\":\"2020-11-13 00:16:26\"}," +
		               "{\"mac\":\"0009200508000115\",\"sn\":\"01010400538\",\"type\":\"103\",\"date\":\"2020-11-12 22:43:03\"}," +
		               "{\"mac\":\"0009190600000056\",\"sn\":\"01010400058\",\"type\":\"103\",\"date\":\"2020-11-10 23:26:26\"}," +
		               "{\"mac\":\"000920050800005A\",\"sn\":\"01010400330\",\"type\":\"103\",\"date\":\"2020-11-10 04:11:02\"}," +
		               "{\"mac\":\"0009190906000011\",\"sn\":\"01010400097\",\"type\":\"103\",\"date\":\"2020-11-09 03:25:15\"}," +
		               "{\"mac\":\"0009190906000028\",\"sn\":\"01010400120\",\"type\":\"103\",\"date\":\"2020-11-03 05:14:46\"}," +
		               "{\"mac\":\"0009190906000006\",\"sn\":\"01010400087\",\"type\":\"103\",\"date\":\"2020-11-01 18:06:54\"}," +
		               "{\"mac\":\"0009190906000016\",\"sn\":\"01010400102\",\"type\":\"103\",\"date\":\"2020-10-31 01:50:13\"}," +
		               "{\"mac\":\"0009200508000049\",\"sn\":\"01010400313\",\"type\":\"103\",\"date\":\"2020-10-23 16:12:54\"}," +
		               "{\"mac\":\"000919090600003C\",\"sn\":\"01010400139\",\"type\":\"103\",\"date\":\"2020-10-22 15:56:15\"}," +
		               "{\"mac\":\"000919090600004D\",\"sn\":\"01010400154\",\"type\":\"103\",\"date\":\"2020-10-19 13:07:48\"}," +
		               "{\"mac\":\"0009190600000031\",\"sn\":\"01010400081\",\"type\":\"103\",\"date\":\"2020-10-15 16:36:33\"}," +
		               "{\"mac\":\"0009190600000011\",\"sn\":\"01010400014\",\"type\":\"103\",\"date\":\"2020-10-15 16:36:33\"}," +
		               "{\"mac\":\"000920072300002C\",\"sn\":\"01010400802\",\"type\":\"103\",\"date\":\"2020-10-14 02:08:07\"}," +
		               "{\"mac\":\"000920050800008B\",\"sn\":\"01010400400\",\"type\":\"103\",\"date\":\"2020-10-13 03:42:19\"}," +
		               "{\"mac\":\"0009200723000029\",\"sn\":\"01010400823\",\"type\":\"103\",\"date\":\"2020-10-13 03:42:19\"}," +
		               "{\"mac\":\"000919060000004D\",\"sn\":\"01010400066\",\"type\":\"103\",\"date\":\"2020-10-12 19:23:06\"}," +
		               "{\"mac\":\"0009190906000010\",\"sn\":\"01010400096\",\"type\":\"103\",\"date\":\"2020-10-12 11:55:26\"}," +
		               "{\"mac\":\"00092005080000C7\",\"sn\":\"01010400460\",\"type\":\"103\",\"date\":\"2020-10-10 03:34:03\"}," +
		               "{\"mac\":\"0009200508000128\",\"sn\":\"01010400557\",\"type\":\"103\",\"date\":\"2020-10-09 08:32:18\"}," +
		               "{\"mac\":\"000920050800000D\",\"sn\":\"01010400253\",\"type\":\"103\",\"date\":\"2020-10-02 08:27:32\"}," +
		               "{\"mac\":\"000919090600005F\",\"sn\":\"01010400232\",\"type\":\"103\",\"date\":\"2020-09-30 10:00:37\"}," +
		               "{\"mac\":\"000920050800002A\",\"sn\":\"01010400282\",\"type\":\"103\",\"date\":\"2020-09-30 09:35:52\"}," +
		               "{\"mac\":\"0009200508000019\",\"sn\":\"01010400265\",\"type\":\"103\",\"date\":\"2020-09-29 19:49:54\"}," +
		               "{\"mac\":\"0009190329000018\",\"sn\":\"01010410300002\",\"type\":\"103\",\"date\":\"2020-09-20 04:46:10\"}," +
		               "{\"mac\":\"000919032900001B\",\"sn\":\"01010410300005\",\"type\":\"103\",\"date\":\"2020-09-20 04:46:10\"}," +
		               "{\"mac\":\"0009200508000059\",\"sn\":\"01010400329\",\"type\":\"103\",\"date\":\"2020-09-09 14:38:39\"}," +
		               "{\"mac\":\"1906180049\",\"sn\":\"01010400160\",\"type\":\"103\",\"date\":\"2020-09-09 08:21:46\"}," +
		               "{\"mac\":\"0009190906000022\",\"sn\":\"01010400114\",\"type\":\"103\",\"date\":\"2020-09-08 19:01:08\"}," +
		               "{\"mac\":\"1906180046\",\"sn\":\"01010400159\",\"type\":\"103\",\"date\":\"2020-09-08 16:20:17\"}," +
		               "{\"mac\":\"1906180045\",\"sn\":\"01010400164\",\"type\":\"103\",\"date\":\"2020-09-08 14:20:12\"}," +
		               "{\"mac\":\"1906180047\",\"sn\":\"01010400162\",\"type\":\"103\",\"date\":\"2020-09-08 14:20:12\"}," +
		               "{\"mac\":\"1906180053\",\"sn\":\"01010400158\",\"type\":\"103\",\"date\":\"2020-09-08 14:20:12\"}," +
		               "{\"mac\":\"1906180050\",\"sn\":\"01010400163\",\"type\":\"103\",\"date\":\"2020-09-08 13:39:04\"}," +
		               "{\"mac\":\"0009190600000015\",\"sn\":\"01010400017\",\"type\":\"103\",\"date\":\"2020-09-04 09:07:25\"}," +
		               "{\"mac\":\"0009190600000012\",\"sn\":\"01010400015\",\"type\":\"103\",\"date\":\"2020-09-04 09:07:25\"}," +
		               "{\"mac\":\"0009190600000018\",\"sn\":\"01010400020\",\"type\":\"103\",\"date\":\"2020-09-04 09:07:25\"}," +
		               "{\"mac\":\"0009190600000009\",\"sn\":\"01010400006\",\"type\":\"103\",\"date\":\"2020-09-04 09:07:25\"}," +
		               "{\"mac\":\"0009200508000126\",\"sn\":\"01010400555\",\"type\":\"103\",\"date\":\"2020-08-29 21:14:22\"}," +
		               "{\"mac\":\"0009190600000020\",\"sn\":\"01010400028\",\"type\":\"103\",\"date\":\"2020-08-26 12:37:08\"}," +
		               "{\"mac\":\"0009200508000034\",\"sn\":\"01010400292\",\"type\":\"103\",\"date\":\"2020-08-25 19:44:59\"}," +
		               "{\"mac\":\"00092005080000D1\",\"sn\":\"01010400470\",\"type\":\"103\",\"date\":\"2020-08-23 01:49:00\"}," +
		               "{\"mac\":\"000920050800010C\",\"sn\":\"01010400529\",\"type\":\"103\",\"date\":\"2020-08-20 09:22:25\"}," +
		               "{\"mac\":\"0009200508000109\",\"sn\":\"01010400526\",\"type\":\"103\",\"date\":\"2020-08-20 09:22:25\"}," +
		               "{\"mac\":\"0009190329000022\",\"sn\":\"01010400166\",\"type\":\"103\",\"date\":\"2020-07-13 08:56:39\"}" +
		               "" +
		               "]";
		    String ss = "[{\"base_x\":\"0\",\"base_y\":\"-0.06\",\"base_z\":\"0\",\"base_acce_x\":\"0.0001\",\"base_acce_y\":\"-0.0002\",\"base_acce_z\":\"0.0009\"},{\"base_x\":\"0\",\"base_y\":\"0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0005\",\"base_acce_y\":\"-0.0009\",\"base_acce_z\":\"0.0002\"},{\"base_x\":\"0.36\",\"base_y\":\"0.08\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0051\",\"base_acce_y\":\"-0.0147\",\"base_acce_z\":\"-0.0114\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.14\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0.0009\",\"base_acce_y\":\"0.0013\",\"base_acce_z\":\"-0.002\"},{\"base_x\":\"-0.03\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.55\",\"base_y\":\"0.13\",\"base_z\":\"0\",\"base_acce_x\":\"0.0091\",\"base_acce_y\":\"-0.0225\",\"base_acce_z\":\"0.0029\"},{\"base_x\":\"-0.02\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0.0015\",\"base_acce_y\":\"-0.001\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.05\",\"base_y\":\"-0.18\",\"base_z\":\"0\",\"base_acce_x\":\"0.0004\",\"base_acce_y\":\"0.0005\",\"base_acce_z\":\"0.0015\"},{\"base_x\":\"-0.23\",\"base_y\":\"-0.04\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0016\",\"base_acce_y\":\"0.001\",\"base_acce_z\":\"0.0013\"},{\"base_x\":\"-0.48\",\"base_y\":\"0.08\",\"base_z\":\"0\",\"base_acce_x\":\"0.0083\",\"base_acce_y\":\"-0.0161\",\"base_acce_z\":\"0.0052\"},{\"base_x\":\"-0.05\",\"base_y\":\"0.02\",\"base_z\":\"0\",\"base_acce_x\":\"0.0006\",\"base_acce_y\":\"0.0002\",\"base_acce_z\":\"-0.0008\"},{\"base_x\":\"-0.09\",\"base_y\":\"-0.07\",\"base_z\":\"0\",\"base_acce_x\":\"0.0015\",\"base_acce_y\":\"0.0003\",\"base_acce_z\":\"-0.0006\"},{\"base_x\":\"0.18\",\"base_y\":\"0.03\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0009\",\"base_acce_y\":\"-0.0064\",\"base_acce_z\":\"0.0084\"},{\"base_x\":\"0.01\",\"base_y\":\"-0.06\",\"base_z\":\"0\",\"base_acce_x\":\"0.0004\",\"base_acce_y\":\"0.0007\",\"base_acce_z\":\"-0.0002\"},{\"base_x\":\"-0.01\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0013\",\"base_acce_y\":\"0.0007\",\"base_acce_z\":\"-0.0013\"},{\"base_x\":\"-0.14\",\"base_y\":\"0.09\",\"base_z\":\"0\",\"base_acce_x\":\"0.0024\",\"base_acce_y\":\"-0.0159\",\"base_acce_z\":\"0.0037\"},{\"base_x\":\"0.41\",\"base_y\":\"0.05\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0073\",\"base_acce_y\":\"-0.009\",\"base_acce_z\":\"0.0167\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"0.33\",\"base_y\":\"0.14\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0056\",\"base_acce_y\":\"-0.0245\",\"base_acce_z\":\"-0.0153\"},{\"base_x\":\"-0.23\",\"base_y\":\"-0.19\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0013\",\"base_acce_y\":\"-0.0004\",\"base_acce_z\":\"-0.0005\"},{\"base_x\":\"-0.07\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0002\",\"base_acce_y\":\"-0.0011\",\"base_acce_z\":\"0.0005\"},{\"base_x\":\"-0.25\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"0.0001\",\"base_acce_y\":\"0.0003\",\"base_acce_z\":\"0.0022\"},{\"base_x\":\"0\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.19\",\"base_y\":\"9.69\",\"base_z\":\"0\",\"base_acce_x\":\"0.0034\",\"base_acce_y\":\"-0.1696\",\"base_acce_z\":\"0.0181\"},{\"base_x\":\"0.03\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.11\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"0.3\",\"base_y\":\"0.02\",\"base_z\":\"0\",\"base_acce_x\":\"0.0004\",\"base_acce_y\":\"-0.0001\",\"base_acce_z\":\"0.0001\"},{\"base_x\":\"-0.07\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.04\",\"base_y\":\"0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0.0016\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0.0011\"},{\"base_x\":\"-0.04\",\"base_y\":\"-0.08\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0005\",\"base_acce_y\":\"0.0009\",\"base_acce_z\":\"0.0004\"},{\"base_x\":\"0.27\",\"base_y\":\"0.19\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0049\",\"base_acce_y\":\"-0.0338\",\"base_acce_z\":\"0.0095\"},{\"base_x\":\"0.16\",\"base_y\":\"0.15\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0035\",\"base_acce_y\":\"-0.0269\",\"base_acce_z\":\"-0.0037\"},{\"base_x\":\"0\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.11\",\"base_y\":\"-0.12\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0.0001\",\"base_acce_z\":\"0.0007\"},{\"base_x\":\"-0.33\",\"base_y\":\"-0.08\",\"base_z\":\"0\",\"base_acce_x\":\"0.0003\",\"base_acce_y\":\"0.0016\",\"base_acce_z\":\"0.0001\"},{\"base_x\":\"0.33\",\"base_y\":\"0.05\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0053\",\"base_acce_y\":\"-0.0099\",\"base_acce_z\":\"0.0046\"},{\"base_x\":\"-0.06\",\"base_y\":\"-0.09\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0002\",\"base_acce_y\":\"-0.0002\",\"base_acce_z\":\"-0.0002\"},{\"base_x\":\"-0.1\",\"base_y\":\"-0.04\",\"base_z\":\"0\",\"base_acce_x\":\"0.0009\",\"base_acce_y\":\"0.0004\",\"base_acce_z\":\"0.0004\"},{\"base_x\":\"0\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0001\",\"base_acce_y\":\"0.0002\",\"base_acce_z\":\"0.0003\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0002\",\"base_acce_y\":\"-0.0015\",\"base_acce_z\":\"0.0017\"},{\"base_x\":\"-0.25\",\"base_y\":\"-0.12\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0011\",\"base_acce_y\":\"-0.0008\",\"base_acce_z\":\"0.0011\"},{\"base_x\":\"-0.73\",\"base_y\":\"-0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0.0123\",\"base_acce_y\":\"0.0022\",\"base_acce_z\":\"0.0004\"},{\"base_x\":\"0.05\",\"base_y\":\"-0.05\",\"base_z\":\"0\",\"base_acce_x\":\"0.0005\",\"base_acce_y\":\"-0.0002\",\"base_acce_z\":\"0.0012\"},{\"base_x\":\"-0.13\",\"base_y\":\"0.04\",\"base_z\":\"0\",\"base_acce_x\":\"0.0013\",\"base_acce_y\":\"-0.0004\",\"base_acce_z\":\"-0.0006\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.09\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0.0012\",\"base_acce_y\":\"-0.0014\",\"base_acce_z\":\"-0.0001\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.22\",\"base_y\":\"0.16\",\"base_z\":\"0\",\"base_acce_x\":\"0.0047\",\"base_acce_y\":\"-0.0283\",\"base_acce_z\":\"0.0082\"},{\"base_x\":\"15.59\",\"base_y\":\"-4.11\",\"base_z\":\"0\",\"base_acce_x\":\"-0.001\",\"base_acce_y\":\"0.0005\",\"base_acce_z\":\"-0.0001\"},{\"base_x\":\"-0.19\",\"base_y\":\"-0.3\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0005\",\"base_acce_y\":\"-0.0004\",\"base_acce_z\":\"-0.0007\"},{\"base_x\":\"-0.06\",\"base_y\":\"-0.05\",\"base_z\":\"0\",\"base_acce_x\":\"0.001\",\"base_acce_y\":\"-0.0005\",\"base_acce_z\":\"-0.001\"},{\"base_x\":\"0\",\"base_y\":\"-0.52\",\"base_z\":\"0\",\"base_acce_x\":\"0.003\",\"base_acce_y\":\"0.0921\",\"base_acce_z\":\"-0.006\"},{\"base_x\":\"0\",\"base_y\":\"0.05\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0002\",\"base_acce_y\":\"0.0002\",\"base_acce_z\":\"-0.0002\"},{\"base_x\":\"-0.25\",\"base_y\":\"-0.06\",\"base_z\":\"0\",\"base_acce_x\":\"0.0009\",\"base_acce_y\":\"-0.0011\",\"base_acce_z\":\"-0.0019\"},{\"base_x\":\"0.12\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.02\",\"base_y\":\"-0.11\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0009\",\"base_acce_y\":\"0.0015\",\"base_acce_z\":\"-0.0013\"},{\"base_x\":\"-0.18\",\"base_y\":\"0.06\",\"base_z\":\"0\",\"base_acce_x\":\"0.0018\",\"base_acce_y\":\"-0.0003\",\"base_acce_z\":\"0.0003\"},{\"base_x\":\"-0.24\",\"base_y\":\"-0.06\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0007\",\"base_acce_y\":\"0.0007\",\"base_acce_z\":\"0.0006\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.04\",\"base_y\":\"0.04\",\"base_z\":\"0\",\"base_acce_x\":\"0.0009\",\"base_acce_y\":\"0.0001\",\"base_acce_z\":\"0\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.02\",\"base_y\":\"0.07\",\"base_z\":\"0\",\"base_acce_x\":\"0.0005\",\"base_acce_y\":\"-0.0015\",\"base_acce_z\":\"0.0001\"},{\"base_x\":\"0\",\"base_y\":\"0.02\",\"base_z\":\"0\",\"base_acce_x\":\"0.0006\",\"base_acce_y\":\"0.0009\",\"base_acce_z\":\"-0.0001\"},{\"base_x\":\"-0.34\",\"base_y\":\"-0.06\",\"base_z\":\"0\",\"base_acce_x\":\"0.0011\",\"base_acce_y\":\"0.0008\",\"base_acce_z\":\"-0.0008\"},{\"base_x\":\"-0.21\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0007\",\"base_acce_y\":\"-0.0001\",\"base_acce_z\":\"-0.0005\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"-0.05\",\"base_y\":\"0.01\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0009\",\"base_acce_y\":\"-0.0008\",\"base_acce_z\":\"-0.001\"},{\"base_x\":\"-0.3\",\"base_y\":\"0.02\",\"base_z\":\"0\",\"base_acce_x\":\"0.0047\",\"base_acce_y\":\"-0.0046\",\"base_acce_z\":\"0.0177\"},{\"base_x\":\"0.19\",\"base_y\":\"0.09\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0025\",\"base_acce_y\":\"-0.0143\",\"base_acce_z\":\"0.0098\"},{\"base_x\":\"-0.15\",\"base_y\":\"0.03\",\"base_z\":\"0\",\"base_acce_x\":\"0.0009\",\"base_acce_y\":\"0.0005\",\"base_acce_z\":\"-0.0007\"},{\"base_x\":\"-0.15\",\"base_y\":\"0.08\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0004\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0.0001\"},{\"base_x\":\"-0.45\",\"base_y\":\"0.18\",\"base_z\":\"0\",\"base_acce_x\":\"0.0079\",\"base_acce_y\":\"-0.0309\",\"base_acce_z\":\"0.0099\"},{\"base_x\":\"-0.12\",\"base_y\":\"-0.12\",\"base_z\":\"0\",\"base_acce_x\":\"-0.001\",\"base_acce_y\":\"0.0003\",\"base_acce_z\":\"0.0009\"},{\"base_x\":\"-0.15\",\"base_y\":\"-0.06\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0005\",\"base_acce_y\":\"0.0009\",\"base_acce_z\":\"0.0002\"},{\"base_x\":\"-0.26\",\"base_y\":\"0.15\",\"base_z\":\"0\",\"base_acce_x\":\"0.0059\",\"base_acce_y\":\"-0.027\",\"base_acce_z\":\"0.0103\"},{\"base_x\":\"-0.9\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0.0154\",\"base_acce_y\":\"-0.0015\",\"base_acce_z\":\"0.0231\"},{\"base_x\":\"0\",\"base_y\":\"-0.03\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0012\",\"base_acce_y\":\"0.0001\",\"base_acce_z\":\"-0.0002\"},{\"base_x\":\"2.18\",\"base_y\":\"0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.037\",\"base_acce_y\":\"-0.0045\",\"base_acce_z\":\"0.0199\"},{\"base_x\":\"-0.57\",\"base_y\":\"0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0.0001\",\"base_acce_y\":\"0.0005\",\"base_acce_z\":\"-0.0027\"},{\"base_x\":\"-1.66\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0002\",\"base_acce_y\":\"0.0002\",\"base_acce_z\":\"0.0004\"},{\"base_x\":\"-0.26\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"0.0009\",\"base_acce_y\":\"0.0007\",\"base_acce_z\":\"-0.0002\"},{\"base_x\":\"-0.12\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0.0009\",\"base_acce_y\":\"-0.0002\",\"base_acce_z\":\"0.0013\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"0\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0004\",\"base_acce_y\":\"-0.0004\",\"base_acce_z\":\"-0.0011\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"0\",\"base_acce_y\":\"0\",\"base_acce_z\":\"0\"},{\"base_x\":\"0\",\"base_y\":\"-0.07\",\"base_z\":\"0\",\"base_acce_x\":\"0.0001\",\"base_acce_y\":\"-0.0008\",\"base_acce_z\":\"0.002\"},{\"base_x\":\"0.09\",\"base_y\":\"0.09\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0022\",\"base_acce_y\":\"-0.0161\",\"base_acce_z\":\"0.0016\"},{\"base_x\":\"0.01\",\"base_y\":\"-0.09\",\"base_z\":\"0\",\"base_acce_x\":\"0.0004\",\"base_acce_y\":\"-0.0006\",\"base_acce_z\":\"-0.0001\"},{\"base_x\":\"-0.01\",\"base_y\":\"-0.06\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0001\",\"base_acce_y\":\"0.0008\",\"base_acce_z\":\"0.0002\"},{\"base_x\":\"2.18\",\"base_y\":\"0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.037\",\"base_acce_y\":\"-0.0045\",\"base_acce_z\":\"0.0199\"},{\"base_x\":\"0.01\",\"base_y\":\"0.01\",\"base_z\":\"0\",\"base_acce_x\":\"0.0002\",\"base_acce_y\":\"-0.0023\",\"base_acce_z\":\"0.0001\"},{\"base_x\":\"0\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0007\",\"base_acce_y\":\"-0.001\",\"base_acce_z\":\"0.0012\"},{\"base_x\":\"-0.18\",\"base_y\":\"-0.02\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0006\",\"base_acce_y\":\"-0.0008\",\"base_acce_z\":\"-0.0007\"},{\"base_x\":\"-0.09\",\"base_y\":\"0.05\",\"base_z\":\"0\",\"base_acce_x\":\"0.0031\",\"base_acce_y\":\"-0.0091\",\"base_acce_z\":\"0.0051\"},{\"base_x\":\"-0.18\",\"base_y\":\"-1.02\",\"base_z\":\"0\",\"base_acce_x\":\"0.0002\",\"base_acce_y\":\"-0.0003\",\"base_acce_z\":\"0.0004\"},{\"base_x\":\"0.02\",\"base_y\":\"0\",\"base_z\":\"0\",\"base_acce_x\":\"-0.0001\",\"base_acce_y\":\"-0.0023\",\"base_acce_z\":\"0.0017\"}]";
            JSONArray dataArr = JSONObject.parseArray(ss);
            JSONArray macArr = JSONObject.parseArray(s);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
                for(int i = 0;i<macArr.size();i++) {
                	JSONObject obj = macArr.getJSONObject(i);
                	Date dDate = sdf.parse(obj.getString("date"));
                	while(dDate.before(date)) {
                		Calendar ca = Calendar.getInstance();
                		ca.setTime(dDate);
                		ca.add(Calendar.HOUR, 1);
                		dDate = ca.getTime();
                		int k = raa();
                		JSONObject json = dataArr.getJSONObject(k);
	        			Map< String, Object> map = new HashMap<String, Object>();
	        			Map< String, Object> d = new HashMap<String, Object>();
    					d.put("gX", json.getString("base_acce_x"));
    					d.put("gY", json.getString("base_acce_y"));
    					d.put("gZ", json.getString("base_acce_z"));
    					d.put("X", json.getString("base_x"));
    					d.put("Y", json.getString("base_y"));
    					d.put("Z", 0);
    					map.put("sblxbm", "103");
    					map.put("jczb",d);
    					map.put("jcsj", sdf.format(dDate));
    					map.put("cgq", "1");
    					HttpUtils.sendWuhanPost("http://119.97.193.69:97/DzhZXJC/http/addSblxcs","datatype=6&deviceid="+obj.getString("sn")+"&data="+JSONObject.toJSONString(map).replaceAll("\\\\",""));
    					System.out.println(JSONObject.toJSONString(map));
                	}
                }
            
		} catch (Exception e) {
			System.out.print("error:{}"+ e);
		}
	}
	
	private static int raa() {
		Random random =  new  Random();              
        int  number = random.nextInt(END - START +  1 ) + START;
        return number;
	}
	
	
}
