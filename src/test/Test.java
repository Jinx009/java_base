package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class Test {

	private static double EARTH_RADIUS = 6378.137;//地球半径
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	} 

	public static double getDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);

	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 10000) / 10000;
	   return s;
	}
	
	
	public static void main(String[] args) {
		System.out.println(13%30);
	}
	
	
	
	public static String getLog(File fileName){
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));
			BufferedReader br = new BufferedReader(reader);
			StringBuilder result = new StringBuilder();
			String s = null;
			List<String> list = new ArrayList<String>();
			while ((s = br.readLine()) != null) {
				list.add(System.lineSeparator() + s);
			}
			br.close();
			if(list!=null&&!list.isEmpty()){
				for(int i = list.size()-1;i>=0;i--){
					result.append(list.get(i));
				}
			}
			return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
