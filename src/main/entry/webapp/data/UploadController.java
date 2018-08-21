package main.entry.webapp.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import main.entry.webapp.BaseController;
import utils.Resp;
import utils.UUIDUtils;

@Controller
public class UploadController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	/**
	 * 上传文件
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/base/upload")
	@ResponseBody
	public Resp<?> uploadFileHandler(@RequestParam("file") MultipartFile file, 
												HttpServletRequest request,
												HttpServletResponse response)  {
		Resp<?> resp = new Resp<>(false);
		InputStream in = null;
		OutputStream out = null;
		try {
			
			if (!file.isEmpty()) {
				String uuid = UUIDUtils.random();
				String rootPath = request.getSession().getServletContext().getRealPath("");
				File dir = new File(rootPath + File.separator + "themes/upload_files");
				if (!dir.exists())
					dir.mkdirs();
		        String suffix = file.getOriginalFilename().split("\\.")[1]; 
		        if(suffix.indexOf("doc")<0&&suffix.indexOf("docx")<0&&suffix.indexOf("txt")<0){
		        	resp.setMsg(" Only allow .doc , .docx ,.txt !");
		        	return resp;
		        }
				String filePath = new Date().getTime() + uuid+"_."+ suffix;
				File serverFile = new File(dir.getAbsolutePath() + File.separator +filePath);
				in = file.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					out.write(b, 0, len);
				}
				out.close();
				in.close();
				resp = new Resp<>("/themes/upload_files/"+filePath);
				resp.setMsg("upload success！");
			}else {
				resp.setMsg("file not found！");
			}
		} catch (Exception e) {
			logger.error("upload error:{}",e);
		} finally {
			try {
				if(in!=null){
					in.close();
					in = null;
				}
				if(out!=null){
					out.close();
					out = null;
				}
			} catch (IOException e) {
				logger.error("close error:{}",e);
			}
		}
		return resp;
	}
	
	
	
}
