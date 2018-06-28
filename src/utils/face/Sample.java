package utils.face;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;

import sun.misc.BASE64Encoder;


public class Sample {

	//设置APPID/AK/SK
    public static final String APP_ID = "11413840";
    public static final String API_KEY = "NODfqUXxcouF6wGHq6BfsjTh";
    public static final String SECRET_KEY = "mL6sAs4yRBlWITyL8ZeRIRAYLhzcG0FU";

    public static void main(String[] args) {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
//        sample(client);
        kown(client);
    }
    
    public static void sample(AipFace client) {
        String image1 = imageToBase64ByLocal("/Users/jinx/Downloads/test/test007.jpg");
        String image2 = imageToBase64ByLocal("/Users/jinx/Downloads/test/test006.jpg");
        MatchRequest req1 = new MatchRequest(image1, "BASE64");
        MatchRequest req2 = new MatchRequest(image2, "BASE64");
        ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
        requests.add(req1);
        requests.add(req2);

        JSONObject res = client.match(requests);
        try {
			System.out.println(res.toString(2));
		} catch (JSONException e) {
			e.printStackTrace();
		}

    }
    
    public static void kown(AipFace client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("max_user_num", "3");
        
        String image = imageToBase64ByLocal("/Users/jinx/Downloads/test/test003.jpg");
        String imageType = "BASE64";
        String groupIdList = "cixin";
        
        // 人脸搜索
        JSONObject res = client.search(image, imageType, groupIdList, options);
        try {
			System.out.println(res.toString(2));
		} catch (JSONException e) {
			e.printStackTrace();
		}

    }
    
    
	/**
	 * 本地图片转换成base64字符串
	 * @param imgFile	图片本地路径
	 * @return
	 *
	 * @dateTime 2018-02-23 14:40:46
	 */
	public static String imageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
 
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	
}
