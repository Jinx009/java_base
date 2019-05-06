package utils;


public class UrlUtils {


	/**
	 * 解析url
	 *
	 * @param url
	 * @return
	 */
	public static String parse(String urlParts,String key) {
		//有参数
		String[] params = urlParts.split("&");
		for (String param : params) {
			String[] keyValue = param.split("=");
			if(keyValue[0].equals(key)){
				return keyValue[1];
			}
		}
		return "";
	}
	
	public static String getHex(Integer num){
		String _num =  Integer.toHexString(600);
		if(_num.length()==1){
			return "0"+_num+"00";
		}
		if(_num.length()==2){
			return _num+"00";
		}
		if(_num.length()==3){
			String[] s = _num.split("");
			return s[1]+s[2]+"0"+s[0];
		}
		if(_num.length()==4){
			String[] s = _num.split("");
			return s[2]+s[3]+s[0]+s[1];
		}
		return "";
	}

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( Integer.toHexString(60));
	}
}
