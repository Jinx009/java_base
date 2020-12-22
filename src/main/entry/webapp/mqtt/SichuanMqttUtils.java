package main.entry.webapp.mqtt;

public class SichuanMqttUtils {

	public static final String HOST = "tcp://218.6.244.191:11883";
	public static final String SERVER_CLINETID = "73960208";
	public static String USERNAME = "Zhanway";
	public static String PASSWORD = "4E5001EAB8565554AE15FBFCA440B055";

	/**
	 * 命令publish到mqtt
	 * @param qos
	 * @param content
	 * @param mac
	 */
	public static void sendCmd(int qos,String content) {
		StringBuilder sb = new StringBuilder();
		sb.append("$dr");
		String topic = sb.toString();
		try {
			SichuanDataManager.sendMessage(qos, toBytes(content), topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }
	
}
