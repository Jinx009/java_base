package utils.face.baidu;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.aip.face.AipFace;

public class Compare {
	
	private static final Logger log = LoggerFactory.getLogger(Compare.class);

	/**
	 * 人脸查找
	 * @param imagePath
	 * @return
	 */
//	{
//		"result": {
//			"face_token": "f94f8667573089e2ff108d4b7fb975c7",
//			"user_list": [{
//				"score": 87.323394775391,
//				"group_id": "maanshan_cixinshequ",
//				"user_id": "user_17717072531",
//				"user_info": "mobilePhone:17717072531,name:金克丝,address:艾泽拉斯8号"
//			}, {
//				"score": 0,
//				"group_id": "maanshan_cixinshequ",
//				"user_id": "user_18217700275",
//				"user_info": "mobilePhone:18217700275,name:Jinx,address:台湾台北"
//			}]
//		},
//		"log_id": 4515753515457,
//		"error_msg": "SUCCESS",
//		"cached": 0,
//		"error_code": 0,
//		"timestamp": 1530515681
//	}
    public static JSONObject compare(String imagePath) {
        try {
        	AipFace client = new AipFace(Contants.APP_ID, Contants.AP_KEY, Contants.APP_SECRET);
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("quality_control", "NORMAL");
            options.put("liveness_control", "LOW");
            options.put("max_user_num", "3");
            String image = Contants.imageToBase64ByLocal(imagePath);
            String imageType = "BASE64";
            JSONObject res = client.search(image, imageType, Contants.GROUP_ID, options);
            log.warn("msg:{}",res);
            return res;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
        return null;
    }
    
    public static void main(String[] args) {
		compare("/Users/jinx/Downloads/test/test0002.jpg");
	}
	
}
