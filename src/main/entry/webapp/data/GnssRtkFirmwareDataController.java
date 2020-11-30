package main.entry.webapp.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.helper.StringUtil;
import database.common.PageDataList;
import database.model.GnssRtkFirmware;
import main.entry.webapp.BaseController;
import service.GnssRtkFirmwareService;
import utils.BaseConstant;
import utils.Resp;

@Controller
@RequestMapping(value = "/d/gnssrtkfirmware")
public class GnssRtkFirmwareDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GnssRtkFirmwareDataController.class);
	
	@Autowired
	private GnssRtkFirmwareService gnssRtkFirmwareService;
	
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> save(String name,String versionNumber,String url){
		Resp<?> resp = new Resp<>(false);
		try {
			gnssRtkFirmwareService.save(url, name, versionNumber);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/del")
	@ResponseBody
	public Resp<?> del(int id){
		Resp<?> resp = new Resp<>(false);
		try {
			gnssRtkFirmwareService.del(id);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> pageList(int p){
		Resp<?> resp = new Resp<>(false);
		try {
			PageDataList<GnssRtkFirmware> list = gnssRtkFirmwareService.findByPage(p);
			return new Resp<>(list);
		} catch (Exception e) {
			log.error("e:",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/uploadFile")
	@ResponseBody
	public Resp<?> uploadFileHandler(@RequestParam("file") MultipartFile file) {
		Resp<?> resp = new Resp<>(false);
		InputStream in = null;
		OutputStream out = null;
		try {
			if (!file.isEmpty()) {
				Date date = new Date();
				String time = String.valueOf(date.getTime());
				String filePath =  StringUtil.add(BaseConstant.BASE_DERICTORY_NAME,time,"_",file.getOriginalFilename());
				log.warn("file path:{}",filePath);
				File serverFile = new File(filePath);
				in = file.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					out.write(b, 0, len);
				}
				out.close();
				in.close();
				resp = new Resp<>(StringUtil.add(time,"_",file.getOriginalFilename()));
				resp.setMsg("上传成功！");
			} else {
				resp.setMsg("未找到文件！");
			}
		} catch (Exception e) {
			log.error("upload error:{}", e);
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				log.error("close error:{}", e);
			}
		}
		return resp;
	}
	
	
}
