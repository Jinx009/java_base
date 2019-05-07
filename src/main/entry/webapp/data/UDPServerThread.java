package main.entry.webapp.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import common.helper.ApplicationContextProvider;
import database.models.IoTCloudDevice;
import database.models.IotCloudLog;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import utils.HttpUtils;

/*
 * 服务器线程处理类
 */
public class UDPServerThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(UDPServerThread.class);

	DatagramSocket socket = null;

	public UDPServerThread(DatagramSocket socket) {
		this.socket = socket;
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];// 指定用于接受数据报的大小
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);// 接受到数据之前该方法处于阻塞状态
				// String info = new String(data, 0, packet.getLength());
				// log.warn("client info:{}", info);
				String info = bytesToHexString(data, packet.getLength());
				log.warn("client info:{}", info);
				String mac = info.substring(0, 16);
				log.warn("client length:{}", packet.getLength());
				log.warn("client mac:{}", mac);
				IotCloudDeviceService iotCloudDeviceService = ((IotCloudDeviceService) ApplicationContextProvider
						.getBeanByName("iotCloudDeviceService"));
				IotCloudLogService iotCloudLogService = ((IotCloudLogService) ApplicationContextProvider
						.getBeanByName("iotCloudLogService"));
				IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByMac(mac);
				IotCloudLog iotCloudLog = new IotCloudLog();
				iotCloudLog.setData(info);
				iotCloudLog.setFromSite("udp");
				iotCloudLog.setCreateTime(new Date());
				iotCloudLog.setImei(ioTCloudDevice.getImei());
				iotCloudLog.setType(0);
				iotCloudLog.setMac(ioTCloudDevice.getMac());
				iotCloudLogService.save(iotCloudLog);
				if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY")) {
					HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + info);
					sendWuhanQj(ioTCloudDevice, iotCloudLog);
				}
				if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("QJ_ZHANWAY_BJ")) {
					HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push?data=" + info);
					sendBeijingQj(ioTCloudDevice, iotCloudLog);
				}
				if (ioTCloudDevice.getLocalIp() != null && ioTCloudDevice.getLocalIp().equals("QJ_PUSHI")) {
					HttpUtils.get("http://app.zhanway.com/home/cloud/qj/zhanway/push/2_0?data=" + info);
					try {
						sendPushi(iotCloudLog.getData(), ioTCloudDevice);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// InetAddress address = packet.getAddress();
				// int port = packet.getPort();
				// byte[] data2 = "ok".getBytes();
				// DatagramPacket packet2 = new DatagramPacket(data2,
				// data2.length, address, port);
				// socket.send(packet2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 普适地质灾害
	 * @param data
	 * @param ioTCloudDevice
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void sendPushi(String data, IoTCloudDevice ioTCloudDevice) throws NumberFormatException, Exception {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> sendData = new HashMap<>();
		map.put("deviceId", ioTCloudDevice.getUdpIp().split("_")[0]);
		map.put("apikey", ioTCloudDevice.getUdpIp().split("_")[1]);
		String type = data.substring(16, 18);
		if (type.equals("68")) {
			type = "报警";
		} else {
			type = "心跳";
		}
		String acc_x = getData10000(data.substring(18, 19), data.substring(18, 22)) ;
		String acc_y = getData10000(data.substring(24, 25), data.substring(24, 28)) ;
		String acc_z = getData10000(data.substring(30, 31), data.substring(30, 34));
		String x = getData100(data.substring(36, 37), data.substring(36, 40));
		String y = getData100(data.substring(42, 43), data.substring(42, 46));
		String z = getData100(data.substring(72, 73), data.substring(72, 76));
		sendData.put("103_1", x+","+y+","+z+","+acc_x+","+acc_y+","+acc_z);
		map.put("data", sendData);
		String json = JSONObject.toJSONString(map);
		log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
		String url = "http://ghiot.cigem.cn/api/devices/datapoints?type=3";
		log.warn("send url-----------------------\n:{}\n---------------------------------", url);
		String res = HttpUtils.postJson(url, json);
		log.warn("send res-----------------------\n:{}\n---------------------------------", res);
	}

	
	private String getData100(String index, String _d) throws Exception {
		log.warn("index:{},data:{}", index, _d);
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		Double r = Double.valueOf(e)/100;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result =  decimalFormat.format(g);
		log.warn("result:{}", result);
		return result;
	}
	
	private String getData10000(String index, String _d) throws Exception {
		log.warn("index:{},data:{}", index, _d);
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		Double r = Double.valueOf(e)/10000;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result =  decimalFormat.format(g);
		log.warn("result:{}", result);
		return result;
	}
	
	private String getData(String index, String _d) throws Exception {
		log.warn("index:{},data:{}", index, _d);
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		Double r = Double.valueOf(e)/1000;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result =  decimalFormat.format(g);
		log.warn("result:{}", result);
		return result;
	}

	private void sendBeijingQj(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> warning = new HashMap<String, Object>();
		Map<String, Object> monitoring = new HashMap<String, Object>();
		String data = iotCloudLog.getData();
		try {
			map.put("voltage", getData(data.substring(48, 49), data.substring(48, 52)));
			warning.put("acc_x_type", Integer.valueOf(data.substring(22, 24)));
			warning.put("acc_y_type", Integer.valueOf(data.substring(28, 30)));
			warning.put("acc_z_type", Integer.valueOf(data.substring(34, 36)));
			warning.put("x_type", Integer.valueOf(data.substring(40, 42)));
			warning.put("y_type", Integer.valueOf(data.substring(46, 48)));
			monitoring.put("x", Double.valueOf(getData(data.substring(36, 37), data.substring(36, 40))));
			monitoring.put("y", Double.valueOf(getData(data.substring(42, 43), data.substring(42, 46))));
			monitoring.put("acc_x", Double.valueOf(getData(data.substring(18, 19), data.substring(18, 22))));
			monitoring.put("acc_y", Double.valueOf(getData(data.substring(24, 25), data.substring(24, 28))));
			monitoring.put("acc_z", Double.valueOf(getData(data.substring(30, 31), data.substring(30, 34))));
			map.put("warning", warning);
			map.put("monitoring", monitoring);
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			String url = "http://" + device.getUdpIp() + device.getSimCard().split("_")[0] + "/datapoints?type=3";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.postBeijingQjJson(url, json, device.getSimCard().split("_")[1]);
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

	private void sendWuhanQj(IoTCloudDevice device, IotCloudLog iotCloudLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		String data = iotCloudLog.getData();
		String sn = data.substring(0, 16);
		map.put("JCDB19A080", sn);
		String type = data.substring(16, 18);
		if (type.equals("68")) {
			type = "报警";
		} else {
			type = "心跳";
		}
		try {
			map.put("JCDB19A130", getData(data.substring(48, 49), data.substring(48, 52)));
			map.put("JCDB19A010", type);
			map.put("JCDB19A020", getData(data.substring(18, 19), data.substring(18, 22)));
			map.put("JCDB19A030", getData(data.substring(24, 25), data.substring(24, 28)));
			map.put("JCDB19A040", getData(data.substring(30, 31), data.substring(30, 34)));
			map.put("JCDB19A050", Integer.valueOf(data.substring(22, 24)));
			map.put("JCDB19A060", Integer.valueOf(data.substring(28, 30)));
			map.put("JCDB19A070", Integer.valueOf(data.substring(34, 36)));
			map.put("JCDB19A090", Double.valueOf(getData(data.substring(36, 37), data.substring(36, 40))));
			map.put("JCDB19A100", Double.valueOf(getData(data.substring(42, 43), data.substring(42, 46))));
			map.put("JCDB19A110", Integer.valueOf(data.substring(40, 42)));
			map.put("JCDB19A120", Integer.valueOf(data.substring(46, 48)));
			String json = JSONObject.toJSONString(map);
			log.warn("send qj-----------------------\n:{}\n---------------------------------", json);
			String url = "http://" + device.getUdpIp() + ":" + device.getUdpPort() + "/DzhZXJC/Sjcj/AddJCDB19A";
			log.warn("send url-----------------------\n:{}\n---------------------------------", url);
			String res = HttpUtils.sendPost(url, "json=" + json + "&appID=DZH_ZXJC_SJCJ");
			log.warn("send res-----------------------\n:{}\n---------------------------------", res);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

	
	public static void main(String[] args) throws IOException {
		/*
		 * 服务器端接受客户端的数据
		 */
		DatagramSocket socket = new DatagramSocket(7777);
		UDPServerThread st = new UDPServerThread(socket);
		st.start();

	}
	
	
	private static String bytesToHexString(byte[] src, int length) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || length <= 0) {
			return null;
		}
		for (int i = 0; i < length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv.toUpperCase());
		}
		return stringBuilder.toString();
	}
}