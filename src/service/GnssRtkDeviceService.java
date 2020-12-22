package service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.common.PageDataList;
import database.dao.GnssRtkDeviceDao;
import database.model.GnssRtkDevice;

@Service
public class GnssRtkDeviceService {
	
	private static final Logger log = LoggerFactory.getLogger(GnssRtkDeviceService.class);

	@Autowired
	private GnssRtkDeviceDao gnssRtkDeviceDao;
	
	public void save(GnssRtkDevice gnssRtkDevice){
		gnssRtkDeviceDao.save(gnssRtkDevice);
	}
	
	public GnssRtkDevice findByRoverTag(String roverTag){
		return gnssRtkDeviceDao.findByRoverTag(roverTag);
	}
	
	public GnssRtkDevice findByNewTime() {
		return gnssRtkDeviceDao.findByNewTime();
	}
	
	public GnssRtkDevice findByNew2Time() {
		return gnssRtkDeviceDao.findByNew2Time();
	}

	public GnssRtkDevice findByMac(String mac) {
		return gnssRtkDeviceDao.findByMac(mac);
	}
	
	public void update(GnssRtkDevice device) {
		gnssRtkDeviceDao.update(device);
	}

	public List<GnssRtkDevice> findAll() {
		return gnssRtkDeviceDao.findAll();
	}

	public void saveDevice(String payload) {
		GnssRtkDevice gnssDevice = new GnssRtkDevice();
		gnssDevice.setMac(payload);
		gnssDevice.setUpdatetime("");
		gnssDevice.setCreateTime(new Date());
		gnssDevice.setSwitchType(0);
		gnssDevice.setDataType("");
		gnssDevice.setRovertag(payload);
		gnssRtkDeviceDao.save(gnssDevice);
	}

	public PageDataList<GnssRtkDevice> page(int p) {
		return gnssRtkDeviceDao.page(p);
	}

	/**
	 * 检验对接平台是否需要发送原始数据
	 * @param mac
	 * @param bs
	 */
	public void checkData(String mac, byte[] bs) {
		GnssRtkDevice device = gnssRtkDeviceDao.findByMac(mac);
		if(device!=null&&device.getLocation().equals("SICHUAN")) {//四川平台
			try {
				 postFile("https://218.6.244.191:15001/api/devices/datapoints?type=3&deviceId="+device.getDeviceId(), bs,device.getApikey());
			} catch (Exception e) {
				log.error("error:{}",e);
			}
		}
		
	}
	
	
	
	public static byte[] toByteArray(InputStream in) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024 * 4];
	    int n = 0;
	    while ((n = in.read(buffer)) != -1) {
	        out.write(buffer, 0, n);
	    }
	    return out.toByteArray();
	}
	
	public static String postFile(String url, byte[] data,String apikey) {  
        String result = null;  
        try {  
            URL url1 = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("Cache-Control", "no-cache");  
            conn.setRequestProperty("apikey",apikey);  
            String boundary = "-----------------------------" + System.currentTimeMillis();  
            conn.setRequestProperty("Content-Type", "application/octet-stream; boundary=" + boundary);  
            OutputStream output = conn.getOutputStream();  
            output.write(("--" + boundary + "\r\n").getBytes());  
            output.write(data);  
            output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());  
            output.flush();  
            output.close();  
            InputStream resp = conn.getInputStream();  
            StringBuffer sb = new StringBuffer();  
            byte[] data2 = new byte[1024];
            int len = 0;
            while ((len = resp.read(data2)) > -1)  
                sb.append(new String(data2, 0, len, "utf-8"));  
            resp.close();  
            result = sb.toString();  
            log.warn("result:{}",result);  
        } catch (ClientProtocolException e) {  
            log.error("postFile，不支持http协议:{}", e);  
        } catch (IOException e) {  
        	log.error("postFile数据传输失败:{}",e);  
        }  
        return result;  
    }  


	
}
