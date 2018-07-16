package utils.face.baidu;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.aip.face.AipFace;

import common.helper.StringUtil;

public class FaceFactory {
	
	private static final Logger log = LoggerFactory.getLogger(FaceFactory.class);
	
	/**
	 * 人脸库单个组创建或更新人脸
	 * @param name
	 * @param address
	 * @param mobilePhone
	 * @param uid
	 * @param image
	 * @return
	 */
//		"result": {
//			"face_token": "cb58a7574de71364bee4adc7295ee591",
//			"location": {
//				"top": 357.5593872,
//				"left": 243.3934937,
//				"rotation": 10,
//				"width": 371,
//				"height": 308
//			}
//		},
//		"log_id": 535798999948,
//		"error_msg": "SUCCESS",
//		"cached": 0,
//		"error_code": 0,
//		"timestamp": 1530508716
//	}
	public static JSONObject registerOrUpdate(String name,String address,String mobilePhone,String uid,String image){
		try {
			AipFace client = new AipFace(Contants.APP_ID, Contants.AP_KEY, Contants.APP_SECRET);
			HashMap<String, String> options = new HashMap<String, String>();
		    options.put("user_info", "mobilePhone:"+mobilePhone+",name:"+name+",address:"+address);
		    options.put("quality_control", "NORMAL");
		    options.put("liveness_control", "LOW");
		    String imageType = "BASE64";
		    String userId = "user_"+mobilePhone;
		    JSONObject res = null;
		    if(StringUtil.isBlank(uid)){
		    	res = client.addUser(image, imageType, Contants.GROUP_ID, userId, options);
		    }else{
		    	res = client.updateUser(image, imageType, Contants.GROUP_ID, userId, options);
		    }
		    log.warn("res:{}",res);
		    return res;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
	/**
	 * 获取所有用户id
	 * @param p
	 * @return
	 */
//	{
//	  "cached": 0,
//	  "error_code": 0,
//	  "error_msg": "SUCCESS",
//	  "log_id": 6599001201653,
//	  "result": {"user_id_list": ["user_17717072531"]},
//	  "timestamp": 1530502819
//	}
	public static JSONObject getList(int p){
		try {
			AipFace client = new AipFace(Contants.APP_ID, Contants.AP_KEY, Contants.APP_SECRET);
			HashMap<String, String> options = new HashMap<String, String>();
		    options.put("start", String.valueOf(100*(p-1)));
		    options.put("length", "100");
		    JSONObject res = client.getGroupUsers(Contants.GROUP_ID, options);
		    log.warn("msg:{}",res);
		    return res;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
//	{
//		"result": {
//			"user_list": [{
//				"user_info": "mobilePhone:17717072531,name:金克丝,address:艾泽拉斯8号",
//				"group_id": "maanshan_cixinshequ"
//			}]
//		},
//		"log_id": 1019425947920,
//		"error_msg": "SUCCESS",
//		"cached": 0,
//		"error_code": 0,
//		"timestamp": 1530503259
//	}
	public static JSONObject getUserInfo(String userId){
		try {
			AipFace client = new AipFace(Contants.APP_ID, Contants.AP_KEY, Contants.APP_SECRET);
			HashMap<String, String> options = new HashMap<String, String>();
		    JSONObject res = client.getUser(userId, Contants.GROUP_ID, options);
		    log.warn("msg:{}",res);
		    return res;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}
	
	/**
	 * 获取用户人脸照片
	 * @param userId
	 * @return
	 */
//	{
//		"result": {
//			"face_list": [{
//				"ctime": "2018-07-02 11:18:39",
//				"face_token": "a1f4400ef33af5c6322de64b0a979925"
//			}]
//		},
//		"log_id": 2010515990019,
//		"error_msg": "SUCCESS",
//		"cached": 0,
//		"error_code": 0,
//		"timestamp": 1530505170
//	}
	public static JSONObject getImage(String userId){
		try {
			AipFace client = new AipFace(Contants.APP_ID, Contants.AP_KEY, Contants.APP_SECRET);
			HashMap<String, String> options = new HashMap<String, String>();
		    JSONObject res = client.faceGetlist(userId, Contants.GROUP_ID, options);
		    log.warn("msg:{}",res);
		    return res;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return null;
	}

	public static void main(String[] args) {
//		getList(1);
		getImage("user_17717072531");
	}
	
}
