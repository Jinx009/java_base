package main.entry.webapp.data.gateway;

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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import database.models.face.FaceGatewayUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.face.FaceGatewayCompareService;
import service.basicFunctions.face.FaceGatewayUserService;
import utils.Resp;
import utils.UUIDUtils;
import utils.face.baidu.Contants;
import utils.face.baidu.FaceFactory;

@Controller
@RequestMapping(value = "/rest/face")
public class GatewayFaceDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayFaceDataController.class);
	
	@Autowired
	private FaceGatewayUserService faceGatewayUserService;
	@Autowired
	private FaceGatewayCompareService faceGatewayCompareService;
	
	/**
	 * 获取人脸库列表
	 * @param p
	 * @return
	 */
	@RequestMapping(path = "/users")
	@ResponseBody
	public Resp<?> pageList(Integer p){
		return new Resp<>(faceGatewayUserService.pageList(p));
	}
	
	/**
	 * 新建或者更新人脸库
	 * @param imagePath
	 * @param name
	 * @param address
	 * @param mobilePhone
	 * @param uid
	 * @return
	 */
	@RequestMapping(path = "/addUser")
	@ResponseBody
	public Resp<?> faceRegister(String imagePath,String name,String address,String mobilePhone,String uid){
		Resp<?> resp = new Resp<>(false);
		try {
			FaceGatewayUser user = faceGatewayUserService.findByMobilePhone(mobilePhone);
			if(user!=null){
				resp.setMsg("该用户已注册！");
				return resp;
			}
			String image =  Contants.imageToBase64ByLocal(Contants.UPLODAD_IMG_PATH+imagePath);
			JSONObject json = FaceFactory.registerOrUpdate(name, address, mobilePhone, uid, image);
			if(json!=null&&json.getString("error_msg").equals("SUCCESS")){
				user = new FaceGatewayUser();
				user.setAddress(address);
				user.setBase64Content(image);
				user.setFaceToken(json.getJSONObject("result").getString("face_token"));
				user.setGroup_id(Contants.GROUP_ID);
				user.setHeight(json.getJSONObject("result").getJSONObject("location").getString("height"));
				user.setImagePath(imagePath);
				user.setLeft(json.getJSONObject("result").getJSONObject("location").getString("left"));
				user.setMobilePhone(mobilePhone);
				user.setName(name);
				user.setRotation(json.getJSONObject("result").getJSONObject("location").getString("rotation"));
				user.setTop(json.getJSONObject("result").getJSONObject("location").getString("top"));
				user.setWidth(json.getJSONObject("result").getJSONObject("location").getString("width"));
				if(uid==null){
					user.setCreateTime(new Date());
				}
				faceGatewayUserService.save(user);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		log.warn("data:{}",resp);
		return resp;
	}
	
	/**
	 * 人脸识别对比列表
	 * @param p
	 * @return
	 */
	@RequestMapping(path = "/compares")
	@ResponseBody
	public Resp<?> compareList(int p){
		return new Resp<>(faceGatewayCompareService.pageList(p));
	}
	
	/**
	 * 上传人脸图片
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public Resp<?> uploadFileHandler(@RequestParam("file") MultipartFile file, 
												HttpServletRequest request,
												HttpServletResponse response)  {
		Resp<?> resp = new Resp<>(false);
		resp.setMsg("图片格式不合法");
		InputStream in = null;
		OutputStream out = null;
		try {
			if (!file.isEmpty()) {
				if(!isImage(file)){
					return resp;
				}
				String uuid = UUIDUtils.random();
				File dir = new File(Contants.UPLODAD_IMG_PATH);
				if (!dir.exists())
					dir.mkdirs();
		        String suffix = file.getOriginalFilename().split("\\.")[1]; 
				String filePath = new Date().getTime()+"_"+ uuid+"_."+ suffix;
				File serverFile = new File(dir.getAbsolutePath()+File.separator +filePath);
				in = file.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					out.write(b, 0, len);
				}
				out.close();
				in.close();
				resp = new Resp<>(filePath);
				resp.setMsg("上传成功！");
			}else {
				resp.setMsg("未找到文件！");
			}
		} catch (Exception e) {
			log.error("upload error:{}",e);
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
				log.error("close error:{}",e);
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
    	String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.BMP|.bmp|.PNG|.png)$";
        String imgp = file.getOriginalFilename();
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(imgp.toLowerCase());
        return matcher.find();
    }   
	
}
