package common.helper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import database.common.nbBaseModel;

public class HttpWebIOHelper {
	

	public static void _printWebJson(Object theData, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(CommonHelper.getStringOfObj(theData));
		out.flush();
		out.close();
	}
	
	public static void printReturnJson(nbReturn ret, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(CommonHelper.getStringOfObj(prepareRetAndData(ret)));
		out.flush();
		out.close();
	}
	
	public static void printStringOut(String theData, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(theData);
		out.flush();
		out.close();
	}
	
	public static String servletInputStream2String(HttpServletRequest request) throws IOException{
		request.setCharacterEncoding("utf-8");
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		while( (line = reader.readLine()) != null){
			stringBuilder.append(line);
		}
		return stringBuilder.toString();
	}
	
	public static Map<String, Object> servletInputStream2JsonMap(HttpServletRequest request) throws IOException{
		String jsonString = servletInputStream2String(request);
		return (Map<String, Object>)JSONObject.parseObject(jsonString);
	}
	
	public static Map<String, Object> prepareRetAndData(nbReturn ret){
		Object obj = ret.getObject();
		return prepareRetAndData(ret, obj);
	}
	
	public static Map<String, Object> prepareRetAndData(nbReturn ret, Object obj){
		Map<String, Object> theData = new HashMap<String, Object>();
		
		theData.put("retCode", ret.getRetCode());
		theData.put("retMessage", ret.getRetString());
		if( obj == null ){
			theData.put("retData", null);
			return theData;
		}
		if( obj instanceof nbBaseModel ){
			theData.put("retData", ((nbBaseModel) obj).modelToMap() );
		}else if (obj instanceof List || obj instanceof Map ){
			theData.put("retData", obj);
		}
		
		return theData;
	}
}
