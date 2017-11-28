package main.entry.webapp.data.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/common/log")
public class CommonLogDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(CommonLogDataController.class);
	
	@RequestMapping(path = "/files")
	@ResponseBody
	public Resp<?> listFile(String d){
		Resp<?> resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,"你所访问的资源不存在！",null);
		try {
			File file=new File("/root/claa_logs/"+d);
//			File file=new File("/Users/jinx/Downloads/"+d);
			if(file.isDirectory()&&file.exists()){
				List<String> list = new ArrayList<String>();
				for(File temp:file.listFiles()){
		            if(temp.isFile()){
		                list.add(temp.toString().split("/root/claa_logs/"+d)[1]);
		            }
		        }
				return new Resp<>(list);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/file")
	@ResponseBody
	public Resp<?> getFile(String d){
		Resp<?> resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,"无内容！",null);
		try {
			String pathname = "/root/claa_logs/"+d;
//			String pathname = "/Users/jinx/Downloads/"+d;
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
		while ((s = br.readLine()) != null) {
			result.append(System.lineSeparator() + s);
		}
		br.close();
		return result.toString();
	} catch (IOException e) {
		log.error("error:{}",e);
	}
	return null;
}
	
}
