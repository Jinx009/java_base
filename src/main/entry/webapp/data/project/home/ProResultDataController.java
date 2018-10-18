package main.entry.webapp.data.project.home;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProResult;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProResultService;
import utils.CheckUtils;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/result")
public class ProResultDataController extends BaseController{

	private static final Logger log =  LoggerFactory.getLogger(ProResultDataController.class);
	
	@Autowired
	private ProResultService proResultService;
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> selectList(Integer paperId){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proResultService.list(paperId));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/compare")
	@ResponseBody
	public Resp<?> compare(Integer id){
		Resp<?> resp = new Resp<>(false);
		try {
			ProResult proResult = proResultService.find(id);
			long time = new Date().getTime();
			String filePath = "/project/server-book/webapps/paper/themes/upload_files/"+time+".txt";
			File file = new File(filePath);
		   try {
	            if(!file.exists()){
	                file.createNewFile();
	            }
	            writeFileContent(filePath, proResult.getContent());
	            String res = CheckUtils.compare("", filePath);
	            if("fail".equals(res)){
	            	resp.setMsg("信用不足！");
	            	return resp;
	            }
	            if("fail1".equals(res)){
	            	resp.setMsg("无返回！");
	            	return resp;
	            }
	            if("fail2".equals(res)){
	            	resp.setMsg("检查失败！");
	            	return resp;
	            }
	            proResult.setRemarkA(res);
	            proResult.setStatus(1);
	            proResultService.update(proResult);
	            return new Resp<>(true);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	   /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param newstr  写入的内容
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
	private static boolean writeFileContent(String filepath,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
	
}
