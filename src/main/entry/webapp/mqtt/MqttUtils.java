package main.entry.webapp.mqtt;

public class MqttUtils {

	public static final String HOST = "tcp://139.196.13.251:1883";
	public static final String SERVER_CLINETID = "manager";
	public static String USERNAME = "Zhanway_gnss";
	public static String PASSWORD = "Zhanway_gnss";

	/**
	 * 命令publish到mqtt
	 * @param qos
	 * @param content
	 * @param mac
	 */
	public static void sendCmd(int qos,String content,String mac) {
		StringBuilder sb = new StringBuilder();
		sb.append("/server/");
		sb.append(mac);
		sb.append("/control");
		String topic = sb.toString();
		try {
			DataManager.sendMessage(qos, content.getBytes(), topic);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
