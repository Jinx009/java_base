package common.helper.tool.util;

public class ResultUtil {

	/**
	 * 返回约谈类型
	 * @param type
	 * @return
	 */
	public static String getType(Integer type) {
		if(1==type){
			return "face_status";
		}else if(2==type){
			return "mobile_status";
		}else{
			return "video_status";
		}
	}
	
	
}
