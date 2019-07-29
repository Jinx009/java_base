package main.entry.webapp.data.vedio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import database.models.vedio.VedioTask;
import main.entry.webapp.BaseController;
import service.basicFunctions.vedio.VedioAreaService;
import service.basicFunctions.vedio.VedioTaskService;
import utils.Resp;
import utils.VedioUtils;

@Controller
@RequestMapping(value = "/d/vedioTask")
public class VedioTaskController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(VedioTaskController.class);
	
	@Autowired
	private VedioTaskService vedioTaskService;
	@Autowired
	private VedioAreaService vedioAreaService;
	
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(String name,Integer areaId,String vedioStart,@RequestParam("file") MultipartFile file, 
			HttpServletRequest request,
			HttpServletResponse response){
		Resp<?> resp = new Resp<>(false);
		InputStream in = null;
		OutputStream out = null;
		try {
			VedioArea vedioArea = vedioAreaService.find(areaId);
			if (!file.isEmpty()) {
				Date date = new Date();
				String rootPath = request.getSession().getServletContext().getRealPath("");
				String dPath = vedioArea.getCameraIndex()+"_"+date.getTime();
				File dir = new File(rootPath + File.separator + "themes/upload_files/vedio/"+dPath);
				if (!dir.exists()){
					dir.mkdirs();
				}
		        String suffix = file.getOriginalFilename().split("\\.")[1]; 
		        if(suffix.indexOf("mp4")<0){
		        	resp.setMsg("仅支持.mp4视频");
		        	return resp;
		        }else{
		        	VedioTask  vedioTask = vedioTaskService.save(dPath, 0l, areaId,vedioStart,"");
		        	String filePath = dPath+"."+suffix;
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
					vedioTask.setTime(VedioUtils.getVedioTime(serverFile));
					vedioTask.setSize(VedioUtils.getVedioSize(serverFile));
					vedioTask.setDirPath(dir.getAbsolutePath());
					vedioTaskService.update(vedioTask);
					new Thread(){
						public void run(){
					        String fileName = vedioTask.getDirPath()+"/"+vedioTask.getName()+".mp4";
					        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
					        Date d;
							try {
								d = format.parse("1998-01-01 00:00:00");
								String time = "";
								long num = vedioTask.getTime()/2;
								File[] files = new File[(int) num];
						        for( int i = 0;i<num;i++){
						            Calendar calendar = Calendar.getInstance();
						            calendar.setTime(d);
						            calendar.add(Calendar.SECOND,2);
						            d = calendar.getTime();
						            time = f.format(d);
						            VedioUtils.covPic(fileName,time,vedioTask.getDirPath()+"/ffmpeg_"+(i+1)+".jpg",vedioTask.getSize());
						            files[i] = new File(vedioTask.getDirPath()+"/ffmpeg_"+(i+1)+".jpg");
						        }
						        VedioUtils.zipFiles(files, new File(vedioTask.getDirPath()+"/"+vedioTask.getName()+".zip"));
						        vedioTask.setStatus(2);
						        vedioTask.setNum((int)num);
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}.start();
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
	
}
