package main.entry.webapp.data.face;

import java.io.InputStream;

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

import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.HttpData;
import utils.HttpUtils;
import utils.Resp;
import utils.encode.Base64;

/**
 * 
 * 人脸识别页面数据接口
 * 
 * @author jinx
 *
 */
@Controller
@RequestMapping(value = "/home/cloud/face")
public class FaceDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(FaceDataController.class);
	
	@Autowired
	private HttpService httpService;
	
	/**
	 * 人脸库上传照片
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(path = "/uploadImg")
	@ResponseBody
	public Resp<?> postFile(@RequestParam("file") MultipartFile file, 
			HttpServletRequest request,
			HttpServletResponse response) {  
		Resp<?> resp = new Resp<>(false);
        try {  
        	InputStream in = null;
    		byte[] data = null;
    		in = file.getInputStream();
			data = new byte[in.available()];
			in.read(data);
			in.close();
			String base64_content = Base64.encode(data);
			resp = JSONObject.parseObject(HttpUtils.postParams(HttpData.getFaceUpLoadUrl(base64_content)),Resp.class); 
        } catch (Exception e) {  
            log.error("error:{}",e);
        }  
        return resp;  
    }  
	
	/**
	 * 更新或新建人脸库人脸
	 * @param imagePath
	 * @param name
	 * @param address
	 * @param mobilePhone
	 * @param uid
	 * @return
	 */
	@RequestMapping(path = "/createOrUpdateFaceFactory")
	@ResponseBody
	public Resp<?> createFaceFactory(String imagePath,String name,String address,String mobilePhone,String uid){
		Resp<?> resp = new Resp<>(false);
		try {
			resp = JSONObject.parseObject( httpService.postParams(HttpData.getCreateFaceFactoryUrl(imagePath,name,address,mobilePhone,uid)),Resp.class);
			return resp;		
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取人脸库中所有人脸
	 * @return
	 */
	@RequestMapping(path = "/faceFactoryUsers")
	@ResponseBody
	public Resp<?> users(Integer p){
		Resp<?> resp = new Resp<>(false);
		try {
			resp = JSONObject.parseObject(httpService.get(HttpData.getFaceFactoryUsersUrl(p)),Resp.class);
			return resp;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	/**
	 * 获取已经对比的人脸结果
	 * @return
	 */
	@RequestMapping(path = "/compareUsers")
	@ResponseBody
	public Resp<?> compareUsers(){
		Resp<?> resp = new Resp<>(false);
		try {
			resp = JSONObject.parseObject(httpService.get(HttpData.getCompareUsersUrl()),Resp.class);
			return resp;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
}
