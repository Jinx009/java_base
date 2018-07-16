package main.entry.webapp.task;

import java.io.File;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.face.FaceGatewayCompare;
import database.models.face.FaceGatewayUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.face.FaceGatewayCompareService;
import service.basicFunctions.face.FaceGatewayUserService;
import utils.face.baidu.Compare;
import utils.face.baidu.Contants;

@Component
@Lazy(value = false)
public class FaceTask extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(FaceTask.class);
	
	@Autowired
	private FaceGatewayCompareService faceGatewayCompareService;
	@Autowired
	private FaceGatewayUserService faceGatewayUserService;

	@Scheduled(fixedRate = 1000 * 1200,initialDelay = 2000)
	public void compare(){
			try {
				File file=new File(Contants.COMPARE_IMG_PATH);
				if(file.isDirectory()&&file.exists()){
					for(File temp:file.listFiles()){
			            if(temp.isFile()&&temp.toString().indexOf("人脸")>-1&&temp.toString().indexOf("fail")>-1){
			                realCompare(temp.toString());
			                return;
			            }
			        }
				}
			} catch (Exception e) {
				log.error("error:{}",e);
			}
	}
	
//	{
//	"result": {
//		"face_token": "f94f8667573089e2ff108d4b7fb975c7",
//		"user_list": [{
//			"score": 87.323394775391,
//			"group_id": "maanshan_cixinshequ",
//			"user_id": "user_17717072531",
//			"user_info": "mobilePhone:17717072531,name:金克丝,address:艾泽拉斯8号"
//		}, {
//			"score": 0,
//			"group_id": "maanshan_cixinshequ",
//			"user_id": "user_18217700275",
//			"user_info": "mobilePhone:18217700275,name:Jinx,address:台湾台北"
//		}]
//	},
//	"log_id": 4515753515457,
//	"error_msg": "SUCCESS",
//	"cached": 0,
//	"error_code": 0,
//	"timestamp": 1530515681
//}
	public void realCompare(String fileName){
		try {
			File file = new File(Contants.COMPARE_IMG_PATH+fileName);
			JSONObject json = Compare.compare(Contants.COMPARE_IMG_PATH+fileName);
			if(json.getInt("error_code")==0){
				FaceGatewayCompare faceGatewayCompare = new FaceGatewayCompare();
				faceGatewayCompare.setBase64Content(Contants.imageToBase64ByLocal(Contants.COMPARE_IMG_PATH+fileName));
				faceGatewayCompare.setCreateTime(new Date());
				faceGatewayCompare.setFaceToken(json.getJSONObject("result").getString("face_token"));
				faceGatewayCompare.setScore(json.getJSONObject("result").getJSONArray("user_list").getJSONObject(0).getString("score"));
				String uid = json.getJSONObject("result").getJSONArray("user_list").getJSONObject(0).getString("user_id");
				String mobilePhone = uid.split("_")[1];
				FaceGatewayUser faceGatewayUser = faceGatewayUserService.findByMobilePhone(mobilePhone);
				faceGatewayCompare.setAddress(faceGatewayUser.getAddress());
				faceGatewayCompare.setName(faceGatewayUser.getName());
				faceGatewayCompare.setUserImgPath(faceGatewayUser.getImagePath());
				faceGatewayCompareService.save(faceGatewayCompare);
				file.renameTo(new File(Contants.COMPARE_IMG_PATH+"人脸_"+new Date().getTime()+"_success.jpg"));
			}else{
				file.renameTo(new File(Contants.COMPARE_IMG_PATH+"人脸_"+new Date().getTime()+"_fail.jpg"));
			}
		} catch (JSONException e) {
			log.error("error:{}",e);
		}
	}
}
