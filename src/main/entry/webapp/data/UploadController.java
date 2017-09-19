package main.entry.webapp.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import utils.encode.Base64;
import utils.io.FileUtils;

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
	@RequestMapping(value = "/base/uploadImg")
	@ResponseBody
	public Resp<?> uploadFileHandler(@RequestParam("file") MultipartFile file, 
												HttpServletRequest request,
												HttpServletResponse response)  {
		Resp<?> resp = new Resp<>(false);
		resp.setMsg("图片格式不合法");
		InputStream in = null;
		OutputStream out = null;
		try {
			
			if (!file.isEmpty()&&isImage(file)) {
				String uuid = UUIDUtils.random();
				String rootPath = request.getSession().getServletContext().getRealPath("");
				File dir = new File(rootPath + File.separator + "themes/upload_files");
				if (!dir.exists())
					dir.mkdirs();
		        String suffix = file.getOriginalFilename().split("\\.")[1]; 
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
				resp.setMsg("上传成功！");
			}else {
				resp.setMsg("未找到文件！");
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
	
	/**
	 * 校验是否为图片
	 * @param imageFile
	 * @return
	 */
    private boolean isImage(MultipartFile file) {  
    	String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";
        String imgp = file.getOriginalFilename();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(imgp.toLowerCase());
        return matcher.find();
    }   
	
	/**
	 * 将base64图片保存
	 * @param picContent
	 * @return
	 */
	public Resp<?> saveFile(String picContent,String picName,HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			String rootPath = req.getSession().getServletContext().getRealPath("");
			File dir = new File(rootPath + File.separator + "themes/upload_files/base64");
			byte[] decodedPicBytes = Base64.decode(picContent);
			String uuid = UUIDUtils.random();
			String savePath = dir + uuid + picName;
			FileUtils.saveBytesAsFile(savePath, decodedPicBytes);
			resp = new Resp<>("/themes/upload_files/base64/"+uuid+picName);
			return resp;
		} catch (Exception e) {
			logger.error(" error:{}",e);
		}
		return resp;
	}
	
}
