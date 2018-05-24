package main.entry.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import utils.model.Resp;

@Controller
@RequestMapping(value = "/common/log")
public class LogsController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(LogsController.class);
	
	
	@RequestMapping(path = "/chaozhouLogs")
	@ResponseBody
	public Resp<?> chaozhouLogs(){
		Resp<?> resp = new Resp<>("500","你所访问的资源不存在！",null);
		try {
			File file=new File("/root/nb/heart_log/");
//			File file=new File("/Users/jinx/Downloads/"+d);
			if(file.isDirectory()&&file.exists()){
				List<String> list = new ArrayList<String>();
				for(File temp:file.listFiles()){
		            if(temp.isFile()&&temp.toString().contains(".txt")){
		                list.add(temp.toString().split("/root/nb/heart_log/heart_log")[1]);
		            }
		        }
				Collections.sort(list);
				return new Resp<>(list);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/chaozhouFile")
	@ResponseBody
	public Resp<?> chaozhou(String d){
		Resp<?> resp = new Resp<>("500","无内容！",null);
		try {
			String pathname = "/root/nb/heart_log/heart_log"+d;
			File fileName = new File(pathname);
			if (fileName.exists()) {
				String str = getLog(fileName);
				return new Resp<>(str);
			} 
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	private static String getLog(File fileName){
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
		log.error("error:{}",e);
	}
	return null;
}
	
	
}