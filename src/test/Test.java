package test;

import com.alibaba.fastjson.JSON;

public class Test {

	public static void main(String[] args) {
		String s = "1.jpg";
		String [] arr = s.split(".");
		System.out.println(JSON.toJSONString(arr));
	}
	
}
