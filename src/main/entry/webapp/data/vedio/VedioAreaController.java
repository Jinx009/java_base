package main.entry.webapp.data.vedio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import database.models.vedio.VedioArea;
import service.basicFunctions.vedio.VedioAreaService;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/vedioArea")
public class VedioAreaController {

	private static final Logger log = LoggerFactory.getLogger(VedioAreaController.class);

	@Autowired
	private VedioAreaService vedioAreaService;

	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(String name,String cameraIndex,@RequestParam("file") MultipartFile file, 
			HttpServletRequest request,
			HttpServletResponse response){
		Resp<?> resp = new Resp<>(false);
		InputStream in = null;
		OutputStream out = null;
		try {
			if (!file.isEmpty()) {
				String rootPath = request.getSession().getServletContext().getRealPath("");
				File dir = new File(rootPath + File.separator + "themes/upload_files/vedio");
				if (!dir.exists()){
					dir.mkdirs();
				}
		        String suffix = file.getOriginalFilename().split("\\.")[1]; 
		        if(suffix.indexOf("jpeg")<0&&suffix.indexOf("jpg")<0&&suffix.indexOf("png")<0){
		        	resp.setMsg("仅支持.jpeg,.jpg,.png格式图片");
		        	return resp;
		        }else{
		        	VedioArea vedioArea = vedioAreaService.save(name, cameraIndex, "", 100.00, 300.00, 300.00, 100.00, 100.00, 100.00, 200.00, 200.00);
		        	String filePath = cameraIndex+"_"+vedioArea.getId()+"."+suffix;
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
					String picPath = "/themes/upload_files/vedio/"+filePath;
			        BufferedImage sourceImg =ImageIO.read(new FileInputStream(serverFile)); 
			        vedioArea.setWidth(sourceImg.getWidth());
			        vedioArea.setHeight(sourceImg.getHeight());
					vedioArea.setPicPath(picPath);
					vedioAreaService.update(vedioArea);
					resp = new Resp<>(true);
					return resp;
		        }
			}
		} catch (Exception e) {
			log.error("e:{}",e);
		}finally {
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
				log.error("close error:{}",e);
			}
		}
		return resp;
	}
	
	public Resp<?> update(Integer id,Double x1,Double x2,Double x3,Double x4,Double y1,Double y2,Double y3,Double y4){
		Resp<?> resp = new Resp<>(false);
		try {
			vedioAreaService.update(id,x1,x2,x3,x4,y1,y2,y3,y4);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
}
