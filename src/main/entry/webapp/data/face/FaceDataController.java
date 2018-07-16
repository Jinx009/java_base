package main.entry.webapp.data.face;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import main.entry.webapp.BaseController;
import service.basicFunctions.HttpService;
import utils.HttpData;
import utils.Resp;

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
        String result = null;  
        try {  
            URL url1 = new URL(HttpData.getFaceUpLoadUrl());  
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("Cache-Control", "no-cache");  
            String boundary = "-----------------------------" + System.currentTimeMillis();  
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);  
            OutputStream output = conn.getOutputStream();  
            output.write(("--" + boundary + "\r\n").getBytes());  
            output.write(  String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName()).getBytes());  
            output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());  
            byte[] data = new byte[1024];  
            int len = 0;  
            InputStream input = file.getInputStream(); 
            while ((len = input.read(data)) > -1) {  
                output.write(data, 0, len);  
            }  
            output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());  
            output.flush();  
            output.close();  
            input.close();  
            InputStream respo = conn.getInputStream();  
            StringBuffer sb = new StringBuffer();  
            while ((len = respo.read(data)) > -1)  
                sb.append(new String(data, 0, len, "utf-8"));  
            respo.close();  
            result = sb.toString();  
            log.warn("res:{}",result);  
            return new Resp<>(JSON.toJSON(result));
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
