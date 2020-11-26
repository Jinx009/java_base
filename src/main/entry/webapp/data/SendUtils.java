package main.entry.webapp.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import utils.HttpUtils;
import utils.StringUtil;

public class SendUtils {

	public static void main(String[] args) {
		try {
			String data = readFileContent("/Users/jinx/Downloads/base.txt");
			String data2 = readFileContent("/Users/jinx/Downloads/new.txt");
			JSONArray base = JSONObject.parseArray(data);
			JSONArray new2 = JSONObject.parseArray(data2);
			for(int i = 0;i<base.size();i++) {
				JSONObject obj = base.getJSONObject(i);
				String sim = obj.getString("C");
				for(int j=0;j<new2.size();j++) {
					JSONObject obj2 = new2.getJSONObject(j);
					if(sim.equals(obj2.getString("sim"))) {
						System.out.println(obj2.getString("mo"));
					}
				}
			}
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
		} catch (Exception e) {
			System.out.print("error:{}"+ e);
		}
	}
	
	private static String hexToFloat_1(String s) {
		Random r1 = new Random();
		int a = r1.nextInt()>0?1:-1;
		Double r = Double.valueOf(s);
		r = r+(r*a/4);
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}
	
	
	
	
	public static String readFileContent(String fileName) {
	    File file = new File(fileName);
	    BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr;
	        while ((tempStr = reader.readLine()) != null) {
	            sbf.append(tempStr);
	        }
	        reader.close();
	        return sbf.toString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    return sbf.toString();
	}
	
}
