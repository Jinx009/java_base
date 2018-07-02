package utils.face.baidu;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Encoder;

public class Contants {

	public static String APP_ID = "11472197";
	public static String AP_KEY = "1eYOPDd7cXKQMZCYSCtirVaS";
	public static String APP_SECRET = "6PVNRpTHwopmSu4tPrEmND0dgppkTOvu";
	public static String GROUP_ID = "maanshan_cixinshequ";
	public static final String UPLODAD_IMG_PATH = "/zhanway/server/gtw/webapps/static/user/";
	public static final String COMPARE_IMG_PATH = "/zhanway/server/gtw/webapps/static/compare/";
	
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
